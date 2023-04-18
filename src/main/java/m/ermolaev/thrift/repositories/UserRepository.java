package m.ermolaev.thrift.repositories;

import m.ermolaev.thrift.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getAll() {
        return jdbcTemplate.query("SELECT nickname FROM user",
                BeanPropertyRowMapper.newInstance(User.class));
    }

}
