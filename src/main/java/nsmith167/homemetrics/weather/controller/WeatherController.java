package nsmith167.homemetrics.weather.controller;

import nsmith167.homemetrics.weather.dto.WeatherReadingDto;
import nsmith167.homemetrics.weather.mapper.WeatherReadingDtoMapper;
import nsmith167.homemetrics.weather.model.WeatherReading;
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
                .getLatestWeather(zipCode, Instant.now());

        return new ResponseEntity<>(
                WeatherReadingDtoMapper.INSTANCE.weatherReadingToWeatherReadingDto(result),
                HttpStatus.OK
        );
    }

    @GetMapping("/local/history")
    public ResponseEntity<List<WeatherReadingDto>> getWeatherHistory(
            @RequestParam("zipCode") String zipCode,
            @RequestParam(value = "startTime", required = true, defaultValue = "0") long startTimeSeconds,
            @RequestParam(value = "endTime", required = true, defaultValue = "0") long endTimeSeconds
    ) {
        Instant startTime = Instant.ofEpochSecond(startTimeSeconds);
        Instant endTime = Instant.ofEpochSecond(endTimeSeconds);
        List<WeatherReading> results = weatherService.getWeatherHistory(zipCode, startTime, endTime);
        List<WeatherReadingDto> response = results.stream()
                .map(WeatherReadingDtoMapper.INSTANCE::weatherReadingToWeatherReadingDto)
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
