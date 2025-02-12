package nsmith167.homemetrics.temphumidity.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import nsmith167.homemetrics.temphumidity.consumer.model.TempHumidityMessage;
import nsmith167.homemetrics.temphumidity.mapper.TempHumidityMessageMapper;
import nsmith167.homemetrics.temphumidity.service.TempHumidityService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TempHumidityMessageHandler {

    private final TempHumidityService tempHumidityService;
    private final ObjectMapper objectMapper;
    private final TempHumidityMessageMapper tempHumidityMessageMapper;

    public TempHumidityMessageHandler(
            TempHumidityService tempHumidityService,
            ObjectMapper objectMapper,
            TempHumidityMessageMapper tempHumidityMessageMapper
    ) {
        this.tempHumidityService = tempHumidityService;
        this.objectMapper = objectMapper;
        this.tempHumidityMessageMapper = tempHumidityMessageMapper;
    }

    public void handleTempHumidityMessage(String message) {
        TempHumidityMessage deserializedMessage;
        try {
            deserializedMessage = objectMapper.readValue(message, TempHumidityMessage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        tempHumidityService.saveTempHumidityReading(tempHumidityMessageMapper.tempHumidityMessageToReading(deserializedMessage));
    }
}
