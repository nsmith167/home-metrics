package nsmith167.homemetrics.temphumidity.mapper;

import nsmith167.homemetrics.temphumidity.consumer.model.TempHumidityMessage;
import nsmith167.homemetrics.temphumidity.model.TempHumidityMetadata;
import nsmith167.homemetrics.temphumidity.model.TempHumidityReading;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TempHumidityMessageMapper {

    public TempHumidityReading tempHumidityMessageToReading(TempHumidityMessage message) {
        return new TempHumidityReading(
                Instant.now(),
                new TempHumidityMetadata(message.zipCode(), message.location()),
                message.temperature(),
                message.humidity()
        );
    }
}
