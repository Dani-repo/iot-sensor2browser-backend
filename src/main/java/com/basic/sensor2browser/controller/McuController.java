package com.basic.sensor2browser.controller;

import com.basic.sensor2browser.dto.ReadingDto;
import com.basic.sensor2browser.dto.ReadingsPayload;
import com.basic.sensor2browser.model.Mcu;
import com.basic.sensor2browser.model.SensorReading;
import com.basic.sensor2browser.service.McuService;
import com.basic.sensor2browser.service.SensorReadingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * REST controller that receives sensor readings from the MCU devices
 * and stores them as SensorReading in the DB.
 */
@RestController
@RequestMapping("api/v1")
public class McuController {

    @Autowired
    McuService mcuService;

    @Autowired
    SensorReadingService sensorReadingService;

    @Value("${MCU_TOKEN}")
    private String expectedMcuToken;

    /**
     * Accepts a batch of readings from an MCU and stores each one in the DB.
     * Returns a JSON response indicating the no# of readings that were processed.
     */
    @PostMapping("/mcu/readings")
    public ResponseEntity<Object> receiveReadings(@RequestBody ReadingsPayload payload,
                                                  @RequestHeader(value = "X-MCU-TOKEN", required = false) String mcuTokenHeader) {
        if (mcuTokenHeader == null || !mcuTokenHeader.equals(expectedMcuToken)) {
            Map<String, Object> body = new HashMap<>();
            body.put("status", "error");
            body.put("message", "Invalid MCU token");
            return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
        }

        Mcu mcu = mcuService.getOrCreateDefaultMcu();
        List<ReadingDto> readings = payload.getReadings();

        for (ReadingDto reading : readings) {
            System.out.println("Sensor: " + reading.getSensorName()
                    + ", value: " + reading.getValue()
                    + ", time: " + reading.getRecordedAt());

            SensorReading sensorReading = new SensorReading();
            sensorReading.setMcu_id(mcu);
            sensorReading.setSensorName(reading.getSensorName());
            sensorReading.setValue(reading.getValue());
            sensorReadingService.save(sensorReading);
        }

        Map<String, Object> body = new HashMap<>();
        body.put("status", "ok");
        body.put("count", readings.size());

        return new ResponseEntity<>(body, HttpStatus.OK);
    }
}
