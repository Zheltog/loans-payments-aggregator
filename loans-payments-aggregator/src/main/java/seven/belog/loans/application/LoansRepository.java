package seven.belog.loans.application;

import seven.belog.loans.domain.Loan;
import java.util.List;

public interface LoansRepository {

    /**
     *
     * @return all loans info present in database
     */
    List<Loan> getAllLoans();
}