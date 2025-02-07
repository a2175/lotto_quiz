package hello.lottery.repository;

import hello.lottery.domain.LotteryNumber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotteryNumberRepository extends JpaRepository<LotteryNumber, Long> {
}
