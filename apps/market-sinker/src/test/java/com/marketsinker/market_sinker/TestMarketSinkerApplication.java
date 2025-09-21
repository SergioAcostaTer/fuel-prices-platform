package com.marketsinker.market_sinker;

import org.springframework.boot.SpringApplication;

import com.fuelprices.market_sinker.MarketSinkerApplication;

public class TestMarketSinkerApplication {

	public static void main(String[] args) {
		SpringApplication.from(MarketSinkerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
