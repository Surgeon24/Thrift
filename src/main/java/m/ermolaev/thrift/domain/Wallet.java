package m.ermolaev.thrift.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('DAY', 'WEEK', 'MONTH', 'YEAR')")
    private Period time_period;

    private int user_id;
    private String status;

    @CreationTimestamp
    private Timestamp created_at;
}
