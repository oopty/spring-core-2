package hello.aop.pointcut;

import hello.aop.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

public class ArgsTest {
    Method helloMethod;

    @BeforeEach
    void setUp() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }

    @Test
    void args() {
         Assertions.assertThat(poincut("args(String)").matches(helloMethod, MemberServiceImpl.class))
                 .isTrue();
         Assertions.assertThat(poincut("args(..)").matches(helloMethod, MemberServiceImpl.class))
                 .isTrue();
         Assertions.assertThat(poincut("args(*)").matches(helloMethod, MemberServiceImpl.class))
                 .isTrue();
         Assertions.assertThat(poincut("args(String, ..)").matches(helloMethod, MemberServiceImpl.class))
                 .isTrue();

         Assertions.assertThat(poincut("args(String, String)").matches(helloMethod, MemberServiceImpl.class))
                 .isFalse();
         Assertions.assertThat(poincut("args(String, *)").matches(helloMethod, MemberServiceImpl.class))
                 .isFalse();

    }

    @Test
    @DisplayName("execution과 다른 점은 넘겨지는 파라미터의 상속관계도 적용된다")
    void argsMatchSuperTypeTrue() {
        Assertions.assertThat(poincut("args(Object)").matches(helloMethod, MemberServiceImpl.class)).isTrue();
        Assertions.assertThat(poincut("execution(* hello.aop.member.MemberServiceImpl.hello(Object))").matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    private AspectJExpressionPointcut poincut(String expression) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(expression);
        return pointcut;
    }
}
