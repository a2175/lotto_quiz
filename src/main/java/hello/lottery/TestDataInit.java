package hello.lottery;

import hello.lottery.domain.LotteryNumber;
import hello.lottery.dto.LotteryNumberDto;
import hello.lottery.repository.LotteryNumberRepository;
import hello.lottery.util.LottoGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class TestDataInit {

    private final LottoGenerator lottoGenerator;
    private final LotteryNumberRepository lotteryNumberRepository;

    /**
     * 확인용 초기 데이터 추가
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initData() {
        log.info("test data init");

        List<LotteryNumberDto> lottoNumbers = lottoGenerator.generateLottoPool();

        for (LotteryNumberDto lottoNumber : lottoNumbers) {
            LotteryNumber lotteryNumber = new LotteryNumber();
            lotteryNumber.setNumbers(
                lottoNumber.getNumbers().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","))
            );
            lotteryNumber.setRank(lottoNumber.getRank());
            lotteryNumber.setAssigned(false);
            lotteryNumberRepository.save(lotteryNumber);
        }
    }

}
