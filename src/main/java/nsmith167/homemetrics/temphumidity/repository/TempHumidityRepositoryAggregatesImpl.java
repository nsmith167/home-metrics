package nsmith167.homemetrics.temphumidity.repository;

import nsmith167.homemetrics.temphumidity.model.TempHumidityReading;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TempHumidityRepositoryAggregatesImpl implements TempHumidityRepositoryAggregates {

    private final MongoOperations mongoTemplate;

    public TempHumidityRepositoryAggregatesImpl(MongoOperations mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @Override
    public List<TempHumidityReading> findTempHumidityReadingsByAggregatePipeline(Aggregation aggregation) {
        return mongoTemplate.aggregate(aggregation, "temp-humidity", TempHumidityReading.class).getMappedResults();
    }
}
