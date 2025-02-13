package nsmith167.homemetrics.temphumidity.controller;

import nsmith167.homemetrics.temphumidity.dto.TempHumidityDto;
import nsmith167.homemetrics.temphumidity.mapper.TempHumidityDtoMapper;
import nsmith167.homemetrics.temphumidity.model.TempHumidityReading;
import nsmith167.homemetrics.temphumidity.service.TempHumidityService;
import nsmith167.homemetrics.weather.dto.WeatherReadingDto;
import nsmith167.homemetrics.weather.mapper.WeatherReadingDtoMapper;
import nsmith167.homemetrics.weather.model.WeatherReading;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/temp-humidity")
public class TempHumidityController {

    private final TempHumidityService tempHumidityService;
    private final TempHumidityDtoMapper tempHumidityDtoMapper;

    public TempHumidityController(TempHumidityService tempHumidityService, TempHumidityDtoMapper tempHumidityDtoMapper) {
        this.tempHumidityService = tempHumidityService;
        this.tempHumidityDtoMapper = tempHumidityDtoMapper;
    }

    @GetMapping("/latest")
    public ResponseEntity<TempHumidityDto> getLatestTempHumidityReading(@RequestParam("location") String location) {
        TempHumidityReading reading = tempHumidityService.getLatestTempHumidityReading(location, Instant.now());

        return new ResponseEntity<>(
                tempHumidityDtoMapper.tempHumidityReadingToDto(reading),
                HttpStatus.OK
        );
    }

    @GetMapping("/history")
    public ResponseEntity<List<TempHumidityDto>> getTempHumidityHistory(
            @RequestParam("location") String location,
            @RequestParam(value = "startTime", required = true, defaultValue = "0") long startTimeSeconds,
            @RequestParam(value = "endTime", required = true, defaultValue = "0") long endTimeSeconds
    ) {
        Instant startTime = Instant.ofEpochSecond(startTimeSeconds);
        Instant endTime = Instant.ofEpochSecond(endTimeSeconds);
        List<TempHumidityReading> results = tempHumidityService.getTempHumidityHistory(location, startTime, endTime);
        List<TempHumidityDto> response = results.stream()
                .map(tempHumidityDtoMapper::tempHumidityReadingToDto)
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
