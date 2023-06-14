package m.ermolaev.thrift.repositories;

import m.ermolaev.thrift.domain.Group_expense;
import m.ermolaev.thrift.domain.Wallet_expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class GroupRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Integer countMembers(int id) {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM group_user WHERE group_id = ?",
                new Object[]{id}, Integer.class);
    }

    public void addGroup(Integer id, String title, String description){
        System.out.println("trying to add group...\n");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO group_table (title, description) VALUES (?, ?)";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, title);
            ps.setString(2, description);
            return ps;
        }, keyHolder);

        int groupId = keyHolder.getKey().intValue();

        String groupUserSql = "INSERT INTO group_user (group_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(groupUserSql, groupId, id);

        System.out.println("success!\n");
    }

    public List<Group_expense> getAllExpenses(Integer id) {
        return jdbcTemplate.query("SELECT * FROM group_expense WHERE group_id = ?",
                new Object[]{id},
                BeanPropertyRowMapper.newInstance(Group_expense.class));
    }

    public String getTitle(int id) {
        return jdbcTemplate.queryForObject("SELECT title FROM group_table WHERE id = ?",
                new Object[]{id}, String.class);
    }

    public List<Integer> getAllDebtsInTheGroup(Integer group_id, Integer user_id){
        return jdbcTemplate.queryForList("SELECT ammount FROM user_in_group_expense WHERE group_expense_id = ? and user_id = ?",
                new Object[]{group_id, user_id}, Integer.class);
    }

    public Integer getDebt(Integer group_id, Integer user_id) {
        try {
            Integer result = jdbcTemplate.queryForObject(
                    "SELECT ammount FROM user_in_group_expense WHERE group_expense_id = ? and user_id = ? LIMIT 1",
                    new Object[]{group_id, user_id}, Integer.class);
            return result != null ? result : 0;
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
    }

}