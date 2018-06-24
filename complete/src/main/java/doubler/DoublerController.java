package doubler;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class DoublerController {

    private static double calculation = 0;
    private static String template_double = "You wanted to double the integer" + " %s" + ", the result is: ";
    private final AtomicLong counter = new AtomicLong();

    public double doubling(double i) {
        return i+i;
    }

    public static void setCalculation(double calculation) {
        DoublerController.calculation = calculation;
    }

    public static double getCalculation() {
        return calculation;
    }

    @RequestMapping("/doubler")
    public Doubler calculate(@RequestParam(value="expression", defaultValue="No doubling requested!") String expression) {
        setCalculation(doubling(Double.parseDouble(expression)));
        return new Doubler(counter.incrementAndGet(),
                            String.format(template_double + getCalculation(), expression));
    }
}
