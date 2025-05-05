package cl.tenpo.learning.reactive.tasks.task2.infrastructure.config;

import cl.tenpo.learning.reactive.tasks.task2.domain.model.event.ErrorEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.sender.SenderOptions;

import java.util.Collections;
import java.util.Map;

import static cl.tenpo.learning.reactive.tasks.task2.util.Constants.CR_RETRY_EXHAUSTED;

@Configuration
public class KafkaConfig {

    public <T> ReceiverOptions<String, T> receiverOptions(
            final KafkaProperties kafkaProperties, final Class<T> eventType, final String topic, final Class<?> clazz
    ) {
        Map<String, Object> props = kafkaProperties.buildConsumerProperties(null);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, clazz);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, eventType);
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        ReceiverOptions<String, T> receiverOptions = ReceiverOptions.create(props);
        return receiverOptions.subscription(Collections.singletonList(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, ErrorEvent> errorEventConsumerTemplate(
            final KafkaProperties kafkaProperties
    ) {
        ReceiverOptions<String, ErrorEvent> receiverOptions = receiverOptions(
                kafkaProperties, ErrorEvent.class, CR_RETRY_EXHAUSTED, JsonDeserializer.class
        );
        return new ReactiveKafkaConsumerTemplate<>(receiverOptions);
    }

    private <K, V> SenderOptions<K, V> senderOptions(final KafkaProperties properties) {
        Map<String, Object> props = properties.buildProducerProperties(null);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false);
        return SenderOptions.create(props);
    }

    @Bean
    public ReactiveKafkaProducerTemplate<String, ErrorEvent> errorEventProducerTemplate(
            final KafkaProperties properties
    ) {
        return new ReactiveKafkaProducerTemplate<>(senderOptions(properties));
    }
}
