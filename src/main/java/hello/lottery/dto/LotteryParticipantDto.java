package hello.lottery.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class LotteryParticipantDto {

    private Long id;
    private Long lotteryNumberId;
    private String phoneNumber;
    private boolean assigned;
    private LocalDateTime createdAt;

    public LotteryParticipantDto(Long lotteryNumberId, String phoneNumber, boolean assigned, LocalDateTime createdAt) {
        this.lotteryNumberId = lotteryNumberId;
        this.phoneNumber = phoneNumber;
        this.assigned = assigned;
        this.createdAt = createdAt;
    }
}
