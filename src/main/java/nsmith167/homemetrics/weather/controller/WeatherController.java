package nsmith167.homemetrics.weather.controller;

import nsmith167.homemetrics.weather.mapper.WeatherReadingDtoMapper;
import nsmith167.homemetrics.weather.model.WeatherReading;
import nsmith167.homemetrics.weather.dto.WeatherReadingDto;
import nsmith167.homemetrics.weather.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    WeatherService weatherService;

    @GetMapping("/local/latest")
    public ResponseEntity<WeatherReadingDto> getLatestWeather(@RequestParam("zipCode") String zipCode) {
        WeatherReading result = weatherService
                .getLatestWeather(Integer.parseInt(zipCode), Instant.now()); //TODO: Refactor this once db is using string for zip

        return new ResponseEntity<>(
                WeatherReadingDtoMapper.INSTANCE.weatherReadingToWeatherReadingDto(result),
                HttpStatus.OK
        );
    }

    @GetMapping("/local/history")
    public ResponseEntity<List<WeatherReadingDto>> getWeatherHistory(
            @RequestParam("zipCode") String zipCode,
            @RequestParam(value = "startTime", required = false, defaultValue = "0") long startTime,
            @RequestParam(value = "endTime", required = false, defaultValue = "0") long endTime
    ) {
        if (endTime == 0) {
            endTime = Long.MAX_VALUE;
        }
        List<WeatherReading> results = weatherService.getWeatherHistory(Integer.parseInt(zipCode), startTime, endTime);
        List<WeatherReadingDto> response = results.stream()
                .map(WeatherReadingDtoMapper.INSTANCE::weatherReadingToWeatherReadingDto)
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
