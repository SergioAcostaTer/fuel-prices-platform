package com.fuelprices.fuel_ingestor.ingest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fuelprices.fuel_ingestor.http.FuelApiClient;
import com.fuelprices.fuel_ingestor.model.external.response.FuelApiResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class IngestController {

  private final FuelApiClient api;

  @GetMapping("/fuel")
  public Mono<FuelApiResponse> fetchFuel() {
    return api.fetchRaw();
  }
}
