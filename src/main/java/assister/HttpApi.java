package assister;

import java.util.Map;

public interface HttpApi {
    String get(String url, Map<String,String> header);
}
