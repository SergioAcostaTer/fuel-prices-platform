package com.fuelprices.market_sinker.repo;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fuelprices.market_sinker.model.StationEvent;
import com.fuelprices.market_sinker.model.StationPricesEvent;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SinkRepository {

  private final JdbcTemplate jdbc;

  public void upsertStationLatest(StationEvent e) {
    String sql = """
        INSERT INTO stations_latest (
          station_id, municipality_id, province_id, region_id,
          brand, address, postal_code, municipality, province, locality,
          schedule, sale_type, side, remission,
          latitude_raw, longitude_raw, latitude, longitude,
          source_ts, ingested_at
        ) VALUES (?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?, ?,?)
        ON CONFLICT (station_id) DO UPDATE SET
          municipality_id=EXCLUDED.municipality_id,
          province_id=EXCLUDED.province_id,
          region_id=EXCLUDED.region_id,
          brand=EXCLUDED.brand,
          address=EXCLUDED.address,
          postal_code=EXCLUDED.postal_code,
          municipality=EXCLUDED.municipality,
          province=EXCLUDED.province,
          locality=EXCLUDED.locality,
          schedule=EXCLUDED.schedule,
          sale_type=EXCLUDED.sale_type,
          side=EXCLUDED.side,
          remission=EXCLUDED.remission,
          latitude_raw=EXCLUDED.latitude_raw,
          longitude_raw=EXCLUDED.longitude_raw,
          latitude=EXCLUDED.latitude,
          longitude=EXCLUDED.longitude,
          source_ts=EXCLUDED.source_ts,
          ingested_at=EXCLUDED.ingested_at
        """;
    jdbc.update(sql,
        e.getStationId(), e.getMunicipalityId(), e.getProvinceId(), e.getRegionId(),
        e.getBrand(), e.getAddress(), e.getPostalCode(), e.getMunicipality(), e.getProvince(), e.getLocality(),
        e.getSchedule(), e.getSaleType(), e.getSide(), e.getRemission(),
        e.getLatitudeRaw(), e.getLongitudeRaw(), e.getLatitude(), e.getLongitude(),
        toTs(e.getSourceTs()), toTs(e.getIngestedAt()));
  }

  public void appendStationHistory(StationEvent e) {
    jdbc.update("""
        INSERT INTO stations_history (
          station_id, brand, address, postal_code, municipality, province, locality,
          schedule, sale_type, side, remission, latitude, longitude, source_ts, ingested_at
        ) VALUES (?,?,?,?, ?,?,?, ?,?,?, ?,?,?, ?,?)
        """,
        e.getStationId(), e.getBrand(), e.getAddress(), e.getPostalCode(), e.getMunicipality(), e.getProvince(),
        e.getLocality(),
        e.getSchedule(), e.getSaleType(), e.getSide(), e.getRemission(), e.getLatitude(), e.getLongitude(),
        toTs(e.getSourceTs()), toTs(e.getIngestedAt()));
  }

  public void insertPrices(StationPricesEvent e) {
    if (e.getPrices() == null || e.getPrices().isEmpty())
      return;
    String sql = """
        INSERT INTO prices (
          ts, station_id, fuel_code, price_eur_l,
          brand, province_id, municipality_id, latitude, longitude,
          source_ts, ingested_at
        ) VALUES (?,?,?,?, ?,?,?, ?,?, ?,?)
        ON CONFLICT (ts, station_id, fuel_code) DO NOTHING
        """;
    jdbc.batchUpdate(sql, e.getPrices().entrySet(), 100,
        (ps, entry) -> {
          ps.setTimestamp(1, toTs(e.getSourceTs()));
          ps.setString(2, e.getStationId());
          ps.setString(3, entry.getKey());
          if (entry.getValue() == null)
            ps.setNull(4, java.sql.Types.NUMERIC);
          else
            ps.setBigDecimal(4, entry.getValue());
          ps.setString(5, e.getBrand());
          ps.setString(6, e.getProvinceId());
          ps.setString(7, e.getMunicipalityId());
          if (e.getLatitude() == null)
            ps.setNull(8, java.sql.Types.DOUBLE);
          else
            ps.setDouble(8, e.getLatitude());
          if (e.getLongitude() == null)
            ps.setNull(9, java.sql.Types.DOUBLE);
          else
            ps.setDouble(9, e.getLongitude());
          ps.setTimestamp(10, toTs(e.getSourceTs()));
          ps.setTimestamp(11, toTs(e.getIngestedAt()));
        });
  }

  private static Timestamp toTs(OffsetDateTime odt) {
    return odt == null ? null : Timestamp.from(odt.toInstant());
  }
}
