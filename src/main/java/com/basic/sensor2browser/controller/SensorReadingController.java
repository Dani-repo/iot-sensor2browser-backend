package com.basic.sensor2browser.controller;

import com.basic.sensor2browser.exception.ResourceNotFoundException;
import com.basic.sensor2browser.model.SensorReading;
import com.basic.sensor2browser.service.McuService;
import com.basic.sensor2browser.service.SensorReadingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


/**
 * REST controller that exposes sensor readings stored in the database
 * so that frontend can display them to browser.
 */
@RestController
@RequestMapping("api/v1/readings")

public class SensorReadingController {

    @Autowired
    SensorReadingService sensorReadingService;

    /**
     * Returns all stored sensor readings as JSON.
     * Throws ResourceNotFoundException when there are no records to return.
     */
    @GetMapping("")
    public ResponseEntity<Object> all(
    ) throws ResourceNotFoundException {
        List<SensorReading> sensorReadingList = sensorReadingService.findAll();
        if(sensorReadingList.isEmpty())
            throw new ResourceNotFoundException("No sensor readings available");
        return new ResponseEntity<>(sensorReadingList, HttpStatus.OK);
    }
}