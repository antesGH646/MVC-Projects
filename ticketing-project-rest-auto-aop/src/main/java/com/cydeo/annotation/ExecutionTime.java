package com.cydeo.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spring annotation must have both the @Target and the @Retention annotations
 */
@Target(ElementType.METHOD)//this annotation is used on the method level
@Retention(RetentionPolicy.RUNTIME)//used at run time
public @interface ExecutionTime {//name of the annotation, annotations are marked with @interface
}
