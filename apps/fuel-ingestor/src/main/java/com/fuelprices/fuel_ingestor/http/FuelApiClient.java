package com.fuelprices.fuel_ingestor.http;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FuelApiClient {

  @Qualifier("fuelWebClient")
  private final WebClient client;

  public Mono<String> fetchRaw() {
    return client.get()
        .accept(MediaType.ALL)
        .retrieve()
        .bodyToMono(String.class);
  }
}
