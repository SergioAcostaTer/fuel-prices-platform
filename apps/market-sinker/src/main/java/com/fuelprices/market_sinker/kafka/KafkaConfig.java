package com.fuelprices.market_sinker.kafka;

import java.util.HashMap;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.fuelprices.market_sinker.model.StationEvent;
import com.fuelprices.market_sinker.model.StationPricesEvent;

@Configuration
public class KafkaConfig {

  /**
   * Keep your global JSON settings: trust all and don't use type headers.
   * This augments the auto-configured ConsumerFactory with these props.
   */
  @Bean
  DefaultKafkaConsumerFactoryCustomizer trustAll() {
    return cf -> cf.updateConfigs(java.util.Map.of(
        JsonDeserializer.TRUSTED_PACKAGES, "*",
        JsonDeserializer.USE_TYPE_INFO_HEADERS, false));
  }

  /**
   * StationEvent listener factory.
   * Clones the auto-configured ConsumerFactory properties (so we keep
   * bootstrap servers, group-id, etc.) and only swaps the value deserializer.
   */
  @Bean(name = "stationKafkaFactory")
  public ConcurrentKafkaListenerContainerFactory<String, StationEvent> stationKafkaFactory(
      ConsumerFactory<String, Object> baseFactory) {

    var cfg = new HashMap<>(baseFactory.getConfigurationProperties());

    var typedFactory = new DefaultKafkaConsumerFactory<>(
        cfg,
        new StringDeserializer(),
        new JsonDeserializer<>(StationEvent.class, false));

    var factory = new ConcurrentKafkaListenerContainerFactory<String, StationEvent>();
    factory.setConsumerFactory(typedFactory);
    factory.setConcurrency(2);
    return factory;
  }

  /**
   * StationPricesEvent listener factory.
   */
  @Bean(name = "pricesKafkaFactory")
  public ConcurrentKafkaListenerContainerFactory<String, StationPricesEvent> pricesKafkaFactory(
      ConsumerFactory<String, Object> baseFactory) {

    var cfg = new HashMap<>(baseFactory.getConfigurationProperties());

    var typedFactory = new DefaultKafkaConsumerFactory<>(
        cfg,
        new StringDeserializer(),
        new JsonDeserializer<>(StationPricesEvent.class, false));

    var factory = new ConcurrentKafkaListenerContainerFactory<String, StationPricesEvent>();
    factory.setConsumerFactory(typedFactory);
    factory.setConcurrency(2);
    return factory;
  }
}
