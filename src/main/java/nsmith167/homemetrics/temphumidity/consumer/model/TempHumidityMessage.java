package nsmith167.homemetrics.temphumidity.consumer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TempHumidityMessage(
        String zipCode,
        String location,
        double temperature,
        double humidity
) { }
