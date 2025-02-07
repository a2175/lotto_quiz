package hello.lottery.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PredefinedWinner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "predefined_winner_id")
    private Long id;

    private String phoneNumber;
}
