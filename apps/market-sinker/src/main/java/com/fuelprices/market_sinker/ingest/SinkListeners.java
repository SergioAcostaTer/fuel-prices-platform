package com.fuelprices.market_sinker.ingest;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fuelprices.market_sinker.model.StationEvent;
import com.fuelprices.market_sinker.model.StationPricesEvent;
import com.fuelprices.market_sinker.repo.SinkRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class SinkListeners {

  private final SinkRepository repo;

  @KafkaListener(topics = "${topicProps.stations}", containerFactory = "stationsKafkaFactory")
  public void stationSink(@Payload StationEvent ev) {
    try {
      repo.upsertStationLatest(ev);
      repo.appendStationHistory(ev);
    } catch (Exception ex) {
      log.error("Failed to sink StationEvent stationId={}", ev.getStationId(), ex);
    }
  }

  @KafkaListener(topics = "${topicProps.prices}", containerFactory = "pricesKafkaFactory")
  public void pricesSink(@Payload StationPricesEvent ev) {
    try {
      repo.insertPrices(ev);
    } catch (Exception ex) {
      log.error("Failed to sink StationPricesEvent stationId={}", ev.getStationId(), ex);
    }
  }
}
