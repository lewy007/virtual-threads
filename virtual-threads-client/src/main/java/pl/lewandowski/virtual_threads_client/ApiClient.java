package pl.lewandowski.virtual_threads_client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class ApiClient {

    public static final Logger LOGGER = LoggerFactory.getLogger(ApiClient.class);

    private final RestClient restClient;

    public ApiClient(RestClient.Builder restClient) {
        this.restClient = restClient.baseUrl("http://localhost:8080").build();
    }

    @GetMapping("/client")
    public String get() {

        LOGGER.info("Running on {}", Thread.currentThread());
        return restClient.get().uri("/hello").retrieve().toEntity(String.class).getBody();

    }
}
