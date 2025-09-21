package com.fuelprices.market_sinker.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationPricesEvent {
  private String stationId;
  private Map<String, String> pricesRaw;
  private Map<String, BigDecimal> prices;
  private String brand;
  private String provinceId;
  private String municipalityId;
  private Double latitude;
  private Double longitude;
  private OffsetDateTime sourceTs;
  private OffsetDateTime ingestedAt;
}
