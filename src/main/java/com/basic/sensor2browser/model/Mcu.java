package com.basic.sensor2browser.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * JPA entity representing a microcontroller unit (MCU) device that owns sensor
 * readings and is associated with one or more SensorReading records.
 */
@Entity
@Table(name = "mcus")
public class Mcu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable=false)
    @NotBlank(message="MCU identifier must not be empty")
    private String identifier;

    @Column
    private String model;

    @Column
    private String mcuDetails;

    @Column(nullable = false)
    private String secret_token;

    @Column
    private Boolean mcuStatus = true;

    @Column
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime created_at;

    @Column
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime last_seen_at;

    public Integer getId() {
        return id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getMcuDetails() {
        return mcuDetails;
    }

    public void setMcuDetails(String specs) {
        this.mcuDetails = specs;
    }

    public String getSecret_token() {
        return secret_token;
    }

    public void setSecret_token(String secret_token) {
        this.secret_token = secret_token;
    }

    public Boolean getMcuStatus() {
        return mcuStatus;
    }

    public void setMcuStatus(Boolean in_operation) {
        this.mcuStatus = in_operation;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public LocalDateTime getLast_seen_at() {
        return last_seen_at;
    }
}

