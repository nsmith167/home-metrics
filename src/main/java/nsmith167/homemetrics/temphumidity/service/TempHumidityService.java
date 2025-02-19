package nsmith167.homemetrics.temphumidity.service;

import nsmith167.homemetrics.temphumidity.model.TempHumidityReading;
import nsmith167.homemetrics.temphumidity.repository.TempHumidityRepository;
import nsmith167.homemetrics.temphumidity.repository.TempHumidityRepositoryAggregates;
import nsmith167.homemetrics.weather.model.WeatherReading;
import nsmith167.homemetrics.weather.repository.WeatherRepositoryAggregates;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Service
public class TempHumidityService {

    private final TempHumidityRepository tempHumidityRepository;

    public TempHumidityService(TempHumidityRepository tempHumidityRepository) {
        this.tempHumidityRepository = tempHumidityRepository;
    }

    public TempHumidityReading getLatestTempHumidityReading(String location, Instant timestamp) {
        AggregationOperation matchZip = Aggregation.match(Criteria.where(TempHumidityRepositoryAggregates.DATA_MODEL_LOCATION_PATH).is(location));
        AggregationOperation filterTimestamp = Aggregation.match(Criteria.where(TempHumidityRepositoryAggregates.DATA_MODEL_TIMESTAMP_PATH).lte(timestamp));
        AggregationOperation sortByTimestampDesc = Aggregation.sort(Sort.Direction.DESC, TempHumidityRepositoryAggregates.DATA_MODEL_TIMESTAMP_PATH);
        AggregationOperation limitOne = Aggregation.limit(1);

        return tempHumidityRepository.findTempHumidityReadingsByAggregatePipeline(
                newAggregation(matchZip, filterTimestamp, sortByTimestampDesc, limitOne)
        ).getFirst();
    }

    public List<TempHumidityReading> getTempHumidityHistory(String location, Instant startTime, Instant endTime) {
        AggregationOperation matchZip = Aggregation.match(Criteria.where(TempHumidityRepositoryAggregates.DATA_MODEL_LOCATION_PATH).is(location));
        AggregationOperation sortByTimestampDesc = Aggregation.sort(Sort.Direction.DESC, TempHumidityRepositoryAggregates.DATA_MODEL_TIMESTAMP_PATH);
        AggregationOperation filterTimestamp = Aggregation.match(Criteria.where(TempHumidityRepositoryAggregates.DATA_MODEL_TIMESTAMP_PATH).lte(endTime).gte(startTime));
        return tempHumidityRepository.findTempHumidityReadingsByAggregatePipeline(
                newAggregation(matchZip, sortByTimestampDesc, filterTimestamp)
        );
    }

    public void saveTempHumidityReading(TempHumidityReading reading) {
        tempHumidityRepository.save(reading);
    }
}
