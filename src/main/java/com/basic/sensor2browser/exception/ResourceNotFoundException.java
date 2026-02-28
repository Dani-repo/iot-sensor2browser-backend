package com.basic.sensor2browser.exception;
//DONE
// Custom method

/**
 * Exception used when a requested resource (eg. a user or sensor reading)
 * cannot be found in the DB.
 */
public class ResourceNotFoundException extends Throwable {

    public ResourceNotFoundException(String message) {

        super(message);
    }
}
