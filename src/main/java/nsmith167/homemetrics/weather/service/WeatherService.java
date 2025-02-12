package nsmith167.homemetrics.weather.service;

import nsmith167.homemetrics.weather.model.WeatherReading;
import nsmith167.homemetrics.weather.repository.WeatherRepository;
import nsmith167.homemetrics.weather.repository.WeatherRepositoryAggregates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Component
public class WeatherService {

    private final WeatherRepository weatherRepository;

    public WeatherService(WeatherRepository weatherRepository) {
        this.weatherRepository = weatherRepository;
    }

    public WeatherReading getLatestWeather(int zipCode, Instant timestamp) {
        AggregationOperation matchZip = Aggregation.match(Criteria.where(WeatherRepositoryAggregates.DATA_MODEL_ZIP_PATH).is(zipCode));
        AggregationOperation filterTimestamp = Aggregation.match(Criteria.where(WeatherRepositoryAggregates.DATA_MODEL_TIMESTAMP_PATH).lte(timestamp));
        AggregationOperation sortByTimestampDesc = Aggregation.sort(Sort.Direction.DESC, WeatherRepositoryAggregates.DATA_MODEL_TIMESTAMP_PATH);
        AggregationOperation limitOne = Aggregation.limit(1);

        return weatherRepository.findWeatherReadingsByAggregationPipeline(
                newAggregation(matchZip, filterTimestamp, sortByTimestampDesc, limitOne)
        ).getFirst();
    }

    public List<WeatherReading> getWeatherHistory(int zipCode, long startTimeSeconds, long endTimeSeconds) {
        AggregationOperation matchZip = Aggregation.match(Criteria.where(WeatherRepositoryAggregates.DATA_MODEL_ZIP_PATH).is(zipCode));
        AggregationOperation sortByTimestampDesc = Aggregation.sort(Sort.Direction.DESC, WeatherRepositoryAggregates.DATA_MODEL_TIMESTAMP_PATH);
        //TODO: handle start and end times and call query
        return List.of();
    }

    public void saveWeatherReading(WeatherReading reading) {
        weatherRepository.save(reading);
    }
}
