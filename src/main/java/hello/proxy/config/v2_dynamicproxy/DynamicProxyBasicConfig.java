package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.app.v1.*;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyBasicConfig {

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1(logTrace));
        LogTraceBasicHandler traceBasicHandler = new LogTraceBasicHandler(orderControllerV1, logTrace);
        OrderControllerV1 proxy = (OrderControllerV1) Proxy.newProxyInstance(logTrace.getClass().getClassLoader(), new Class[]{OrderControllerV1.class}, traceBasicHandler);

        return proxy;
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1Impl orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
        LogTraceBasicHandler traceBasicHandler = new LogTraceBasicHandler(orderServiceV1, logTrace);
        OrderServiceV1 proxy = (OrderServiceV1) Proxy.newProxyInstance(logTrace.getClass().getClassLoader(), new Class[]{OrderServiceV1.class}, traceBasicHandler);

        return proxy;
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1 orderRepositoryV1 = new OrderRepositoryV1Impl();
        LogTraceBasicHandler traceBasicHandler = new LogTraceBasicHandler(orderRepositoryV1, logTrace);
        OrderRepositoryV1 proxy = (OrderRepositoryV1) Proxy.newProxyInstance(logTrace.getClass().getClassLoader(), new Class[]{OrderRepositoryV1.class}, traceBasicHandler);

        return proxy;
    }
}
