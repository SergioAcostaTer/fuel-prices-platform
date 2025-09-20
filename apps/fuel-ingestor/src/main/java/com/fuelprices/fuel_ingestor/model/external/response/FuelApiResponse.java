package com.fuelprices.fuel_ingestor.model.external.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FuelApiResponse {

  @JsonProperty("Fecha")
  private String timestamp;

  @JsonProperty("ListaEESSPrecio")
  private List<FuelStationDto> stations;

  @JsonProperty("Nota")
  private String note;

  @JsonProperty("ResultadoConsulta")
  private String result;
}
