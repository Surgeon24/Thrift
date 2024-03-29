package m.ermolaev.thrift.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
    private int id;
    private String title;
    private String description;
    private Timestamp created_at;
    private String code;

    public int getId() {
        return id;
    }
    public String getTitle(){
        return title;
    }
}
