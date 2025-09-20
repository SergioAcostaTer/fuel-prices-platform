package com.fuelprices.fuel_ingestor.model.external.response;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FuelApiResponse {

  private static final DateTimeFormatter FECHA_FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
  private static final ZoneId SPAIN_TZ = ZoneId.of("Europe/Madrid");

  @JsonProperty("Fecha")
  private String timestampRaw;

  @JsonProperty("ListaEESSPrecio")
  private List<FuelStationDto> stations = Collections.emptyList();

  @JsonProperty("Nota")
  private String note;

  @JsonProperty("ResultadoConsulta")
  private String result;

  private OffsetDateTime parsedTimestamp;

  @JsonProperty("Fecha")
  public void setTimestampRaw(String value) {
    this.timestampRaw = value;
    this.parsedTimestamp = parseFecha(value);
    propagateTimestamp();
  }

  @JsonProperty("ListaEESSPrecio")
  public void setStations(List<FuelStationDto> stations) {
    this.stations = (stations == null) ? Collections.emptyList() : stations;
    propagateTimestamp();
  }

  private void propagateTimestamp() {
    if (parsedTimestamp == null || stations.isEmpty())
      return;
    stations.forEach(s -> s.setTimestamp(parsedTimestamp));
  }

  private static OffsetDateTime parseFecha(String fecha) {
    if (fecha == null || fecha.isBlank())
      return null;
    try {
      LocalDateTime ldt = LocalDateTime.parse(fecha.trim(), FECHA_FMT);
      return ldt.atZone(SPAIN_TZ).toOffsetDateTime();
    } catch (DateTimeParseException e) {
      return null;
    }
  }
}
