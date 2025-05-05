package cl.tenpo.learning.reactive.tasks.task2.testConfig.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TestFileUtils {
    public static final Path TEST_DIR = Path.of("src/test/resources");
    private static final ObjectMapper MAPPER =
        new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    private TestFileUtils() {
    }

    public static <T> T readFile(final String path, final Class<T> valueType) throws IOException {
        String json = Files.readString(TEST_DIR.resolve(path));
        return MAPPER.readValue(json, valueType);
    }

    public static <T> T readFile(final String path, final TypeReference<T> valueType) throws IOException {
        String json = Files.readString(TEST_DIR.resolve(path));
        return MAPPER.readValue(json, valueType);
    }

    public static String readFile(final String path) {
        try {
            return Files.readString(TEST_DIR.resolve(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file: " + path);
        }
    }
}
