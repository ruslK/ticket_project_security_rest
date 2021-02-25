package com.ticketsproject.aspect;

import com.ticketsproject.exception.TicketingProjectException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Aspect
@Configuration
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(* com.ticketsproject.controller.ProjectController.*(..)) " +
            "|| execution(* com.ticketsproject.controller.TaskController.*(..))")
    public void anyControllerOperations() {
    }

    @Before("anyControllerOperations()")
    public void anyBeforeControllerOperationsAdvice(JoinPoint joinPoint) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Before(User:{} - Method:{} - Parameter:{}",
                auth.getName(), joinPoint.getSignature().toShortString(), joinPoint.getArgs());
    }

    @AfterReturning(pointcut = "anyControllerOperations()", returning = "results")
    public void anyAfterReturningControllerOperationAdvice(JoinPoint joinPoint, Object results) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("AfterReturning (User:{} - Method:{} - Parameter:{})",
                auth.getName(), joinPoint.getSignature().toShortString(), results.toString());
    }

    @AfterThrowing(pointcut = "anyControllerOperations()", throwing = "exception")
    public void anyAfterThrowingControllerOperationAdvise(JoinPoint joinPoint, TicketingProjectException exception) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("AfterThrowing (User:{} - Method:{} - Exception:{})",
                auth.getName(), joinPoint.getSignature().toShortString(), exception.getLocalizedMessage());
    }


}

