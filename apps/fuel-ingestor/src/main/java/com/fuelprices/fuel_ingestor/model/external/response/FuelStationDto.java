package com.fuelprices.fuel_ingestor.model.external.response;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FuelStationDto {

  @JsonAlias("C.P.")
  private String postalCode;

  @JsonAlias("Dirección")
  private String address;

  @JsonAlias("Horario")
  private String schedule;

  @JsonAlias("Latitud")
  private String latitude;

  @JsonAlias("Localidad")
  private String locality;

  @JsonAlias("Longitud (WGS84)")
  private String longitude;

  @JsonAlias("Margen")
  private String side;

  @JsonAlias("Municipio")
  private String municipality;

  @JsonAlias("Precio Adblue")
  private String priceAdblue;

  @JsonAlias("Precio Amoniaco")
  private String priceAmmonia;

  @JsonAlias("Precio Biodiesel")
  private String priceBiodiesel;

  @JsonAlias("Precio Bioetanol")
  private String priceBioethanol;

  @JsonAlias("Precio Biogas Natural Comprimido")
  private String priceBioCng;

  @JsonAlias("Precio Biogas Natural Licuado")
  private String priceBioLng;

  @JsonAlias("Precio Diésel Renovable")
  private String priceRenewableDiesel;

  @JsonAlias("Precio Gas Natural Comprimido")
  private String priceCng;

  @JsonAlias("Precio Gas Natural Licuado")
  private String priceLng;

  @JsonAlias("Precio Gases licuados del petróleo")
  private String priceLpg;

  @JsonAlias("Precio Gasoleo A")
  private String priceDieselA;

  @JsonAlias("Precio Gasoleo B")
  private String priceDieselB;

  @JsonAlias("Precio Gasoleo Premium")
  private String priceDieselPremium;

  @JsonAlias("Precio Gasolina 95 E10")
  private String priceGas95E10;

  @JsonAlias("Precio Gasolina 95 E25")
  private String priceGas95E25;

  @JsonAlias("Precio Gasolina 95 E5")
  private String priceGas95E5;

  @JsonAlias("Precio Gasolina 95 E5 Premium")
  private String priceGas95E5Premium;

  @JsonAlias("Precio Gasolina 95 E85")
  private String priceGas95E85;

  @JsonAlias("Precio Gasolina 98 E10")
  private String priceGas98E10;

  @JsonAlias("Precio Gasolina 98 E5")
  private String priceGas98E5;

  @JsonAlias("Precio Gasolina Renovable")
  private String priceRenewableGasoline;

  @JsonAlias("Precio Hidrogeno")
  private String priceHydrogen;

  @JsonAlias("Precio Metanol")
  private String priceMethanol;

  @JsonAlias("Provincia")
  private String province;

  @JsonAlias("Remisión")
  private String remission;

  @JsonAlias("Rótulo")
  private String brand;

  @JsonAlias("Tipo Venta")
  private String saleType;

  @JsonAlias("% BioEtanol")
  private String percentBioethanol;

  @JsonAlias("% Éster metílico")
  private String percentEsterMetilico;

  @JsonAlias("IDEESS")
  private String stationId;

  @JsonAlias("IDMunicipio")
  private String municipalityId;

  @JsonAlias("IDProvincia")
  private String provinceId;

  @JsonAlias("IDCCAA")
  private String regionId;

  private OffsetDateTime timestamp;
}
