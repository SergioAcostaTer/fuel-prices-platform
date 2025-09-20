package com.fuelprices.fuel_ingestor.model.external.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class FuelStationDto {

  @JsonProperty("C.P.")
  private String postalCode;

  @JsonProperty("Dirección")
  private String address;

  @JsonProperty("Horario")
  private String schedule;

  @JsonProperty("Latitud")
  private String latitude;

  @JsonProperty("Localidad")
  private String locality;

  @JsonProperty("Longitud (WGS84)")
  private String longitude;

  @JsonProperty("Margen")
  private String side;

  @JsonProperty("Municipio")
  private String municipality;

  @JsonProperty("Precio Adblue")
  private String priceAdblue;

  @JsonProperty("Precio Amoniaco")
  private String priceAmmonia;

  @JsonProperty("Precio Biodiesel")
  private String priceBiodiesel;

  @JsonProperty("Precio Bioetanol")
  private String priceBioethanol;

  @JsonProperty("Precio Biogas Natural Comprimido")
  private String priceBioCng;

  @JsonProperty("Precio Biogas Natural Licuado")
  private String priceBioLng;

  @JsonProperty("Precio Diésel Renovable")
  private String priceRenewableDiesel;

  @JsonProperty("Precio Gas Natural Comprimido")
  private String priceCng;

  @JsonProperty("Precio Gas Natural Licuado")
  private String priceLng;

  @JsonProperty("Precio Gases licuados del petróleo")
  private String priceLpg;

  @JsonProperty("Precio Gasoleo A")
  private String priceDieselA;

  @JsonProperty("Precio Gasoleo B")
  private String priceDieselB;

  @JsonProperty("Precio Gasoleo Premium")
  private String priceDieselPremium;

  @JsonProperty("Precio Gasolina 95 E10")
  private String priceGas95E10;

  @JsonProperty("Precio Gasolina 95 E25")
  private String priceGas95E25;

  @JsonProperty("Precio Gasolina 95 E5")
  private String priceGas95E5;

  @JsonProperty("Precio Gasolina 95 E5 Premium")
  private String priceGas95E5Premium;

  @JsonProperty("Precio Gasolina 95 E85")
  private String priceGas95E85;

  @JsonProperty("Precio Gasolina 98 E10")
  private String priceGas98E10;

  @JsonProperty("Precio Gasolina 98 E5")
  private String priceGas98E5;

  @JsonProperty("Precio Gasolina Renovable")
  private String priceRenewableGasoline;

  @JsonProperty("Precio Hidrogeno")
  private String priceHydrogen;

  @JsonProperty("Precio Metanol")
  private String priceMethanol;

  @JsonProperty("Provincia")
  private String province;

  @JsonProperty("Remisión")
  private String remission;

  @JsonProperty("Rótulo")
  private String brand;

  @JsonProperty("Tipo Venta")
  private String saleType;

  @JsonProperty("% BioEtanol")
  private String percentBioethanol;

  @JsonProperty("% Éster metílico")
  private String percentEsterMetilico;

  @JsonProperty("IDEESS")
  private String stationId;

  @JsonProperty("IDMunicipio")
  private String municipalityId;

  @JsonProperty("IDProvincia")
  private String provinceId;

  @JsonProperty("IDCCAA")
  private String regionId;
}
