package com.fuelprices.market_sinker.kafka;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
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

  @Bean
  DefaultKafkaConsumerFactoryCustomizer trustAll() {
    return cf -> cf.updateConfigs(Map.of(
        JsonDeserializer.TRUSTED_PACKAGES, "*",
        JsonDeserializer.USE_TYPE_INFO_HEADERS, false));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, StationEvent> stationKafkaFactory(
      ConsumerFactory<String, StationEvent> cf) {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, StationEvent>();
    factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(
        Map.of(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class),
        new StringDeserializer(),
        new JsonDeserializer<>(StationEvent.class, false)));
    factory.setConcurrency(2);
    return factory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, StationPricesEvent> pricesKafkaFactory(
      ConsumerFactory<String, StationPricesEvent> cf) {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, StationPricesEvent>();
    factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(
        Map.of(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
            ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class),
        new StringDeserializer(),
        new JsonDeserializer<>(StationPricesEvent.class, false)));
    factory.setConcurrency(2);
    return factory;
  }
}
