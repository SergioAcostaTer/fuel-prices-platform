package com.fuelprices.fuel_ingestor.ingest;

import java.net.URI;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fuelprices.fuel_ingestor.config.IngestorProps;
import com.fuelprices.fuel_ingestor.http.FuelApiClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@EnableScheduling
@RequiredArgsConstructor
@Slf4j
public class IngestJob {

  private final FuelApiClient api;
  private final IngestorProps props;

  @Scheduled(cron = "${ingestor.schedule.cron}")
  public void runOnce() {
    var result = api.fetchRaw().block();

    System.out.println("Fetched data from " + sanitizeUrl(props.source.baseUrl) + ": " + result);

  }

  private static String sanitizeUrl(String url) {
    try {
      return URI.create(url).toString();
    } catch (Exception e) {
      return url;
    }
  }

}
