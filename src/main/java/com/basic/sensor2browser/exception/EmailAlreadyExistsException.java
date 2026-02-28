package com.basic.sensor2browser.exception;

/**
 * Exception thrown when attempting to register a user with an email
 * address that already exists in the system.
 */
public class EmailAlreadyExistsException extends Throwable{

    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
