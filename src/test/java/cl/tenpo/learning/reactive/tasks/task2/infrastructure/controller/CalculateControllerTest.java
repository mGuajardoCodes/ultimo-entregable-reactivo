package cl.tenpo.learning.reactive.tasks.task2.infrastructure.controller;

import cl.tenpo.learning.reactive.tasks.task2.T2Application;
import cl.tenpo.learning.reactive.tasks.task2.infrastructure.dto.internal.CalculationRequest;
import cl.tenpo.learning.reactive.tasks.task2.testConfig.MockServerConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(classes = T2Application.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Import(MockServerConfig.class)
@ActiveProfiles("test")
class CalculateControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Nested
    @DisplayName("Calculate percentage test")
    class CalculatePercentageTest {

        @DisplayName("Make correctly calculation")
        @Test
        void calculatePercentageSatisfactorily() {
            CalculationRequest request = new CalculationRequest(5.0, 5.0);
            webTestClient
                    .post()
                    .uri("/calculation")
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .exchange()
                    .expectStatus()
                    .isOk()
                    .expectBody();
        }
    }

}
