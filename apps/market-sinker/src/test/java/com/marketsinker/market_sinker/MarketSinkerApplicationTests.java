package com.marketsinker.market_sinker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class MarketSinkerApplicationTests {

	@Test
	void contextLoads() {
	}

}
