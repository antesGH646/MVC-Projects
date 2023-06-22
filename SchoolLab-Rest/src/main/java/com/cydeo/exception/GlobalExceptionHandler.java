package com.cydeo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Whenever the methods throw Java exceptions, Spring needs to know.
 * This class handles any thrown exception using
 * the @ExceptionHandler(customException.class) annotation.
 * create an exception handler methods under this annotation
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * This method returns meaningful customized response body
     * @param exception NotFoundException
     * @param request HttpServletRequest captures and returns anything after the base url
     * @return A customized response body instead of the default internal service error and
     * status code 500, meaning it returns a meaningful customized response body
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ExceptionWrapper> handlerNotFoundException(NotFoundException exception,
                                                                     HttpServletRequest request) {
        //create a json ExceptionWrapper and return
        ExceptionWrapper exceptionWrapper = new ExceptionWrapper();
        exceptionWrapper.setTimeStamp(LocalDateTime.now());
        exceptionWrapper.setMessage(exception.getMessage());
        exceptionWrapper.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionWrapper.setPath(request.getRequestURI());//captures the path after the base url for return

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)//returns the 404
                .body(exceptionWrapper);//returns the body timestamp, status, message, and path
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionWrapper> handleValidationError(MethodArgumentNotValidException ex,HttpServletRequest request){
        ExceptionWrapper exceptionWrapper = new ExceptionWrapper();
        exceptionWrapper.setMessage("Invalid Input(s)");
        exceptionWrapper.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionWrapper.setTimeStamp(LocalDateTime.now());
        exceptionWrapper.setPath(request.getRequestURI());

        List<ValidationError> validationErrors = new ArrayList<>();

        for (ObjectError error : ex.getBindingResult().getAllErrors()){

            String fieldName = ((FieldError) error).getField();
            Object rejectedValue = ((FieldError) error).getRejectedValue();
            String errorMessage = error.getDefaultMessage();

            ValidationError validationError = new ValidationError(fieldName, rejectedValue, errorMessage);
            validationErrors.add(validationError);
        }

        exceptionWrapper.setValidationErrorList(validationErrors);
        exceptionWrapper.setErrorCount(validationErrors.size());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionWrapper);
    }
}
