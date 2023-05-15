package m.ermolaev.thrift.repositories;

import m.ermolaev.thrift.domain.Wallet;
import m.ermolaev.thrift.domain.Wallet_expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WalletRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Wallet_expense> getAllExpenses(Integer id) {
        System.out.println("id = " + id);
        return jdbcTemplate.query("SELECT * FROM wallet_expense WHERE wallet_id = ?",
                new Object[]{id},
                BeanPropertyRowMapper.newInstance(Wallet_expense.class));
    }
}