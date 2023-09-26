package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanPostProcessorTest {
    @Test
    void basicConfig() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class);
        B b = applicationContext.getBean("a", B.class);
        b.call();

        Assertions.assertThatThrownBy(() -> applicationContext.getBean(A.class)).isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Slf4j
    @Configuration
    static class BeanPostProcessorConfig {

        @Bean
        public A a() {
            return new A();
        }

        @Bean
        public AtoBBeanPostProcessor atoBBeanPostProcessor() {
            return new AtoBBeanPostProcessor();
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

    @Slf4j
    static class AtoBBeanPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("bean = {}, beanName = {}", bean, beanName);

            if(bean instanceof A) {
                return new B();
            }

            return bean;
        }
    }
}
