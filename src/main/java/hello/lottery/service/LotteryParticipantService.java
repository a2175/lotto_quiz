package hello.lottery.service;

import hello.lottery.domain.LotteryNumber;
import hello.lottery.domain.PredefinedWinner;
import hello.lottery.dto.LotteryParticipantDto;
import hello.lottery.repository.mybatis.LotteryNumberMapper;
import hello.lottery.repository.mybatis.LotteryParticipantMapper;
import hello.lottery.repository.mybatis.PredefinedWinnerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class LotteryParticipantService {

    private final LotteryNumberMapper lotteryNumberMapper;
    private final LotteryParticipantMapper lotteryParticipantMapper;
    private final PredefinedWinnerMapper predefinedWinnerMapper;

    public boolean join(String phoneNumber) {

        List<PredefinedWinner> predefinedWinners = predefinedWinnerMapper.findAll();

        // 1등이 정해진 특정 휴대폰 번호인지 확인
        boolean isPredefinedWinner = predefinedWinners.stream()
                .anyMatch(winner -> phoneNumber.equals(winner.getPhoneNumber()));

        // 1등이 정해진 특정 휴대폰 번호일 경우 새로운 1등 로또 번호를 생성
        if(isPredefinedWinner) {
            List<LotteryNumber> lotteryNumber = lotteryNumberMapper.findByRank(1);
            LotteryNumber firstPrizeNumber = lotteryNumber.get(0);

            LotteryNumber newLotteryNumber = new LotteryNumber();
            newLotteryNumber.setNumbers(firstPrizeNumber.getNumbers());
            newLotteryNumber.setAssigned(true);
            newLotteryNumber.setRank(1);
            lotteryNumberMapper.save(newLotteryNumber);

            LotteryParticipantDto lotteryParticipantDto = new LotteryParticipantDto(
                    newLotteryNumber.getId(), phoneNumber, false, LocalDateTime.now()
            );

            lotteryParticipantMapper.save(lotteryParticipantDto);
        }
        else {
            Optional<LotteryNumber> lotteryNumber = lotteryNumberMapper.selectUnassignedLotteryNumberForUpdate();
            if (lotteryNumber.isPresent()) {
                LotteryParticipantDto lotteryParticipantDto = new LotteryParticipantDto(
                        lotteryNumber.get().getId(), phoneNumber, false, LocalDateTime.now()
                );

                lotteryParticipantMapper.save(lotteryParticipantDto);
                lotteryNumberMapper.setLotteryAssignedTrue(lotteryNumber.get().getId());
            }
        }

        return true;
    }
}