package com.fuelprices.market_sinker.model;

import java.time.OffsetDateTime;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StationEvent {
  private String stationId;
  private String municipalityId;
  private String provinceId;
  private String regionId;
  private String brand;
  private String address;
  private String postalCode;
  private String municipality;
  private String province;
  private String locality;
  private String schedule;
  private String saleType;
  private String side;
  private String remission;
  private String latitudeRaw;
  private String longitudeRaw;
  private Double latitude;
  private Double longitude;
  private OffsetDateTime sourceTs;
  private OffsetDateTime ingestedAt;
}
