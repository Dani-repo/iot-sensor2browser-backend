package com.basic.sensor2browser.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
/**
 * DTO representing a single sensor reading as it is
 * received from or sent to external clients (e.g. MCU JSON payloads).
 */
public class ReadingDto {

    private Integer id;
//    private String sensorName;
    private double value;
//    private String recorded_at;

    @JsonProperty("sensor")
    private String sensorName;

    @JsonProperty("timestamp")
    private String recorded_at;

    public String getSensorName() { return sensorName; }
    public void setSensorName(String sensor) { this.sensorName = sensor; }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    public String getRecordedAt() { return recorded_at; }
    public void setRecordedAt(String timestamp) { this.recorded_at = timestamp; }

}
