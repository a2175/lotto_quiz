package hello.lottery.repository;

import hello.lottery.domain.LotteryParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotteryParticipantRepository extends JpaRepository<LotteryParticipant, Long> {
}
