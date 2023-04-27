package seven.belog.loans.infrastructure;

import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import seven.belog.loans.domain.Loan;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class LoansRepositoryImplTest {

    private final JdbcTemplate jdbcTemplate = mock(JdbcTemplate.class);

    private final LoansRepositoryImpl repository = new LoansRepositoryImpl(jdbcTemplate);

    @Test
    void getAllLoans() {
        List<Loan> expected = List.of(
            new Loan("111111", LocalDate.of(2020, 1, 1)),
            new Loan("32A10", LocalDate.of(2020, 3, 1)),
            new Loan("111", LocalDate.of(2020, 10, 1))
        );

        when(jdbcTemplate.query(any(String.class), any(RowMapper.class))).thenReturn(expected);

        assertThat(repository.getAllLoans()).isEqualTo(expected);
        verify(jdbcTemplate, times(1)).query(any(String.class), any(RowMapper.class));
    }
}
