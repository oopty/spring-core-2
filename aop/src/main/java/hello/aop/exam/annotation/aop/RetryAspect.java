package hello.aop.exam.annotation.aop;

import hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {
    @Around("@annotation(retry)")
    public Object retryAdvice(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        int maxRetryNumber = retry.value();
        Exception exceptionHolder = null;
        for(int retryNum = 1; retryNum <= maxRetryNumber; retryNum++) {
            try {
                return joinPoint.proceed();
            } catch (Exception e) {
                log.info("[retry] {}/{}", retryNum, maxRetryNumber);
                exceptionHolder = e;
            }
        }
        throw exceptionHolder;
    }
}
