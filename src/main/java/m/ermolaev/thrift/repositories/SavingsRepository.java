package m.ermolaev.thrift.repositories;

import m.ermolaev.thrift.domain.Savings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SavingsRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Savings> getAllInstruments(String nickname) {
        return jdbcTemplate.query("SELECT * FROM savings WHERE user_id = (SELECT id FROM users WHERE username = ?);",
                new Object[]{nickname},
                BeanPropertyRowMapper.newInstance(Savings.class));
    }

    public void addInvestment(Integer id, String title, String description, Integer amount) {
        System.out.println("trying to add investment...\n");
        String sql = "INSERT INTO savings (user_id, title, description, amount) VALUES (?, ?, ?, ?);";
        jdbcTemplate.update(sql, id, title, description, amount);
        System.out.println("success!\n");
    }

    public Savings getInvestment(Integer id){
        return jdbcTemplate.queryForObject("SELECT * FROM savings WHERE id = ?;",
                new Object[]{id},
                BeanPropertyRowMapper.newInstance(Savings.class));
    }

    public void deleteInvestment(Integer id) {
        String sql = "DELETE FROM savings WHERE id = ?;";
        jdbcTemplate.update(sql, id);
    }

    public void updateInvestment(Integer user_id, String title, String description, Integer amount, Integer id) {
        System.out.println("Trying to update an investment...\n");
        String sql = "UPDATE savings SET user_id = ?, title = ?, description = ?, amount = ? WHERE id = ?;";
        jdbcTemplate.update(sql, user_id, title, description, amount, id);
        System.out.println("Success!\n");
    }


}