package pl.lewandowski.virtual.threads.post.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ApiService {

    public static final Logger LOGGER = LoggerFactory.getLogger(ApiService.class);

    private final RestClient restClient;

    public ApiService(RestClient.Builder restClient) {
        this.restClient = restClient.baseUrl("https://jsonplaceholder.typicode.com").build();
    }

    // Platform Threads
    public List<String> fetchPostTitleWithPlatformThreads() throws ExecutionException, InterruptedException {

        // Tworzymy pule 10 watkow (threads)
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        //Lista zadan do wykonania
        List<Future<String>> futures = new ArrayList<>();
        //Lista tytulow (titles)
//        List<String> titles = new ArrayList<>();

        // dodajemy 100 wykonan metody fetchPostTitle() do Listy futures
        // i od razu wyciagamy title z future i dodajemy do listy title
        for (int i = 0; i < 999; i++) {
            futures.add(executorService.submit(this::fetchPostTitle));
//            titles.add(futures.get(i).get());
        }

        // bierzemy z kazdego zadania title i dodajemy do nowej listy
        List<String> titles = new ArrayList<>();
        for (Future<String> future : futures) {
            titles.add(future.get());
        }

        executorService.shutdown();

        return titles;
    }

    // Virtual Threads

    public List<String> fetchPostTitleWithVirtualThreads() throws ExecutionException, InterruptedException {

        // Tworzymy pule 10 watkow (threads)
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();

        //Lista zadan do wykonania
        List<Future<String>> futures = new ArrayList<>();
        //Lista tytulow (titles)
//        List<String> titles = new ArrayList<>();

        // dodajemy 100 wykonan metody fetchPostTitle() do Listy futures
        // i od razu wyciagamy title z future i dodajemy do listy title
        for (int i = 0; i < 999; i++) {
            futures.add(executorService.submit(this::fetchPostTitle));
//            titles.add(futures.get(i).get());
        }

        // bierzemy z kazdego zadania title i dodajemy do nowej listy
        List<String> titles = new ArrayList<>();
        for (Future<String> future : futures) {
            titles.add(future.get());
        }

        executorService.shutdown();

        return titles;
    }


    //Pobieranie z API pierwszego elementu 100 razy

    @GetMapping("/client")
    public String fetchPostTitle() {

        LOGGER.info("Running on {}", Thread.currentThread());
        Post post = restClient.get().uri("/posts/1").retrieve().toEntity(Post.class).getBody();

        return Objects.requireNonNull(post).getTitle();
    }
}
