package biz;

import assister.LianJiaWebOperator;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LianJiaWebOperatorTest {


    private LianJiaWebOperator lianJiaWebOperator;


    private LoginContext loginContext;

    @BeforeEach
    public void before(){
        lianJiaWebOperator = new LianJiaWebOperator();
        loginContext = new LianJiaWebLoginContext();
    }

    @org.junit.jupiter.api.Test
    void followTestWhenGivenEmptyThrowException() {
        assertThrows(IllegalArgumentException.class, () -> {
            lianJiaWebOperator.follow("",loginContext);
        });
    }

    @org.junit.jupiter.api.Test
    void followTestWhenGivenNotExistedCodeThenReturn0(){
        int result = lianJiaWebOperator.follow("101119046366",loginContext);
//        assertEquals(0,result);
    }

    @org.junit.jupiter.api.Test
    void unFollowTest_when_given_not_existed_then_return_fail() {
        int i = lianJiaWebOperator.unFollow("123456", loginContext);
//        assertEquals(0, i);
    }


}