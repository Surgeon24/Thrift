package m.ermolaev.thrift.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Savings {
    private int id;
    private int user_id;
    private String title;
    private String description;
    private int sum;
    private Timestamp created_at;
}
