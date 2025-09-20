package com.fuelprices.fuel_ingestor.ingest;

import java.time.OffsetDateTime;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fuelprices.fuel_ingestor.config.IngestorProps;
import com.fuelprices.fuel_ingestor.http.FuelApiClient;
import com.fuelprices.fuel_ingestor.model.kafka.StationEvent;
import com.fuelprices.fuel_ingestor.model.kafka.StationEventsMapper;
import com.fuelprices.fuel_ingestor.model.kafka.StationPricesEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FuelIngestScheduler {

  private final FuelApiClient api;
  private final IngestorProps props;
  private final KafkaTemplate<String, StationEvent> stationTpl;
  private final KafkaTemplate<String, StationPricesEvent> pricesTpl;

  @Scheduled(cron = "${ingestor.schedule.cron}")
  public void run() {
    api.fetchRaw()
        .doOnNext(resp -> {
          var sourceTs = resp.getParsedTimestamp();
          var now = OffsetDateTime.now();

          resp.getStations().forEach(s -> {
            var stationEv = StationEventsMapper.toStationEvent(s, sourceTs, now);
            var pricesEv = StationEventsMapper.toStationPricesEvent(s, sourceTs, now);

            String key = s.getStationId();
            stationTpl.send(props.topics().stationsRaw(), key, stationEv);
            pricesTpl.send(props.topics().pricesRaw(), key, pricesEv);
          });
        })
        .subscribe();
  }
}
