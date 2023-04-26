package seven.belog.loans.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Payment {
    private PaymentCode code;
    private LocalDate date;
}