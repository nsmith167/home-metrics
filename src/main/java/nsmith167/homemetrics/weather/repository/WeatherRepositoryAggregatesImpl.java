package nsmith167.homemetrics.weather.repository;

import nsmith167.homemetrics.weather.model.WeatherReading;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WeatherRepositoryAggregatesImpl implements WeatherRepositoryAggregates {

    private final MongoOperations mongoTemplate;

    public WeatherRepositoryAggregatesImpl(MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public List<WeatherReading> findWeatherReadingsByAggregationPipeline(Aggregation aggregation) {
        return mongoTemplate.aggregate(aggregation, "weather", WeatherReading.class).getMappedResults();
    }
}
