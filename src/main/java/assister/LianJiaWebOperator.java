package assister;

import biz.HouseOperator;
import biz.LoginContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Headers;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import utils.OKHttpUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;

@Slf4j
public class LianJiaWebOperator implements HouseOperator {

    private static final String LIANJIA_WEB_UN_FOLLOW_API = "https://user.lianjia.com/site/deletefocushouse";

    private static final String LIANJIA_WEB_FOLLOW_API = "https://bj.lianjia.com/api/SetHouseFav?id=%s&isFav=1";



    @Override
    public int follow(String code, LoginContext context) {

        if (code == null || code.isEmpty())
            throw new IllegalArgumentException("code can not be empty");

        String followHouseFullUrl = String.format(LIANJIA_WEB_FOLLOW_API, code);
        HashMap<String, String> headers = new HashMap<>();
        headers.put("Referer", "https://bj.lianjia.com");

        Map<String, String> contextHeaderMap = context.headers();
        headers.putAll(contextHeaderMap);

        Headers of = Headers.of(headers);

        String strResult = OKHttpUtil.get(followHouseFullUrl, of);
        log.info("follow result:{}", strResult);

        JSONObject followResultJsonObject = JSON.parseObject(strResult);
        JSONObject data = followResultJsonObject.getJSONObject("data");

        if (data == null) {
            return -1;
        }
        Integer status = data.getInteger("status");
        if (!Objects.equals(status, 0)) {
            String msg = data.getString("msg");
            return -1;
        }
        return 0;
    }

    @Override
    public int unFollow(String code, LoginContext context) {
        /**
         * 链家接口返回数据结构
         * {
         *     "code": 1,
         *     "data": true,
         *     "msg": "取消关注成功"
         * }
         */

        Map<String, String> headers = context.headers();
        headers.put("Referer", "https://bj.lianjia.com");


        FormBody formBody = new FormBody.Builder()
                .add("id", code)
                .add("type", "ershoufang")
                .build();

        String strResult = OKHttpUtil.post(LIANJIA_WEB_UN_FOLLOW_API, Headers.of(headers), formBody);
        log.info("调用链家取消关注房源接口返回结果strResult=【{}】", strResult);

        JSONObject followResultJsonObject = JSON.parseObject(strResult);

        Integer respCode = followResultJsonObject.getInteger("code");
        //  code 为1表示成功
        if (!Objects.equals(respCode, 1)) {
            String msg = followResultJsonObject.getString("msg");
            log.error("调用链家取消关注房源接口返回结果中status不为0，msg=【{}】", msg);
            return -1;
        }
        return 0;
    }
}
