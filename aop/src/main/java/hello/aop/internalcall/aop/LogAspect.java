package hello.aop.internalcall.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Slf4j
public class LogAspect {

    @Before("execution(* hello.aop.internalcall..*.*(..))")
    public void log(JoinPoint joinPoint) {
        log.info("[log]{}, args={}", joinPoint.getSignature(), joinPoint.getArgs());
    }
}
