package pl.lewandowski.virtual.threads.post.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
public class PostController {

    private final ApiService apiService;

    public PostController(ApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/pt")
    public String getPt() throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        List<String> strings = apiService.fetchPostTitleWithPlatformThreads();
        long end = System.currentTimeMillis();

        return "Fetched: " + strings.size() + ", in " + (end - start) + " ms.";
    }

    @GetMapping("/vt")
    public String getVt() throws ExecutionException, InterruptedException {

        long start = System.currentTimeMillis();
        List<String> strings = apiService.fetchPostTitleWithPlatformThreads();
        long end = System.currentTimeMillis();

        return "Fetched: " + strings.size() + ", in " + (end - start) + " ms.";
    }


}
