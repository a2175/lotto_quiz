package hello.lottery.repository.mybatis;

import hello.lottery.domain.LotteryNumber;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface LotteryNumberMapper {

    void save(LotteryNumber lotteryNumber);

    List<LotteryNumber> findAll();

    LotteryNumber findById(@Param("id") Long id);

    List<LotteryNumber> findByRank(@Param("rank") int rank);

    Optional<LotteryNumber> selectUnassignedLotteryNumberForUpdate();

    void setLotteryAssignedTrue(@Param("id") Long id);
}
