package hello.lottery.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(name = "uk_phone_number", columnNames = "phoneNumber")
})
@Getter
@Setter
public class LotteryParticipant {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lottery_participant_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lottery_number_id")
    private LotteryNumber lotteryNumber;

    private String phoneNumber;
    private boolean assigned;
    private LocalDateTime createdAt;
}
