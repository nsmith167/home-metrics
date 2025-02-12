package nsmith167.homemetrics.weather.model;

import org.springframework.data.mongodb.core.mapping.Field;

public record WeatherReadingMetadata(
        @Field("zip_code") String zipCode
) {}