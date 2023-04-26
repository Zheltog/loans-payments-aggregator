package seven.belog.loans.application;

import seven.belog.loans.domain.Loan;
import java.util.List;

public interface LoansQueryService {
    List<Loan> getAllLoans();
}