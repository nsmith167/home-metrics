package nsmith167.homemetrics.temphumidity.repository;

import nsmith167.homemetrics.temphumidity.model.TempHumidityReading;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import java.util.List;

public interface TempHumidityRepositoryAggregates {

    String DATA_MODEL_LOCATION_PATH = "metadata.location";
    String DATA_MODEL_TIMESTAMP_PATH = "timestamp";

    List<TempHumidityReading> findTempHumidityReadingsByAggregatePipeline(Aggregation aggregation);
}
