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
    private int limit_sum;
    private int wallet_id;

    public Integer getCurrentSum(){
        return current_sum;
    }

    public Integer getLimit(){
        return limit_sum;
    }
}
