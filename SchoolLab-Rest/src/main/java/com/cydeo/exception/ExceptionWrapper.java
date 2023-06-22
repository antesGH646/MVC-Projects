package com.cydeo.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)//doest not display if the values are null
public class ExceptionWrapper {

    private LocalDateTime timeStamp;
    private Integer status;
    private String path;
    private String message;

    private List<ValidationError> validationErrorList;
    private Integer errorCount;
}
