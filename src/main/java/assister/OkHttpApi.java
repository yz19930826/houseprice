package assister;

import okhttp3.Headers;
import utils.OKHttpUtil;

import java.util.Map;

public class OkHttpApi implements HttpApi{

    @Override
    public String get(String url, Map<String, String> header) {
        return OKHttpUtil.get(url, Headers.of(header));
    }
}
