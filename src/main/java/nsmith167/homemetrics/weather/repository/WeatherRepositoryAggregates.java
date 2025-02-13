package nsmith167.homemetrics.weather.repository;

import nsmith167.homemetrics.weather.model.WeatherReading;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import java.util.List;

public interface WeatherRepositoryAggregates {

    String DATA_MODEL_ZIP_PATH = "metadata.zip_code";
    String DATA_MODEL_TIMESTAMP_PATH = "timestamp";

    List<WeatherReading> findWeatherReadingsByAggregationPipeline(Aggregation aggregation);
}
