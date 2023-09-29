package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.app.v1.*;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {

    private final String[] PATTERNS = {"requ*", "order*", "save*"};

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        OrderControllerV1 orderControllerV1 = new OrderControllerV1Impl(orderServiceV1(logTrace));
        LogTraceFilterHandler traceFilterHandler = new LogTraceFilterHandler(orderControllerV1, logTrace, PATTERNS);
        OrderControllerV1 proxy = (OrderControllerV1) Proxy.newProxyInstance(
                logTrace.getClass().getClassLoader(),
                new Class[]{OrderControllerV1.class}, traceFilterHandler);

        return proxy;
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1Impl orderServiceV1 = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
        LogTraceFilterHandler traceFilterHandler = new LogTraceFilterHandler(orderServiceV1, logTrace, PATTERNS);
        OrderServiceV1 proxy = (OrderServiceV1) Proxy.newProxyInstance(logTrace.getClass().getClassLoader(), new Class[]{OrderServiceV1.class}, traceFilterHandler);

        return proxy;
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1 orderRepositoryV1 = new OrderRepositoryV1Impl();
        LogTraceFilterHandler traceFilterHandler = new LogTraceFilterHandler(orderRepositoryV1, logTrace, PATTERNS);
        OrderRepositoryV1 proxy = (OrderRepositoryV1) Proxy.newProxyInstance(logTrace.getClass().getClassLoader(), new Class[]{OrderRepositoryV1.class}, traceFilterHandler);

        return proxy;
    }
}
