package hello.lottery.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LotteryNumber {

    @Id @GeneratedValue
    @Column(name = "lottery_number_id")
    private Long id;

    private String numbers;
    private int rank;
    private boolean assigned;
}
