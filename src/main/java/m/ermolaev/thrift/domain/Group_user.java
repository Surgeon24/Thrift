package m.ermolaev.thrift.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group_user {
    private int group_id;
    private int user_id;
}
