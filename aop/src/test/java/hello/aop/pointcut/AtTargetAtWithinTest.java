package hello.aop.pointcut;

import hello.aop.member.annotation.ClassAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(AtTargetAtWithinTest.Config.class)
public class AtTargetAtWithinTest {

    @Autowired
    Child child;

    @Test
    void compareAtTargetAtWithin() {
        log.info("child = {}", child.getClass());
        child.childMethod();
        child.parentMethod();
    }

    public static class Config {
        @Bean
        public Child child() {
            return new Child();
        }
        @Bean
        public AtTargetAtWithinAspect atTargetAtWithinAspect() {
            return new AtTargetAtWithinAspect();
        }
    }

    public static class Parent {
        public void parentMethod() {}

    }
    @ClassAop
    public static class Child extends Parent {
        public void childMethod() {}
    }

    @Aspect
    @Slf4j
    public static class AtTargetAtWithinAspect {
        @Around("execution(* hello.aop.pointcut..*(..)) && @target(hello.aop.member.annotation.ClassAop)")
        public Object atTarget(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[AtTarget] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
        @Around("execution(* hello.aop.pointcut..*(..)) && @within(hello.aop.member.annotation.ClassAop)")
        public Object atWithin(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[AtWithin] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}