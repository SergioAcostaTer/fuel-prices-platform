package com.fuelprices.market_sinker;

import org.springframework.boot.SpringApplication;

public class TestMarketSinkerApplication {

  public static void main(String[] args) {
    SpringApplication.from(MarketSinkerApplication::main).with(TestcontainersConfiguration.class).run(args);
  }

}
