package com.cydeo.enums;

/**
 * Gender is created for the radio buttons gender selection
 * in the user create form.
 */
public enum Gender {

    // MALE, FEMALE
    //most used way, if you want to modify the values
    MALE("Male"), FEMALE("Female");
    private final String value;

    Gender(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
