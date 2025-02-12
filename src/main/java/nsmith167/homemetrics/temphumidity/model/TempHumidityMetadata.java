package nsmith167.homemetrics.temphumidity.model;

import org.springframework.data.mongodb.core.mapping.Field;

public record TempHumidityMetadata(
        @Field("zip_code") String zipCode,
        String location
) {
}
