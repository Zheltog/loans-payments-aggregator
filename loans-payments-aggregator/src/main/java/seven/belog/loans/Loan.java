package seven.belog.loans;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Loan {
    private String payments;
    private LocalDate startDate;
}