package com.fuelprices.fuel_ingestor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.fuelprices.fuel_ingestor.config.IngestorProps;

@SpringBootApplication
@EnableConfigurationProperties(IngestorProps.class)
@EnableScheduling
public class FuelIngestorApplication {

  public static void main(String[] args) {
    SpringApplication.run(FuelIngestorApplication.class, args);
  }

}
