package nsmith167.homemetrics.weather.repository;

import nsmith167.homemetrics.weather.model.WeatherReading;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends MongoRepository<WeatherReading, String>, WeatherRepositoryAggregates {
}
