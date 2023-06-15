package m.ermolaev.thrift.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String nickname;
    private String email;
    private String password_hash;
    private String role;
    private Timestamp created_at;

    public String getUsername() {return nickname;}
    public String getPassword() {
        return password_hash;
    }
    public String getRoles() {
        return role;
    }


}
