package biz;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AppFollowedQueryLoginContextTest {

    @Test
    void headers() {
        AppFollowedQueryLoginContext context = new AppFollowedQueryLoginContext();
        Map<String, String> headers = context.headers();
        assertTrue(headers.containsKey("cookie"));
        assertTrue(headers.containsKey("lianjia-access-token"));
    }
}