package com.basic.sensor2browser.repository;

import com.basic.sensor2browser.model.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for accessing SensorReading entities.
 */
@Repository
public interface SensorReadingRepository extends JpaRepository<SensorReading, Integer> {
}


