package nsmith167.homemetrics.temphumidity.repository;

import nsmith167.homemetrics.temphumidity.model.TempHumidityReading;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempHumidityRepository extends MongoRepository<TempHumidityReading, String>, TempHumidityRepositoryAggregates {
}
