package m.ermolaev.thrift.repositories;

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
        return jdbcTemplate.query("SELECT * FROM wallet_expense WHERE wallet_id = ?",
                new Object[]{id},
                BeanPropertyRowMapper.newInstance(Wallet_expense.class));
    }

    public void addWallet(Integer id, String title, String time_period){
        System.out.println("trying to add wallet...\n");
        String sql = "INSERT INTO wallet (user_id, title, time_period) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, id, title, time_period);
        System.out.println("success!\n");
    }

    public Integer getSpends(List<Wallet_expense> expenses){
        Integer spends = 0;
        for (Wallet_expense expense : expenses){
            spends += expense.getCurrentSum();
        }
        return spends;
    }

    public Integer getLimits(List<Wallet_expense> expenses){
        Integer limits = 0;
        for (Wallet_expense expense : expenses){
            limits += expense.getLimit();
        }
        return limits;
    }

    public void addExpense(int id, String title, int limit){
        String joinSql = "INSERT INTO wallet_expense (wallet_id, title, limit_sum, current_sum) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(joinSql, id, title, limit, 0);

        System.out.println("Success!\n");
    }
}