package hello.lottery;

import hello.lottery.domain.LotteryNumber;
import hello.lottery.domain.LotteryParticipant;
import hello.lottery.domain.PredefinedWinner;
import hello.lottery.dto.LotteryParticipantDto;
import hello.lottery.repository.mybatis.LotteryNumberMapper;
import hello.lottery.repository.mybatis.LotteryParticipantMapper;
import hello.lottery.repository.mybatis.PredefinedWinnerMapper;
import hello.lottery.service.LotteryParticipantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class LotteryParticipantServiceTest {

    @Autowired
    LotteryParticipantService lotteryParticipantService;

    @Autowired
    LotteryNumberMapper lotteryNumberMapper;

    @Autowired
    LotteryParticipantMapper lotteryParticipantMapper;

    @Autowired
    PredefinedWinnerMapper predefinedWinnerMapper;

    private final int THREAD_COUNT = 15000;

    @Test
    void 동시참가() throws InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<Boolean>> futures = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT; i++) {
            String phoneNumber = Integer.toString(i);
            futures.add(executorService.submit(() -> {
                try {
                    return lotteryParticipantService.join(phoneNumber); // 동시 실행할 트랜잭션 메서드
                } catch (Exception e) {
                    e.printStackTrace();
                    return false; // 트랜잭션 충돌 시 false 반환
                }
            }));
        }

        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.SECONDS);

        // 모든 작업의 결과를 확인
        for (Future<Boolean> future : futures) {
            assertThat(future.get()).isTrue(); // 모든 트랜잭션이 정상적으로 처리되었는지 확인
        }

        List<LotteryParticipantDto> lotteryParticipantDtoList = lotteryParticipantMapper.findAll();

        assertEquals(10000, lotteryParticipantDtoList.size(), "총 참가자는 10000명 이어야 한다.");

        for (int i = 0; i < lotteryParticipantDtoList.size(); i++) {
            int order = i + 1;

            Long lotteryNumberId = lotteryParticipantDtoList.get(i).getLotteryNumberId();
            LotteryNumber lotteryNumber = lotteryNumberMapper.findById(lotteryNumberId);

            if(lotteryNumber.getRank() == 1) {
                assertTrue(order >= 4000 && order <= 6000, "1등 당첨자는 4000 이상 6000 이하에 분포되어야 합니다.");
            }
            else if(lotteryNumber.getRank() == 2) {
                assertTrue(order >= 2000 && order <= 7000, "2등 당첨자는 2000 이상 7000 이하에 분포되어야 합니다.");
            }
            else if(lotteryNumber.getRank() == 3) {
                assertTrue(order >= 1000 && order <= 8000, "3등 당첨자는 1000 이상 8000 이하에 분포되어야 합니다.");
            }
        }
    }

    @Test
    void 일등_특정_휴대폰_번호() {
        String phoneNumber = "010-1234-5678";

        PredefinedWinner predefinedWinner = new PredefinedWinner();
        predefinedWinner.setPhoneNumber(phoneNumber);
        predefinedWinnerMapper.save(predefinedWinner);

        lotteryParticipantService.join(phoneNumber);

        LotteryParticipantDto lotteryParticipantDto = lotteryParticipantMapper.findByPhoneNumber(phoneNumber);
        Long lotteryNumberId = lotteryParticipantDto.getLotteryNumberId();
        LotteryNumber lotteryNumber = lotteryNumberMapper.findById(lotteryNumberId);

        assertEquals(1, lotteryNumber.getRank(), "특정 휴대폰 번호는 1등 이어야 한다.");
    }
}
