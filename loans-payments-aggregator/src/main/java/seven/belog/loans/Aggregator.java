package seven.belog.loans;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
class Aggregator {

    private final JdbcTemplate jdbcTemplate;

    private final Logger logger = LoggerFactory.getLogger(Aggregator.class);

    public Aggregator(@Autowired JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Loan> getAllLoans() {
        List<Loan> loans = List.of();

        var mapper = new RowMapper<Loan>() {
            @Override
            public Loan mapRow(ResultSet rs, int rowNum) throws SQLException {
                var loan = new Loan();
                loan.setPayments(rs.getString("payments"));
                loan.setStartDate(rs.getDate("start_date").toLocalDate());
                return loan;
            }
        };

        try {
            loans = jdbcTemplate.query("select * from loans.loans", mapper);
        } catch (DataAccessException e) {
            logger.error(e.getMessage());
        }

        return loans;
    }
}