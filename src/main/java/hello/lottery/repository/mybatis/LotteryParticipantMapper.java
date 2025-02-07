package hello.lottery.repository.mybatis;

import hello.lottery.domain.LotteryParticipant;
import hello.lottery.dto.LotteryParticipantDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface LotteryParticipantMapper {

    LotteryParticipantDto findByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    List<LotteryParticipantDto> findAll();

    void save(LotteryParticipantDto lotteryParticipantDto);
}
