package seven.belog.loans.application;

import seven.belog.loans.domain.Loan;
import seven.belog.loans.domain.PaymentCode;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

public interface LoansService {
    /**
     *
     * @return map: payment date to list of payments
     */
    SortedMap<LocalDate, List<PaymentCode>> getAllPaymentsSummary();

    /**
     *
     * @param allPayments - map of all payments
     * @return map: payment date to the worst payment (with the highest badness) of all loans known
     */
    SortedMap<LocalDate, PaymentCode> getWorstPaymentsSummary(SortedMap<LocalDate, List<PaymentCode>> allPayments);

    /**
     *
     * @param worstPayments - map of the worst payments
     * @return united loan of all the worst payments
     */
    Loan getUnitedLoan(SortedMap<LocalDate, PaymentCode> worstPayments);
}