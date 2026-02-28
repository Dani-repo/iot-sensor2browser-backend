package com.basic.sensor2browser.repository;

import com.basic.sensor2browser.model.Mcu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for accessing MCU device entities.
 */
@Repository
public interface McuRepository extends JpaRepository<Mcu, Integer> {

}
