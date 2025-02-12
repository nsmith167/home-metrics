package nsmith167.homemetrics.temphumidity.mapper;

import nsmith167.homemetrics.temphumidity.dto.TempHumidityDto;
import nsmith167.homemetrics.temphumidity.model.TempHumidityReading;
import org.springframework.stereotype.Component;

@Component
public class TempHumidityDtoMapper {

    public TempHumidityDto tempHumidityReadingToDto(TempHumidityReading reading) {
        return new TempHumidityDto(
                reading.timestamp(),
                reading.metadata().location(),
                reading.temperature(),
                reading.humidity()
        );
    }
}
