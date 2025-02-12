package nsmith167.homemetrics.temphumidity.dto;

import java.time.Instant;

public record TempHumidityDto(
        Instant timestamp,
        String location,
        double temperature,
        double humidity
) {
}
