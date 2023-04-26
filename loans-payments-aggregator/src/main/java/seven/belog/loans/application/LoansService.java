package seven.belog.loans.application;

import seven.belog.loans.domain.Loan;
import seven.belog.loans.domain.PaymentCode;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

public interface LoansService {
    SortedMap<LocalDate, List<PaymentCode>> getAllPaymentsSummary();
    SortedMap<LocalDate, PaymentCode> getWorstPaymentsSummary();
    Loan getUnitedLoan();
}