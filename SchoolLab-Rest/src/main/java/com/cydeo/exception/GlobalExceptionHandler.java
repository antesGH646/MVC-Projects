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
        //create a json ExceptionWrapper object
        ExceptionWrapper exceptionWrapper = new ExceptionWrapper();
        //setting the messages
        exceptionWrapper.setTimeStamp(LocalDateTime.now());
        exceptionWrapper.setMessage(exception.getMessage());
        exceptionWrapper.setStatus(HttpStatus.NOT_FOUND.value());
        exceptionWrapper.setPath(request.getRequestURI());//captures the path after the base url for return
        //returning customized exception message response
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)//returns the 404
                .body(exceptionWrapper);//returns the body timestamp, status, message, and path
    }

    /**
     * When a user forgets to include a required fields value in the
     * request body, Spring will throw a MethodArgumentNotValidException
     * due to the missed required values based on the added validations.
     * This method captures the Exception and sends back why the exception
     * is happening, and displays the field that is causing the exception
     * @param ex MethodArgumentNotValidException
     * @param request HttpServletRequest
     * @return a customized exception message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionWrapper> handleValidationError(MethodArgumentNotValidException ex,HttpServletRequest request){

        //creating an object of the ExceptionWrapper
        ExceptionWrapper exceptionWrapper = new ExceptionWrapper();

        //Assigning the fields of the ExceptionWrapper
        exceptionWrapper.setMessage("Invalid Input(s)");
        exceptionWrapper.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionWrapper.setTimeStamp(LocalDateTime.now());
        exceptionWrapper.setPath(request.getRequestURI());

        //Storing a list of errors
        List<ValidationError> validationErrorsList = new ArrayList<>();

        //BindingResult has methods to capture the errors
        for (ObjectError error : ex.getBindingResult().getAllErrors()){
            //storing the errors based on their types
            String fieldName = ((FieldError) error).getField();
            Object rejectedValue = ((FieldError) error).getRejectedValue();
            String errorMessage = error.getDefaultMessage();
            //assigning the errors into the ValidatorError
            ValidationError validationError = new ValidationError(fieldName, rejectedValue, errorMessage);
            validationErrorsList.add(validationError);//adding the errors into the list
        }

        //setting the list of validating errors into the ExceptionWrapper object
        exceptionWrapper.setValidationErrorList(validationErrorsList);
        //setting the number of errors into the counter
        exceptionWrapper.setErrorCount(validationErrorsList.size());

        //returning a status and the customized exceotion message
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exceptionWrapper);
    }
}
