package com.fuelprices.fuel_ingestor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@ConfigurationProperties(prefix = "ingestor")
@Validated
public record IngestorProps(
    @Valid Source source,
    @Valid Schedule schedule,
    @Valid Topics topics,
    @Valid Limits limits) {
  public record Source(
      @NotBlank String baseUrl,
      @Positive Integer timeoutMs,
      @NotBlank String userAgent,
      @Positive Integer maxSizeInMb,
      String apiKey) {
  }

  public record Schedule(
      @NotBlank String cron) {
  }

  public record Topics(
      @NotBlank String pricesRaw,
      @NotBlank String stationsRaw,
      @NotBlank String dlq) {
  }

  public record Limits(
      @Positive Integer maxBatch) {
  }
}
