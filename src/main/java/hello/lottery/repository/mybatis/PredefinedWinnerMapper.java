package hello.lottery.repository.mybatis;

import hello.lottery.domain.PredefinedWinner;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PredefinedWinnerMapper {

    List<PredefinedWinner> findAll();

    void save(PredefinedWinner predefinedWinners);
}
