package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0() {
        Hello hello = new Hello();

        log.info("start");
        String result = hello.callA();
        log.info("result = {}", result);

        log.info("start");
        String result2 = hello.callB();
        log.info("result = {}", result2);

    }

    @Slf4j
    static class Hello {
        public String callA() {
            log.info("callA");
            return "A";
        }
        public String callB() {
            log.info("callB");
            return "B";
        }
    }


    @Test
    void reflection1() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        // callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(target, methodCallA);

        // callB 메서드 정보
        Method methodCallB = classHello.getMethod("callA");
        dynamicCall(target, methodCallB);
    }

    private static void dynamicCall(Hello target, Method methodCallB) throws IllegalAccessException, InvocationTargetException {
        log.info("start");
        Object result2 = methodCallB.invoke(target);
        log.info("result = {}", result2);
    }
}
