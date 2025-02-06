package hello.lottery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class LotteryNumberDto {

    private Set<Integer> numbers = new HashSet<>();
    private int rank;
}
