package com.basic.sensor2browser.dto;

import java.util.List;

/**
 * Simple wrapper object representing a JSON payload that contains
 * a list of sensor readings sent by an MCU in a single request.
 */
public class ReadingsPayload {
    private List<ReadingDto> readings;

    public List<ReadingDto> getReadings() { return readings; }
    public void setReadings(List<ReadingDto> readings) { this.readings = readings; }
}

