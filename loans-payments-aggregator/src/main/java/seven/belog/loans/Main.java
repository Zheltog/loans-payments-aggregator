package seven.belog.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);

        var aggregator = context.getBean(Aggregator.class);
        var loans = aggregator.getAllLoans();
        loans.forEach(System.out::println);
    }
}