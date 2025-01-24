package nsmith167.homemetrics.weather.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import nsmith167.homemetrics.weather.consumer.model.WeatherScannerMessage;
import nsmith167.homemetrics.weather.mapper.WeatherScannerMessageMapper;
import nsmith167.homemetrics.weather.service.WeatherService;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class WeatherMqttConsumer {

    private final WeatherService weatherService;

    public WeatherMqttConsumer(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    public void consumeWeatherScannerMessage(Message<?> message) {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        WeatherScannerMessage deserializedMessage;
        try {
            deserializedMessage = objectMapper.readValue((String) message.getPayload(), WeatherScannerMessage.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        weatherService.saveWeatherReading(
                WeatherScannerMessageMapper.INSTANCE.weatherScannerMessageToWeatherReading(deserializedMessage)
        );
    }
}
