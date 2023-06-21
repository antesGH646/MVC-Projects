package com.cydeo.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExceptionWrapper {

    private LocalDateTime localDateTime;
    private Integer status;
    private String path;
    private String message;
}
