package m.ermolaev.thrift.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group_expense {
    private int id;
    private String title;
    private int sum;
    private int payer_id;
    private int group_id;
}
