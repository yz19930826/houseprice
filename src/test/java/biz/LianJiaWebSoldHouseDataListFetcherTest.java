package biz;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LianJiaWebSoldHouseDataListFetcherTest {

    private LianJiaWebSoldHouseDataListFetcher fetcher;

    private LianJiaWebLoginContext loginContext;

    @BeforeEach
    public void before(){
        fetcher = new LianJiaWebSoldHouseDataListFetcher();
        loginContext = new LianJiaWebLoginContext();
    }

    @Test
    void fetch() {
        List<String> fetch = fetcher.fetch("1111047382388",loginContext);
        System.out.println(fetch);
        assertEquals(fetch.size() ,0);
    }

    @Test
    void testMockito() {
        JSON mock = Mockito.mock(JSON.class);
        Mockito.when(mock.toJSONString()).thenThrow(new IOException("mock exception"));
//        String s = mock.toJSONString();
//        System.out.println(s);
    }
}