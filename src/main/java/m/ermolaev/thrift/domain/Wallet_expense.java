package m.ermolaev.thrift.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Wallet_expense {
    private int id;
    private String title;
    private int current_sum;
    private int limit;
    private int wallet_id;
}
