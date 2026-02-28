package com.basic.sensor2browser.exception;
//DONE

/**
 * Exception used to signal that an incoming HTTP request body could
 * not be parsed or is having invalid syntax for the expected format.
 */
public class MessageNotReadableException extends RuntimeException {
    public MessageNotReadableException() {
        super("Invalid Data. Please check again..");
    }
}
