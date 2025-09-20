package com.fuelprices.fuel_ingestor.model.kafka;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fuelprices.fuel_ingestor.model.external.response.FuelStationDto;

public final class StationEventsMapper {
  private StationEventsMapper() {
  }

  private static Double parseCoord(String raw) {
    if (raw == null || raw.isBlank())
      return null;
    try {
      return Double.parseDouble(raw.replace(',', '.').trim());
    } catch (NumberFormatException ex) {
      return null;
    }
  }

  private static BigDecimal parsePrice(String raw) {
    if (raw == null || raw.isBlank())
      return null;
    String normalized = raw.replace(',', '.').replaceAll("[^0-9.]", "").trim();
    if (normalized.isEmpty())
      return null;
    try {
      return new BigDecimal(normalized);
    } catch (NumberFormatException ex) {
      return null;
    }
  }

  public static StationEvent toStationEvent(
      FuelStationDto s,
      OffsetDateTime sourceTs,
      OffsetDateTime ingestedAt) {

    return StationEvent.builder()
        .stationId(s.getStationId())
        .municipalityId(s.getMunicipalityId())
        .provinceId(s.getProvinceId())
        .regionId(s.getRegionId())
        .brand(s.getBrand())
        .address(s.getAddress())
        .postalCode(s.getPostalCode())
        .municipality(s.getMunicipality())
        .province(s.getProvince())
        .locality(s.getLocality())
        .schedule(s.getSchedule())
        .saleType(s.getSaleType())
        .side(s.getSide())
        .remission(s.getRemission())
        .latitudeRaw(s.getLatitude())
        .longitudeRaw(s.getLongitude())
        .latitude(parseCoord(s.getLatitude()))
        .longitude(parseCoord(s.getLongitude()))
        .sourceTs(sourceTs)
        .ingestedAt(ingestedAt)
        .build();
  }

  public static StationPricesEvent toStationPricesEvent(
      FuelStationDto s,
      OffsetDateTime sourceTs,
      OffsetDateTime ingestedAt) {

    Map<String, String> raw = new LinkedHashMap<>();
    Map<String, BigDecimal> num = new LinkedHashMap<>();

    put(raw, num, "ADBLUE", s.getPriceAdblue());
    put(raw, num, "AMMONIA", s.getPriceAmmonia());
    put(raw, num, "BIODIESEL", s.getPriceBiodiesel());
    put(raw, num, "BIOETHANOL", s.getPriceBioethanol());
    put(raw, num, "BIO_CNG", s.getPriceBioCng());
    put(raw, num, "BIO_LNG", s.getPriceBioLng());
    put(raw, num, "RENEWABLE_DIESEL", s.getPriceRenewableDiesel());
    put(raw, num, "CNG", s.getPriceCng());
    put(raw, num, "LNG", s.getPriceLng());
    put(raw, num, "LPG", s.getPriceLpg());
    put(raw, num, "DIESEL_A", s.getPriceDieselA());
    put(raw, num, "DIESEL_B", s.getPriceDieselB());
    put(raw, num, "DIESEL_PREMIUM", s.getPriceDieselPremium());
    put(raw, num, "GAS95_E10", s.getPriceGas95E10());
    put(raw, num, "GAS95_E25", s.getPriceGas95E25());
    put(raw, num, "GAS95_E5", s.getPriceGas95E5());
    put(raw, num, "GAS95_E5_PREMIUM", s.getPriceGas95E5Premium());
    put(raw, num, "GAS95_E85", s.getPriceGas95E85());
    put(raw, num, "GAS98_E10", s.getPriceGas98E10());
    put(raw, num, "GAS98_E5", s.getPriceGas98E5());
    put(raw, num, "RENEWABLE_GASOLINE", s.getPriceRenewableGasoline());
    put(raw, num, "HYDROGEN", s.getPriceHydrogen());
    put(raw, num, "METHANOL", s.getPriceMethanol());

    return StationPricesEvent.builder()
        .stationId(s.getStationId())
        .pricesRaw(raw)
        .prices(num)
        .brand(s.getBrand())
        .provinceId(s.getProvinceId())
        .municipalityId(s.getMunicipalityId())
        .latitude(parseCoord(s.getLatitude()))
        .longitude(parseCoord(s.getLongitude()))
        .sourceTs(sourceTs)
        .ingestedAt(ingestedAt)
        .build();
  }

  private static void put(Map<String, String> raw, Map<String, BigDecimal> num, String key, String value) {
    if (value == null)
      return;
    raw.put(key, value);
    num.put(key, parsePrice(value));
  }
}
