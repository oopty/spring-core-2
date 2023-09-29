package hello.aop.proxyvs;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Slf4j
public class ProxyAspect {
    @Before("execution(* hello.aop..*.*(..))")
    public void log(JoinPoint joinPoint) {
        log.info("[proxyAspect] {}", joinPoint.getSignature());
    }
}
