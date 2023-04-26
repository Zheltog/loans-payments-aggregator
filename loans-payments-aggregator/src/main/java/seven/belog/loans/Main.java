package seven.belog.loans;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import seven.belog.loans.application.LoansService;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication
            .run(Main.class, args)
            .getBean(LoansService.class)
            .getUnitedLoan();
    }
}