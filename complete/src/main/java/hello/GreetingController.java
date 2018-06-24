package hello;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static String time_of_day = "morning";
    private static String template = "Good " + time_of_day + " %s!";
    private final AtomicLong counter = new AtomicLong();

    public static void setTime_of_day(String time_of_day) {
        GreetingController.time_of_day = time_of_day;
    }

    public static void setTemplate(String template) {
        GreetingController.template = template;
    }

    public static String getCurrentTimeUsingDate() {
        Date date = new Date();
        String strDateFormat = "HH:mm:ss a";
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
        String formattedDate= dateFormat.format(date);
        return formattedDate;
    }

    public static void setGreetingTime() {
        int currentHour = Integer.parseInt(getCurrentTimeUsingDate().substring(0,2));
        if(currentHour >= 0 && currentHour < 12){
            setTime_of_day("Morning");
        }else if(currentHour >= 12 && currentHour < 16){
            setTime_of_day("Afternoon");
        }else if(currentHour >= 16 && currentHour < 21){
            setTime_of_day("Evening");
        }else if(currentHour >= 21 && currentHour < 24){
            setTime_of_day("Night");
        }
        setTemplate("Good " + time_of_day + " %s!");
    }

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        setGreetingTime();
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
}
