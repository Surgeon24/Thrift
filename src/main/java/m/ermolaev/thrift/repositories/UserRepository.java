package m.ermolaev.thrift.repositories;

import m.ermolaev.thrift.domain.Group;
import m.ermolaev.thrift.domain.User;
import m.ermolaev.thrift.domain.Wallet;
import m.ermolaev.thrift.domain.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(password);
//            String hashedPassword = password;
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

    public User findByUsername(String username){
        User user = jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ? LIMIT 1",
                new Object[]{username},
                BeanPropertyRowMapper.newInstance(User.class));
        return user;
    }

    public void registerUser(String username, String password, String confirm_password) throws Exception {
        if (!password.equals(confirm_password)){
            throw new Exception("Passwords are not matched.");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
//        String hashedPassword = password;
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (count > 0) {
            throw new Exception("User with username " + username + " already exists.");
        }

        sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, username, hashedPassword);
    }

    public User getUser(String nickname){
        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE username = ? LIMIT 1",
                new Object[]{nickname},
                BeanPropertyRowMapper.newInstance(User.class));
    }

    public Integer getUserId(String nickname) {
        return jdbcTemplate.queryForObject("SELECT id FROM users WHERE username = ? LIMIT 1",
                new Object[]{nickname},
                Integer.class);
    }

    public String getUserNickname(Integer id) {
        return jdbcTemplate.queryForObject("SELECT username FROM users WHERE id = ? LIMIT 1",
                new Object[]{id},
                String.class);
    }

    public List<Group> getAllGroups(String nickname){
        User user = getUser(nickname);
        return jdbcTemplate.query("SELECT * FROM group_table WHERE group_table.id IN (SELECT group_id FROM group_user WHERE user_id = ?)",
                    new Object[]{user.getId()},
                    BeanPropertyRowMapper.newInstance(Group.class));
    }

    public List<Wallet> getAllWallets(String nickname){
        User user = getUser(nickname);
        return jdbcTemplate.query("SELECT * FROM wallet WHERE wallet.user_id = ?",
                new Object[]{user.getId()},
                BeanPropertyRowMapper.newInstance(Wallet.class));
    }


    public List<Notification> getNotifications(int id){
        return jdbcTemplate.query("SELECT * FROM notifications WHERE recipient = ?",
                new Object[]{id},
                BeanPropertyRowMapper.newInstance(Notification.class));
    }

}
