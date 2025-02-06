package hello.lottery.util;

import hello.lottery.dto.LotteryNumberDto;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LottoGenerator {

    private static final int TOTAL_PLAYERS = 10000;
    private static final int LOTTO_SIZE = 6;
    private static final int NUMBER_RANGE = 45;

    private Set<Integer> firstPrizeNumbers = new HashSet<>();

    public List<LotteryNumberDto> generateLottoPool() {
        Random random = new Random();
        Set<Set<Integer>> lottoSet = new HashSet<>();
        List<LotteryNumberDto> lottoList = new ArrayList<>(Collections.nCopies(TOTAL_PLAYERS, null));

        // 1등 당첨 번호 생성
        firstPrizeNumbers = generateLottoNumbers(random);

        // 1등 위치 설정 (4000~6000 사이)
        int firstPrizeIndex = random.nextInt(2000) + 4000;
        lottoList.set(firstPrizeIndex, new LotteryNumberDto(firstPrizeNumbers, 1));
        lottoSet.add(firstPrizeNumbers);

        // 2등 배치 (5개 동일)
        placeWinners(lottoList, lottoSet, firstPrizeNumbers, 2, 5, 5, 2000, 7000, random);

        // 3등 배치 (4개 동일)
        placeWinners(lottoList, lottoSet, firstPrizeNumbers, 3, 4, 44, 1000, 8000, random);

        // 4등 배치 (3개 동일)
        placeWinners(lottoList, lottoSet, firstPrizeNumbers, 4, 3, 950, 0, TOTAL_PLAYERS, random);

        // 나머지 번호 채우기
        for (int i = 0; i < TOTAL_PLAYERS; i++) {
            if (lottoList.get(i) == null) {
                while (true) {
                    Set<Integer> newNumbers = generateLottoNumbers(random);
                    if (!lottoSet.contains(newNumbers) && !isWinner(newNumbers, firstPrizeNumbers)) {
                        lottoList.set(i, new LotteryNumberDto(newNumbers, 0));
                        lottoSet.add(newNumbers);
                        break;
                    }
                }
            }
        }

        return lottoList;
    }

    private void placeWinners(List<LotteryNumberDto> lottoList, Set<Set<Integer>> lottoSet,
                              Set<Integer> baseNumbers, int rank, int matchCount, int numWinners,
                              int minIndex, int maxIndex, Random random) {
        Set<Integer> usedIndexes = new HashSet<>();
        while (usedIndexes.size() < numWinners) {
            int index = random.nextInt(maxIndex - minIndex) + minIndex;
            if (lottoList.get(index) != null) continue;

            Set<Integer> newNumbers = new HashSet<>(baseNumbers);
            List<Integer> baseList = new ArrayList<>(baseNumbers);
            Collections.shuffle(baseList, random);

            // 기존 번호 중 matchCount 개만 유지
            newNumbers.retainAll(new HashSet<>(baseList.subList(0, matchCount)));

            // 나머지 숫자 랜덤 채우기
            while (newNumbers.size() < LOTTO_SIZE) {
                int newNum = random.nextInt(NUMBER_RANGE) + 1;
                if (!baseNumbers.contains(newNum)) {
                    newNumbers.add(newNum);
                }
            }

            if (!lottoSet.contains(newNumbers)) {
                lottoList.set(index, new LotteryNumberDto(newNumbers, rank));
                lottoSet.add(newNumbers);
                usedIndexes.add(index);
            }
        }
    }

    private boolean isWinner(Set<Integer> numbers, Set<Integer> firstPrizeNumbers) {
        int matchCount = 0;
        for (int num : numbers) {
            if (firstPrizeNumbers.contains(num)) {
                matchCount++;
            }
        }

        return matchCount >= 3; // 3개 이상 일치하면 당첨자로 간주
    }

    private Set<Integer> generateLottoNumbers(Random random) {
        Set<Integer> numbers = new HashSet<>();
        while (numbers.size() < LOTTO_SIZE) {
            numbers.add(random.nextInt(NUMBER_RANGE) + 1);
        }
        return numbers;
    }

    public Set<Integer> getFirstPrizeNumbers() {
        return firstPrizeNumbers;
    }
}
