package hello.aop.internalcall;

import hello.aop.internalcall.aop.LogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({CallServiceV2.class, LogAspect.class})
class CallServiceV2Test {

    @Autowired
    CallServiceV2 callService;

    @Test
    void internalTest() {
        callService.internal();
    }
    @Test
    void externalTest() {
        callService.external();
    }
}