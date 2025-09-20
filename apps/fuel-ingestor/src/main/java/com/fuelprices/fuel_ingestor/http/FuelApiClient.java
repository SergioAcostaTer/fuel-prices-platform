package com.fuelprices.fuel_ingestor.http;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.fuelprices.fuel_ingestor.model.external.response.FuelApiResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FuelApiClient {

  @Qualifier("fuelWebClient")
  private final WebClient client;

  public Mono<FuelApiResponse> fetchRaw() {
    return client.get()
        .uri("/")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(FuelApiResponse.class)
        .retryWhen(
            reactor.util.retry.Retry.backoff(3, java.time.Duration.ofSeconds(2))
                .filter(ex -> !(ex instanceof java.net.UnknownHostException)));
  }

}
