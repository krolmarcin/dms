package pl.com.bottega.dms.infrastructure.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
public class ProfilingAspect {

    @Around("execution(* pl.com.bottega.dms.application..*.*(..))")
    public Object profile(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = new Date().getTime();
        try {
            Object returnValue = joinPoint.proceed();
            logTime(joinPoint, startTime);
            return returnValue;
        } catch (Throwable ex) {
            logTime(joinPoint, startTime);
            throw ex;
        }
    }

    private void logTime(ProceedingJoinPoint joinPoint, long startTime) {
        long endTime = new Date().getTime();
        long deltaTime = endTime - startTime;
        String msg = String.format("Execution %s took %s ms", joinPoint.getSignature(), deltaTime);
        Logger.getLogger(ProfilingAspect.class).info(msg);
    }

}