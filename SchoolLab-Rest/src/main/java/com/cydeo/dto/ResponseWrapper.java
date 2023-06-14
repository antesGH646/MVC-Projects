package com.cydeo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * If you want to add additional objects such as status code, headers on the top
 * of the DTO serialized Json object, you can define them in a separate class then
 * call the class whenever you want to need the objects. In this class, three objects
 * are defined along with the object we want to return, so can be serialized and
 * added to any serialized Json objects.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseWrapper {

    private boolean success;
    private String message;
    private Integer code;
    private Object data;//represents any serialized DTO


    //accepts two parameters a message to display alon with the Json object(serialized DTO)
    public ResponseWrapper(String message, Object data) {
        this.message = message;
        this.data = data;
        this.success= true;
        this.code = HttpStatus.OK.value();
    }

    //accepts two parameters a message to display
    public ResponseWrapper(String message) {
        this.message = message;
        this.success= true;
        this.code = HttpStatus.OK.value();
    }
}
