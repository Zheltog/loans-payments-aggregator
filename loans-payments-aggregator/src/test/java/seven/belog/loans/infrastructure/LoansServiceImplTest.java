package seven.belog.loans.infrastructure;

import org.junit.jupiter.api.Test;
import seven.belog.loans.application.LoansRepository;
import seven.belog.loans.domain.Loan;
import seven.belog.loans.domain.PaymentCode;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class LoansServiceImplTest {

    private final LoansRepository repository = mock(LoansRepository.class);

    private final LoansServiceImpl service = new LoansServiceImpl(repository);;

    private final SortedMap<LocalDate, List<PaymentCode>> allPayments = allPayments();

    private final SortedMap<LocalDate, PaymentCode> worstPayments = worstPayments();

    private final Loan unitedLoan = unitedLoan();

    @Test
    void getAllPayments() {
        when(repository.getAllLoans()).thenReturn(List.of(
                new Loan("111111", LocalDate.of(2020, 1, 1)),
                new Loan("32A10", LocalDate.of(2020, 3, 1)),
                new Loan("111", LocalDate.of(2020, 10, 1))
        ));

        var result = service.getAllPaymentsSummary();

        assertThat(result).hasSize(10);
        assertThat(result).isEqualTo(allPayments);
    }

    @Test
    void getWorstPayments() {
        var result = service.getWorstPaymentsSummary(allPayments);

        assertThat(result).hasSize(10);
        assertThat(result).isEqualTo(worstPayments);
    }

    @Test
    void getUnitedLoan() {
        var result = service.getUnitedLoan(worstPayments);
        assertThat(result).isEqualTo(unitedLoan);
    }

    private SortedMap<LocalDate, List<PaymentCode>> allPayments() {
        SortedMap<LocalDate, List<PaymentCode>> map = new TreeMap<>();
        map.put(LocalDate.of(2020,1, 1), List.of(PaymentCode.of('1')));
        map.put(LocalDate.of(2020,2, 1), List.of(PaymentCode.of('1')));
        map.put(LocalDate.of(2020,3, 1), List.of(PaymentCode.of('1'), PaymentCode.of('0')));
        map.put(LocalDate.of(2020,4, 1), List.of(PaymentCode.of('1'), PaymentCode.of('1')));
        map.put(LocalDate.of(2020,5, 1), List.of(PaymentCode.of('1'), PaymentCode.of('A')));
        map.put(LocalDate.of(2020,6, 1), List.of(PaymentCode.of('1'), PaymentCode.of('2')));
        map.put(LocalDate.of(2020,7, 1), List.of(PaymentCode.of('3')));
        map.put(LocalDate.of(2020,10, 1), List.of(PaymentCode.of('1')));
        map.put(LocalDate.of(2020,11, 1), List.of(PaymentCode.of('1')));
        map.put(LocalDate.of(2020,12, 1), List.of(PaymentCode.of('1')));
        return map;
    }

    private SortedMap<LocalDate, PaymentCode> worstPayments() {
        SortedMap<LocalDate, PaymentCode> map = new TreeMap<>();
        map.put(LocalDate.of(2020,1, 1), PaymentCode.of('1'));
        map.put(LocalDate.of(2020,2, 1), PaymentCode.of('1'));
        map.put(LocalDate.of(2020,3, 1), PaymentCode.of('0'));
        map.put(LocalDate.of(2020,4, 1), PaymentCode.of('1'));
        map.put(LocalDate.of(2020,5, 1), PaymentCode.of('A'));
        map.put(LocalDate.of(2020,6, 1), PaymentCode.of('2'));
        map.put(LocalDate.of(2020,7, 1), PaymentCode.of('3'));
        map.put(LocalDate.of(2020,10, 1), PaymentCode.of('1'));
        map.put(LocalDate.of(2020,11, 1), PaymentCode.of('1'));
        map.put(LocalDate.of(2020,12, 1), PaymentCode.of('1'));
        return map;
    }

    private Loan unitedLoan() {
        return new Loan("111XX32A1011", LocalDate.of(2020, 1, 1));
    }
}
