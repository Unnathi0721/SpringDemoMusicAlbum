package spring.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class AspectLogging {
    private Logger myLogger = Logger.getLogger(getClass().getName());
    @Before(value="execution(* spring.demo.controller.*.saveArtist(..))")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args=joinPoint.getArgs();
        for (Object arg: args) {
            myLogger.info("Arg: " + arg.toString());
        }
        myLogger.info("logBefore() is running!");
        myLogger.info("hijacked : " + joinPoint.getSignature().getName());
        myLogger.info("******");
    }
}
