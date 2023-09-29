package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BasicTest {
    @Test
    void basicConfig() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class);
        A a = applicationContext.getBean("a", A.class);
        a.call();

        Assertions.assertThatThrownBy(() -> applicationContext.getBean(B.class)).isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Slf4j
    @Configuration
    static class BasicConfig {

        @Bean
        public A a() {
            return new A();
        }
    }

    @Slf4j
    static class A {
        public void call() {
            log.info("call A'");
        }
    }

    @Slf4j
    static class B {
        public void call() {
            log.info("call B'");
        }
    }
}
