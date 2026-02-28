package com.basic.sensor2browser.exception;
//DONE

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
/**
 * Centralized exception handler that customizes error responses for
 * validation errors, unreadable messages, and not-found conditions.
 */
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // 1. When the user sends data that is not readable,
    // throw the error: handleHttpMessageNotReadableException
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        // Prepare the custom message using MessageNotReadableException

        MessageNotReadableException messageNotReadableException = new MessageNotReadableException();

        // store the various error responses in HashMap, to returning as part of the exception handling response
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error", messageNotReadableException.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // 2. When the user sends a request body that is empty OR incomplete
    // call up handleMethodArgumentNotValid to respond to @Valid annotation applied to @RequestBody
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(err -> { /** revised to getFieldErrors(); */
            String field = err.getField();                      /** call the error's field **/
            String message = err.getDefaultMessage();
            errors.put(field, message);
        });

        // store the various error responses in a HashMap, to returning as part of the exception handling response
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error:", errors);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // ------------------------


    // 3. Manage ResourceNotFoundException at a global level
    // Derive our own custom methods to handle httpEntityNotFound
    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity<Object> httpEntityNotFound(ResourceNotFoundException ex){

        // store various error responses in a HashMap, to returning as part of the exception handling response
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        //  will be triggered by PUT http://localhost:8888/customer/xxx where xxx is not present
    }

    // 4. Manage NoResourceFoundException at a global level
    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex,
                                                                    HttpHeaders headers,
                                                                    HttpStatusCode status,
                                                                    WebRequest request) {
        // Prepare the custom message using MessageNotReadableException
        // Reusing ResourceNotFoundException for the purpose of catching exception when no path variable is applied.


        ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException("Resource not found");

        // store the various error responses in a HashMap, to returning as part of the exception handling response
        Map<String, String> errorResponse = new HashMap<>();

        errorResponse.put("error:", resourceNotFoundException.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        // will be triggered if no path variable (no customer ID) provided
        // by PUT http://localhost:8888/customer/
    }
}
