package nsmith167.homemetrics.temphumidity.service;

import nsmith167.homemetrics.temphumidity.model.TempHumidityReading;
import nsmith167.homemetrics.temphumidity.repository.TempHumidityRepository;
import org.springframework.stereotype.Service;

@Service
public class TempHumidityService {

    private TempHumidityRepository tempHumidityRepository;

    public TempHumidityService(TempHumidityRepository tempHumidityRepository) {
        this.tempHumidityRepository = tempHumidityRepository;
    }

    public void saveTempHumidityReading(TempHumidityReading reading) {
        tempHumidityRepository.save(reading);
    }
}
