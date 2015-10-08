package rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Привет, %s!";
    private final AtomicLong counter = new AtomicLong();

    /**
     * http://localhost:8080/
     *
     * @return GREAT, Application started! :)
     */
    @RequestMapping("/")
    public String index() {
        return "GREAT, Application started! :)";
    }

    @RequestMapping(value = "/greeting", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
    public Greeting greeting(@RequestParam(value = "name",
            defaultValue = "World") String name) {
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
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(@RequestParam(value = "a", defaultValue = "2") int a,
                      @RequestParam(value = "b", defaultValue = "2") int b) {
        return String.format("%d + %d = %d", a, b, a + b);
    }

    /**
     * Сложение двух целых чисел
     * Пример запроса: http://localhost:8080/add/3/10
     * Пример ответа:  3 + 10 = 13
     *
     * @param as первое слагаемое
     * @param bs второе слагаемое
     * @return сумма
     */
    @RequestMapping(value = "/add/{a}/{b}")
    public String add2(@PathVariable(value = "a") String as,
                       @PathVariable(value = "b") String bs) throws IOException {
        int a, b;
        try {
            a = Integer.parseInt(as);
        } catch (NumberFormatException e) {
            return "Первое значение '" + as + "' не является целым числом";
        }
        try {
            b = Integer.parseInt(bs);
        } catch (NumberFormatException e) {
            return "Второе значение '" + bs + "' не является целым числом";
        }
        String res = String.format("%d + %d = %d", a, b, a + b);
        try (PrintWriter writer = new PrintWriter(new FileWriter("log.txt", true))) {
            writer.println(res);
        }
        return res;
    }

    @RequestMapping(value = "/search/{query}")
    public List<String> search(@PathVariable(value = "query") String query) {
        List<String> list = new ArrayList<>();
        list.add("Поиск по строке '" + query + "'");
        list.add("Найдено 1");
        list.add("Найдено 2");
        return list;
    }
}
