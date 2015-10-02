package rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Привет, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    /**
     * Сложение двух целых чисел
     * Пример запроса: http://localhost:8080/add?a=3&b=10
     * Пример ответа:  3 + 10 = 13
     *
     * @param a первое слагаемое
     * @param b второе слагаемое
     * @return сумма
     */
    @RequestMapping("/add")
    public String greeting(@RequestParam(value = "a", defaultValue = "2") int a,
                           @RequestParam(value = "b", defaultValue = "2") int b) {
        return String.format("%d + %d = %d", a, b, a + b);
    }
}
