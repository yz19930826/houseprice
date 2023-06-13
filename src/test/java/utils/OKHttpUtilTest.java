package utils;

import biz.AppFollowedQueryLoginContext;
import okhttp3.Headers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OKHttpUtilTest {

    @Test
    void get() {

        String s = OKHttpUtil.get("https://apps.api.lianjia.com/user/house/followedListV3?city_id=110000&house_type=ershoufang&limit_count=20&limit_offset=0&request_ts=1680677231", Headers.of(new AppFollowedQueryLoginContext().headers()));
        System.out.println(s);
    }

    @Test
    void post() {
    }
}