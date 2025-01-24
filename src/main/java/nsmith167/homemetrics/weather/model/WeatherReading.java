package nsmith167.homemetrics.weather.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.Instant;

@Document("weather")
public record WeatherReading(
        Instant timestamp,
        WeatherReadingMetadata metadata,
        double temperature,
        double humidity,
        @Field("temperature_apparent") double temperatureApparent,
        @Field("cloud_cover") double cloudCover
) {}
