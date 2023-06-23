package com.cydeo.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This class is designed to send additional json responses in addition to
 * the DTO serialized fields and values in the response body.
 * A list of errors are also serialized with these custom messages
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)//doest not display if the values are null
public class ExceptionWrapper {

    private LocalDateTime timeStamp;
    private Integer status;
    private String path;
    private String message;

    //It is a list, b/c may have multiple errors
    private List<ValidationError> validationErrorList;
    private Integer errorCount; //to count the number of errors
}
