package m.ermolaev.thrift.repositories;

import m.ermolaev.thrift.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getAll() {
        return jdbcTemplate.query("SELECT username FROM users",
                BeanPropertyRowMapper.newInstance(User.class));
    }

    public User findByUsernameAndPassword(String username, String password) {
        try {
//            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//            String hashedPassword = passwordEncoder.encode(password);
            String hashedPassword = password;
            System.out.println("password: " + password);
            System.out.println("hashedpassword: " + hashedPassword);
            User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1",
                    new Object[]{username, hashedPassword},
                    BeanPropertyRowMapper.newInstance(User.class));
            return user;
        } catch (EmptyResultDataAccessException ex) {
            return null; // handle the case when no user was found
        }
    }

    public void registerUser(String username, String password) throws Exception {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = passwordEncoder.encode(password);
        String hashedPassword = password;
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (count > 0) {
            throw new Exception("User with username " + username + " already exists.");
        }

        sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, username, hashedPassword);
    }



}
