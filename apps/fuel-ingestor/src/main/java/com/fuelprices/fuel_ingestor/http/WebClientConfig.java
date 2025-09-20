package com.fuelprices.fuel_ingestor.http;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.fuelprices.fuel_ingestor.config.IngestorProps;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

  @Bean("fuelWebClient")
  public WebClient fuelWebClient(IngestorProps props) {
    var source = props.source();

    // sensible defaults
    int timeoutMs = source.timeoutMs() != null ? source.timeoutMs() : 10_000;

    SslContext insecure;
    try {
      insecure = SslContextBuilder.forClient()
          .trustManager(InsecureTrustManagerFactory.INSTANCE)
          .build();
    } catch (javax.net.ssl.SSLException e) {
      throw new RuntimeException("Failed to build insecure SSL context", e);
    }

    HttpClient httpClient = HttpClient.create()
        .secure(ssl -> ssl.sslContext(insecure))
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, timeoutMs)
        .responseTimeout(Duration.ofMillis(timeoutMs))
        .followRedirect(true)
        .compress(true);

    ExchangeStrategies strategies = ExchangeStrategies.builder()
        .codecs(cfg -> cfg.defaultCodecs().maxInMemorySize(source.maxSizeInMb() * 1024 * 1024))
        .build();

    WebClient.Builder builder = WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .exchangeStrategies(strategies);

    if (source.baseUrl() != null && !source.baseUrl().isBlank()) {
      builder.baseUrl(source.baseUrl());
    }

    if (source.userAgent() != null && !source.userAgent().isBlank()) {
      builder.defaultHeader("User-Agent", source.userAgent());
    }

    return builder.build();
  }
}
