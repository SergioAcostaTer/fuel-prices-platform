package com.fuelprices.fuel_ingestor.model.external.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FuelApiResponse {

  @JsonAlias("Fecha")
  private String timestamp;

  @JsonAlias("ListaEESSPrecio")
  private List<FuelStationDto> stations;

  @JsonAlias("Nota")
  private String note;

  @JsonAlias("ResultadoConsulta")
  private String result;
}
