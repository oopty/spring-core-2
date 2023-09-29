package hello.aop.internalcall;

import hello.aop.internalcall.aop.LogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({CallServiceV0.class, LogAspect.class})
class CallServiceV0Test {

    @Autowired
    CallServiceV0 callService;

    @Test
    void internalTest() {
        callService.internal();
    }

    @Test
    void externalTest() {
        callService.external();
    }
}