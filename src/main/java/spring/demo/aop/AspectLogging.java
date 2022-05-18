package spring.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogging {
    @Before(value="execution(* spring.demo.controller.*.saveArtist(..))")
    public void logBefore(JoinPoint joinPoint) {
        Object[] args=joinPoint.getArgs();
        for (Object arg: args) {
            System.out.println("Arg: " + arg.toString());
        }
        System.out.println("logBefore() is running!");
        System.out.println("hijacked : " + joinPoint.getSignature().getName());
        System.out.println("******");
    }
}
