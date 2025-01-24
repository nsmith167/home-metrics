package nsmith167.homemetrics.weather.consumer.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record WeatherScannerMessage(
        Instant timestamp,
        @JsonProperty("zip") String zipCode,
        @JsonProperty("cloud_cover") double cloudCover,
        double temperature,
        @JsonProperty("temperature_apparent") double temperatureApparent,
        double humidity
) {
}
