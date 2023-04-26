package seven.belog.loans.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import seven.belog.loans.application.LoansQueryService;
import seven.belog.loans.application.LoansService;
import seven.belog.loans.domain.Loan;
import seven.belog.loans.domain.Payment;
import seven.belog.loans.domain.PaymentCode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class LoansServiceImpl implements LoansService {

    private final LoansQueryService queryService;

    private final Logger logger = LoggerFactory.getLogger(LoansServiceImpl.class);

    public LoansServiceImpl(@Autowired LoansQueryService queryService) {
        this.queryService = queryService;
    }

    @Override
    public Loan getUnitedLoan() {
        logger.info("Counting united loan...");

        SortedMap<LocalDate, PaymentCode> worstSummary = getWorstPaymentsSummary();

        if (worstSummary.isEmpty()) {
            return null;
        }

        LocalDate startDate = worstSummary.firstKey();
        LocalDate nextDate = startDate;
        StringBuilder payments = new StringBuilder();

        for (Map.Entry<LocalDate, PaymentCode> entry: worstSummary.entrySet()) {
            LocalDate date = entry.getKey();
            PaymentCode code = entry.getValue();

            if (!date.equals(nextDate)) {
                var monthsSkipped = ChronoUnit.MONTHS.between(nextDate, date);
                for (int i = 1; i <= monthsSkipped; i++) {
                    payments.append(PaymentCode.noData().getValue());
                    logger.info("Worst payment for date {} is '{}'", date.plusMonths(i), PaymentCode.noData().getLocalization());
                }
            }

            payments.append(code.getValue());
            logger.info("Worst payment for date {} is '{}'", date, code.getLocalization());

            nextDate = date.plusMonths(1);
        }

        Loan result = new Loan(payments.reverse().toString(), startDate);

        logger.info("Result united loan: payments = {}, start date = {}", result.getPayments(), result.getStartDate());

        return result;
    }

    @Override
    public SortedMap<LocalDate, PaymentCode> getWorstPaymentsSummary() {
        SortedMap<LocalDate, PaymentCode> worstSummary = new TreeMap<>();

        getAllPaymentsSummary().forEach((key, value) -> {
            PaymentCode worstPayment = value.stream().max(PaymentCode::compareTo).orElseThrow(IllegalStateException::new);
            worstSummary.put(key, worstPayment);
        });

        return worstSummary;
    }

    @Override
    public SortedMap<LocalDate, List<PaymentCode>> getAllPaymentsSummary() {
        var allLoans = queryService.getAllLoans();

        List<Payment> allPayments = allLoans
                .stream()
                .map(this::getPaymentsOf)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        SortedMap<LocalDate, List<PaymentCode>> summary = new TreeMap<>();

        allPayments.forEach(payment -> {
            summary.putIfAbsent(payment.getDate(), new LinkedList<>());
            summary.get(payment.getDate()).add(payment.getCode());
        });

        return summary;
    }

    private List<Payment> getPaymentsOf(Loan loan) {
        List<Payment> payments = new LinkedList<>();

        LocalDate startDate = loan.getStartDate();
        char[] codeChars = loan.getPayments().toCharArray();
        int codeCharsLength = codeChars.length;

        for (int i = codeCharsLength - 1; i >= 0; i--) {
            PaymentCode code = PaymentCode.of(codeChars[i]);
            int monthsToAdd = codeCharsLength - 1 - i;
            LocalDate date = startDate.plusMonths(monthsToAdd);
            payments.add(new Payment(code, date));
        }

        return payments;
    }
}
