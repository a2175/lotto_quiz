package hello.lottery;

import hello.lottery.dto.LotteryNumberDto;
import hello.lottery.util.LottoGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LottoGeneratorTest {

    LottoGenerator lottoGenerator = new LottoGenerator();
    List<LotteryNumberDto> lottoNumbers = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        lottoNumbers = lottoGenerator.generateLottoPool();
    }

    @Test
    public void 생성개수() {
        assertEquals(10000, lottoNumbers.size(), "생성개수는 10000개여야 한다.");
    }

    @Test
    public void 자릿수() {
        for (LotteryNumberDto lotteryNumberDto : lottoNumbers) {
            assertEquals(6, lotteryNumberDto.getNumbers().size(), "로또 번호는 6자리 여야 한다.");
        }
    }

    @Test
    public void 당첨() {
        for (LotteryNumberDto lotteryNumberDto : lottoNumbers) {
            Set<Integer> numbers = lotteryNumberDto.getNumbers();
            Set<Integer> baseNumbers = lottoGenerator.getFirstPrizeNumbers();
            int matchCount = getMatchCount(numbers, baseNumbers);
            if(lotteryNumberDto.getRank() == 1) {
                assertEquals(6, matchCount, "1등 당첨자는 6자리가 동일해야 한다.");
            }
            else if(lotteryNumberDto.getRank() == 2) {
                assertEquals(5, matchCount, "2등 당첨자는 5자리가 동일해야 한다.");
            }
            else if(lotteryNumberDto.getRank() == 3) {
                assertEquals(4, matchCount, "3등 당첨자는 4자리가 동일해야 한다.");
            }
            else if(lotteryNumberDto.getRank() == 4) {
                assertEquals(3, matchCount, "4등 당첨자는 3자리가 동일해야 한다.");
            }
            else {
                assertTrue(matchCount >= 0 && matchCount <= 2, "당첨자가 아니면 2자리 이하로 동일해야 한다.");
            }
        }
    }

    @Test
    public void 등수별_당첨자수() {
        int firstPrizeWinner = 0;
        int secondPrizeWinner = 0;
        int thirdPrizeWinner = 0;
        int fourthPrizeWinner = 0;

        for (LotteryNumberDto lotteryNumberDto : lottoNumbers) {
            if(lotteryNumberDto.getRank() == 1) {
                firstPrizeWinner++;
            }
            else if(lotteryNumberDto.getRank() == 2) {
                secondPrizeWinner++;
            }
            else if(lotteryNumberDto.getRank() == 3) {
                thirdPrizeWinner++;
            }
            else if(lotteryNumberDto.getRank() == 4) {
                fourthPrizeWinner++;
            }
        }

        assertEquals(1, firstPrizeWinner, "1등 당첨자는 1명 이어야 한다.");
        assertEquals(5, secondPrizeWinner, "2등 당첨자는 5명 이어야 한다.");
        assertEquals(44, thirdPrizeWinner, "3등 당첨자는 44명 이어야 한다.");
        assertEquals(950, fourthPrizeWinner, "4등 당첨자는 950명 이어야 한다.");
    }

    @Test
    public void 등수별_분배() {
        for (int i = 0; i < lottoNumbers.size(); i++) {
            int order = i + 1;

            if(lottoNumbers.get(i).getRank() == 1) {
                assertTrue(order >= 4000 && order <= 6000, "1등 당첨자는 4000 이상 6000 이하에 분포되어야 합니다.");
            }
            else if(lottoNumbers.get(i).getRank() == 2) {
                assertTrue(order >= 2000 && order <= 7000, "2등 당첨자는 2000 이상 7000 이하에 분포되어야 합니다.");
            }
            else if(lottoNumbers.get(i).getRank() == 3) {
                assertTrue(order >= 1000 && order <= 8000, "3등 당첨자는 1000 이상 8000 이하에 분포되어야 합니다.");
            }
        }
    }

    private int getMatchCount(Set<Integer> numbers, Set<Integer> baseNumbers) {
        int matchCount = 0;
        for (int num : numbers) {
            if (baseNumbers.contains(num)) {
                matchCount++;
            }
        }

        return matchCount;
    }
}