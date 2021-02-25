package com.ticketsproject.aspect;

import com.ticketsproject.exception.TicketingProjectException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Aspect
@Configuration
public class LoggingAspect {

    private Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("execution(* com.ticketsproject.controller.ProjectController.*(..)) " +
            "|| execution(* com.ticketsproject.controller.TaskController.*(..))")
    public void anyControllerOperations() {
    }

    @Before("anyControllerOperations()")
    public void anyBeforeControllerOperationsAdvice(JoinPoint joinPoint) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Before(User:{} - Method:{} - Parameter:{}",
                auth.getName(), joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "anyControllerOperations()", returning = "results")
    public void anyAfterReturningControllerOperationAdvice(JoinPoint joinPoint, Object results) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("AfterReturning (User:{} - Method:{} - Parameter:{})",
                auth.getName(), joinPoint.getSignature().toShortString(), results.toString());
    }

    @AfterThrowing(pointcut = "anyControllerOperations()", throwing = "exception")
    public void anyAfterThrowingControllerOperationAdvise(JoinPoint joinPoint, TicketingProjectException exception) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("AfterThrowing (User:{} - Method:{} - Exception:{})",
                auth.getName(), joinPoint.getSignature().toShortString(), exception.getLocalizedMessage());
    }


}

