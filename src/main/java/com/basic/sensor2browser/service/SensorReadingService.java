package com.basic.sensor2browser.service;

import com.basic.sensor2browser.model.SensorReading;
import com.basic.sensor2browser.repository.SensorReadingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service that encapsulates persistence operations
 * for SensorReading entities.
 */
@Service
public class SensorReadingService {

    private final SensorReadingRepository sensorReadingRepository;

    public SensorReadingService(SensorReadingRepository sensorReadingRepository) {
        this.sensorReadingRepository = sensorReadingRepository;
    }

    /**
     * Persists the given SensorReading entity and returns the managed
     * instance from the repository.
     */
    public SensorReading save(SensorReading sensorReading) {
        return sensorReadingRepository.save(sensorReading);
    }

    /**
     * Retrieves all sensor readings currently stored in the database.
     */
    public List<SensorReading> findAll() {
        return sensorReadingRepository.findAll();
    }
}
