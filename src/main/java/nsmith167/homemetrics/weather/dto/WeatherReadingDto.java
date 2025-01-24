package nsmith167.homemetrics.weather.dto;

import java.time.Instant;

public record WeatherReadingDto(
        Instant timestamp,
        String zipCode,
        double temperature,
        double humidity,
        double temperatureApparent,
        double cloudCover
) {
}
