package com.fuelprices.fuel_ingestor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ingestor")
public class IngestorProps {
  public Source source = new Source();
  public Schedule schedule = new Schedule();
  public Topics topics = new Topics();
  public Limits limits = new Limits();

  public static class Source {
    public String baseUrl;
    public int timeoutMs;
    public String userAgent;
  }

  public static class Schedule {
    public String cron;
  }

  public static class Topics {
    public String pricesRaw;
    public String stationsRaw;
    public String dlq;
  }

  public static class Limits {
    public int maxBatch;
  }
}
