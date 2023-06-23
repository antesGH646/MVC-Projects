package com.cydeo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is designed to send back a message to the user
 * if the user misses to write a required value field of the DTO
 * in the request body when posting a data.
 * Object of this class is used inside the ExceptionWrapper.
 */
@Getter
@Setter
@AllArgsConstructor
public class ValidationError {
    private String errorField;//to store the field that is causing the error
    private Object rejectedValue;//to hold any type of value
    private String reason;//the reason why it was rejected
}
