package cl.tenpo.learning.reactive.tasks.task2.testConfig;

import cl.tenpo.learning.reactive.tasks.task2.testConfig.util.TestFileUtils;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.HttpStatus;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.io.IOException;

@TestConfiguration
public class MockServerConfig {

    private static final int PORT = 52777;
    private static MockWebServer mockServer;

    public MockServerConfig() {
        mockServer = new MockWebServer();
    }

    @PostConstruct
    void initialize() throws IOException {
        mockServer.start(PORT);
        mockServer.setDispatcher(getDispatcher());
    }

    @PreDestroy
    void terminate() throws IOException {
        mockServer.shutdown();
    }

    private static Dispatcher getDispatcher() {
        return new Dispatcher() {
            @Override
            public @NotNull MockResponse dispatch(final @NotNull RecordedRequest request) throws InterruptedException {
                String path = request.getPath();
                assert path != null;

                if (path.contains("/learning-reactive/external-api/percentage")) {
                    return new MockResponse()
                            .addHeader("Content-Type", "application/json; charset=utf-8")
                            .setBody(TestFileUtils.readFile("dto/percentage_response.json"))
                            .setResponseCode(HttpStatus.OK.value());
                }

                return new MockResponse().setResponseCode(HttpStatus.NOT_FOUND.value());
            }
        };
    }
}
