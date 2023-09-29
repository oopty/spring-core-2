package hello.aop.internalcall;

import hello.aop.internalcall.aop.LogAspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import({CallServiceV3.class, LogAspect.class})
class CallServiceV3Test {

    @Autowired
    CallServiceV3 callService;

    @Test
    void externalTest() {
        callService.external();
    }
}