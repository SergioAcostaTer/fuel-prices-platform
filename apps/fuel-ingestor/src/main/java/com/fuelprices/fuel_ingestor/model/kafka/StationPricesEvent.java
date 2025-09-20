package com.fuelprices.fuel_ingestor.model.kafka;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationPricesEvent {
  private String stationId;

  /** Raw strings exactly as received, e.g. {"GAS95_E5":"1,589"} */
  private Map<String, String> pricesRaw;

  /** Parsed decimals (EUR/L), nullable if unparsable, e.g. {"GAS95_E5":1.589} */
  private Map<String, BigDecimal> prices;

  private String brand;
  private String provinceId;
  private String municipalityId;
  private Double latitude;
  private Double longitude;

  private OffsetDateTime sourceTs;
  private OffsetDateTime ingestedAt;
}
