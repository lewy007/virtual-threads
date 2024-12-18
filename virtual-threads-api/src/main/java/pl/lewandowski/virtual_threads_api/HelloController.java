package pl.lewandowski.virtual_threads_api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String get() throws InterruptedException {

        Thread.sleep(3000);
        return "Hello World";
    }
}
