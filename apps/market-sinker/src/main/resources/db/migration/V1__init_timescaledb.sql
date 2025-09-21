CREATE EXTENSION IF NOT EXISTS timescaledb;

CREATE TABLE IF NOT EXISTS stations_latest (
  station_id        TEXT PRIMARY KEY,
  municipality_id   TEXT,
  province_id       TEXT,
  region_id         TEXT,
  brand             TEXT,
  address           TEXT,
  postal_code       TEXT,
  municipality      TEXT,
  province          TEXT,
  locality          TEXT,
  schedule          TEXT,
  sale_type         TEXT,
  side              TEXT,
  remission         TEXT,
  latitude_raw      TEXT,
  longitude_raw     TEXT,
  latitude          DOUBLE PRECISION,
  longitude         DOUBLE PRECISION,
  source_ts         TIMESTAMPTZ,
  ingested_at       TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS stations_history (
  id                BIGSERIAL PRIMARY KEY,
  station_id        TEXT NOT NULL,
  brand             TEXT,
  address           TEXT,
  postal_code       TEXT,
  municipality      TEXT,
  province          TEXT,
  locality          TEXT,
  schedule          TEXT,
  sale_type         TEXT,
  side              TEXT,
  remission         TEXT,
  latitude          DOUBLE PRECISION,
  longitude         DOUBLE PRECISION,
  source_ts         TIMESTAMPTZ NOT NULL,
  ingested_at       TIMESTAMPTZ NOT NULL
);
CREATE INDEX IF NOT EXISTS stations_history_station_ts_idx
  ON stations_history(station_id, source_ts DESC);

CREATE TABLE IF NOT EXISTS prices (
  ts               TIMESTAMPTZ NOT NULL,
  station_id       TEXT NOT NULL,
  fuel_code        TEXT NOT NULL,     -- e.g. GAS95_E5
  price_eur_l      NUMERIC(7,4),      -- nullable if unparsable
  brand            TEXT,
  province_id      TEXT,
  municipality_id  TEXT,
  latitude         DOUBLE PRECISION,
  longitude        DOUBLE PRECISION,
  source_ts        TIMESTAMPTZ,
  ingested_at      TIMESTAMPTZ,
  PRIMARY KEY (ts, station_id, fuel_code)
);

SELECT create_hypertable('prices', by_range('ts'), if_not_exists => TRUE);

CREATE INDEX IF NOT EXISTS prices_station_idx
  ON prices(station_id, ts DESC);
CREATE INDEX IF NOT EXISTS prices_fuel_ts_idx
  ON prices(fuel_code, ts DESC);
CREATE INDEX IF NOT EXISTS prices_geo_idx
  ON prices(latitude, longitude);
