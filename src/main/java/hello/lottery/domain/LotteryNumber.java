package hello.lottery.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LotteryNumber {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lottery_number_id")
    private Long id;

    private String numbers;
    private int rank;
    private boolean assigned;
}
