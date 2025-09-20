package com.fuelprices.fuel_ingestor;

import org.springframework.boot.SpringApplication;

public class TestFuelIngestorApplication {

	public static void main(String[] args) {
		SpringApplication.from(FuelIngestorApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
