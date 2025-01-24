package nsmith167.homemetrics.weather.mapper;

import nsmith167.homemetrics.weather.dto.WeatherReadingDto;
import nsmith167.homemetrics.weather.model.WeatherReading;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeatherReadingDtoMapper {

    WeatherReadingDtoMapper INSTANCE = Mappers.getMapper(WeatherReadingDtoMapper.class);

    @Mapping(source = "metadata.zipCode", target = "zipCode")
    WeatherReadingDto weatherReadingToWeatherReadingDto(WeatherReading reading);
}
