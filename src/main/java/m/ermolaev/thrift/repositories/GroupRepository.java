package m.ermolaev.thrift.repositories;

import org.springframework.beans.factory.annotation.Autowired;
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
}