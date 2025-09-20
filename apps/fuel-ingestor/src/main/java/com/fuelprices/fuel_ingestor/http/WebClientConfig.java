package com.fuelprices.fuel_ingestor.http;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import com.fuelprices.fuel_ingestor.config.IngestorProps;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
public class WebClientConfig {

  @Bean
  WebClient fuelWebClient(IngestorProps props) {
    var source = props.source;
    var httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, source.timeoutMs)
        .responseTimeout(Duration.ofMillis(source.timeoutMs))
        .doOnConnected(conn -> conn
            .addHandlerLast(new ReadTimeoutHandler(source.timeoutMs, TimeUnit.MILLISECONDS))
            .addHandlerLast(new WriteTimeoutHandler(source.timeoutMs, TimeUnit.MILLISECONDS)));

    return WebClient.builder()
        .baseUrl(source.baseUrl)
        .defaultHeader("User-Agent", source.userAgent)
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();
  }
}
