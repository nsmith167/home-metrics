package nsmith167.homemetrics.temphumidity.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document("temp-humidity")
public record TempHumidityReading(
        Instant timestamp,
        TempHumidityMetadata metadata,
        double temperature,
        double humidity
) { }
