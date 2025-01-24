package nsmith167.homemetrics.weather.mapper;

import nsmith167.homemetrics.weather.consumer.model.WeatherScannerMessage;
import nsmith167.homemetrics.weather.model.WeatherReading;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WeatherScannerMessageMapper {

    WeatherScannerMessageMapper INSTANCE = Mappers.getMapper(WeatherScannerMessageMapper.class);

    @Mapping(source = "zipCode", target = "metadata.zipCode")
    WeatherReading weatherScannerMessageToWeatherReading(WeatherScannerMessage message);
}
