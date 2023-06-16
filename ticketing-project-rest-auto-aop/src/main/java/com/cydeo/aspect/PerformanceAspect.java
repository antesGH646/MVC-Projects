package com.cydeo.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * You can track performance of any object inside the application methods,
 * but using Spring AOP you can monitor any performed action of any class
 * method separately inside an aspect class.
 * An aspect class must have both the @Aspect and @Configuration class.
 */
@Aspect
@Configuration
public class PerformanceAspect {

    //creating a logger for the PerformanceAspect class
    Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    @Pointcut("@annotation(com.cydeo.annotation.ExecutionTime)")//defining/passing the custom annotation path
    private void anyExecutionTimeOperation(){}

    /**
     * Inorder to use proceed() method need to pass the ProceedingJointPoint interface
     * @param proceedingJoinPoint ProceedingJointPoint
     * @return Object
     */
    @Around("anyExecutionTimeOperation()") //passing the Pointcut name
    public Object anyExecutionOperationAdvice(ProceedingJoinPoint proceedingJoinPoint) {

        //defining the time before the execution
        long beforeTime = System.currentTimeMillis();
        Object result;

        logger.info("Execution starting");

        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        //defining the time after the execution
        long afterTime = System.currentTimeMillis();
        //logging the calculated performance time of the business logic or method or object of the application
        logger.info("Performance time: {} ms - Method: {} - Parameter: {}",
                (afterTime - beforeTime),
                proceedingJoinPoint.getSignature().toShortString(),
                proceedingJoinPoint.getArgs());
        return result;
    }

}
