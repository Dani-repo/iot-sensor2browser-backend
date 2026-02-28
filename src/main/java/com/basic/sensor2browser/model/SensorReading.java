package com.basic.sensor2browser.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

/**
 * JPA entity representing a single sensor reading produced by an MCU,
 * including the owning MCU, sensor name, measured value, and timestamp.
 */
@Entity
public class SensorReading {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)    //
    @JoinColumn(name = "mcu_id", nullable = false)         // Many readings to one mcu
    @OnDelete(action = OnDeleteAction.CASCADE)              // manages the foreign constraint of parent entity (mcu)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // resolve BeanSerialization issue
    private Mcu mcu_id;

    @Column
    private String sensorName;

    @Column
    private double value;

    @Column
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime recorder_at;

    public Integer getId() {
        return id;
    }

    public Mcu getMcu_id() {
        return mcu_id;
    }

    public void setMcu_id(Mcu mcu) {
        this.mcu_id = mcu;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public LocalDateTime getRecorder_at() {
        return recorder_at;
    }
}
