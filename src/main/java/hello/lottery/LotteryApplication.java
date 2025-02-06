package hello.lottery;

import hello.lottery.repository.LotteryNumberRepository;
import hello.lottery.util.LottoGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LotteryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LotteryApplication.class, args);
	}

	@Bean
	public TestDataInit testDataInit(LottoGenerator lottoGenerator, LotteryNumberRepository lotteryNumberRepository) {
		return new TestDataInit(lottoGenerator, lotteryNumberRepository);
	}

}
