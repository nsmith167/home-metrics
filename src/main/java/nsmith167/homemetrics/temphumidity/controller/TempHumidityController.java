package nsmith167.homemetrics.temphumidity.controller;

import nsmith167.homemetrics.temphumidity.dto.TempHumidityDto;
import nsmith167.homemetrics.temphumidity.mapper.TempHumidityDtoMapper;
import nsmith167.homemetrics.temphumidity.model.TempHumidityReading;
import nsmith167.homemetrics.temphumidity.service.TempHumidityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

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
}
