package nsmith167.homemetrics.weather.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import nsmith167.homemetrics.weather.consumer.model.WeatherScannerMessage;
import nsmith167.homemetrics.weather.mapper.WeatherScannerMessageMapper;
import nsmith167.homemetrics.weather.service.WeatherService;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WeatherScannerMessageHandler {

    private final WeatherService weatherService;
    private final ObjectMapper objectMapper;

    public WeatherScannerMessageHandler(
            WeatherService weatherService,
            ObjectMapper objectMapper
    ) {
        this.weatherService = weatherService;
        this.objectMapper = objectMapper;
    }

    public void handleWeatherScannerMessage(String message) {
        WeatherScannerMessage deserializedMessage;
        try {
            deserializedMessage = objectMapper.readValue(message, WeatherScannerMessage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        weatherService.saveWeatherReading(
                WeatherScannerMessageMapper.INSTANCE.weatherScannerMessageToWeatherReading(deserializedMessage)
        );
    }
}
