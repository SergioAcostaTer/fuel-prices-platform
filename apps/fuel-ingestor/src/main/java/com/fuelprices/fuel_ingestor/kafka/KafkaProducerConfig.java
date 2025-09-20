package com.fuelprices.fuel_ingestor.kafka;

import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import com.fuelprices.fuel_ingestor.model.kafka.StationEvent;
import com.fuelprices.fuel_ingestor.model.kafka.StationPricesEvent;

@Configuration
public class KafkaProducerConfig {

  @Bean
  DefaultKafkaProducerFactoryCustomizer noTypeHeaders() {
    return pf -> pf.updateConfigs(java.util.Map.of(
        "spring.json.add.type.headers", false));
  }

  @Bean
  public KafkaTemplate<String, StationEvent> stationEventTemplate(
      org.springframework.kafka.core.ProducerFactory<String, StationEvent> pf) {
    return new KafkaTemplate<>(pf);
  }

  @Bean
  public KafkaTemplate<String, StationPricesEvent> stationPricesEventTemplate(
      org.springframework.kafka.core.ProducerFactory<String, StationPricesEvent> pf) {
    return new KafkaTemplate<>(pf);
  }
}
