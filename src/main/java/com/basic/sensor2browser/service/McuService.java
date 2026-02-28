package com.basic.sensor2browser.service;

import com.basic.sensor2browser.model.Mcu;
import com.basic.sensor2browser.repository.McuRepository;
import org.springframework.stereotype.Service;

/**
 * Service responsible for looking up and provisioning MCU entities
 * used to associate incoming sensor readings with a device.
 */
@Service
public class McuService {

    private final McuRepository mcuRepository;

    public McuService(McuRepository mcuRepository) {
        this.mcuRepository = mcuRepository;
    }

    public Mcu getOrCreateDefaultMcu() {
        return mcuRepository.findAll()
                .stream()
                .findFirst()
                .orElseGet(() -> {
                    Mcu mcu = new Mcu();
                    mcu.setIdentifier("default-mcu");
                    mcu.setSecret_token("default");
                    return mcuRepository.save(mcu);
                });
    }
}
