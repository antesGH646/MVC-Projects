package com.cydeo.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * You can log any action inside the application methods, but using Spring AOP you can
 * monitor any performed action of any class method separately inside an aspect class.
 * An aspect class must have both the @Aspect and @Configuration class.
 */
@Aspect//marks that this is an aspect class
@Configuration//marks that this is a configuration class
public class LoggingAspect {

    //creating a Logger that logs the LoggingAspect class
    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * This method gets the username details from keycloak authorization
     * Since keycloak is controlling all the login and token generation
     * SimpleKeycloakAccount captures the Account details so the method
     * would be able to returns the username.
     * details.
     * @return String details
     */
    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        SimpleKeycloakAccount details = (SimpleKeycloakAccount) authentication.getDetails();
        return details.getKeycloakSecurityContext().getToken().getPreferredUsername();
    }

    /**
     * This pointcut method monitors any method of any parameter for both the
     * ProjectController and TaskController.
     *      1st. Define what you want to monitor(Pointcut expressions)
     *           inside the @Pointcut
     *      2nd. Create the Pointcut method
     */
    @Pointcut("execution(* com.cydeo.controller.ProjectController.*(..)) || " +
            "execution(* com.cydeo.controller.TaskController.*(..))")
    private void anyControllerOperation() {}

    /**
     * In an advice method:
     *    1st define/pass the pointcut method
     *    2nd define/create the before advice method
     * @param joinPoint Joined points
     */
    @Before("anyControllerOperation()")//defining/passing the pointcut method
    public void beforeAnyControllerOperation(JoinPoint joinPoint) {//creating before method
       String username = getUserName();
        logger.info("Before () -> User : {} - Method: {} - Parameters: {}", username, joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    /**
     * - The @AfterReturning accepts two parameters the pointcut and the returning
     *   1st define pointcut as a key and assign it with the pointcut method name
     *   2nd define the returning as a key with the results
     * - create the after returning method with the JoinPoint and Object parameters
     */
    @AfterReturning(pointcut = "anyControllerOperation()", returning = "results")
    public void afterReturningAnyControllerOperationAdvice(JoinPoint joinPoint, Object results) {
        String username = getUserName();
        logger.info("AfterReturning -> User: {} - Method: {} - Results: {}", username, joinPoint.getSignature().toShortString(), results.toString());
    }

    /**
     * This method monitors if exception due to null values is handled after an api url is sent
     * - The @AfterThrowing accepts two parameters the Pointcut and the RuntimeException
     *       1st define pointcut as a key and assign it with the pointcut method name
     *      2nd define the RuntimeException as a key with the exception
     * - create the after throwing method with the JoinPoint and RuntimeException parameters
     */
    @AfterThrowing(pointcut = "anyControllerOperation()", throwing = "exception")
    public void afterThrowingAnyControllerOperationAdvice(JoinPoint joinPoint, RuntimeException exception) {
        String username = getUserName();
        logger.info("AfterThrowing -> User: {} - Method: {} - Exception: {}", username, joinPoint.getSignature().toShortString(), exception.getMessage());
    }
}
