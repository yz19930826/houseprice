package biz;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class AppFollowedQueryLoginContext implements LoginContext{

    @Override
    public Map<String, String> headers() {
        return parseHeaders();
    }


    private Map<String,String> parseHeaders(){
        String headerStr = "host: apps.api.lianjia.com\n" +
                "lianjia-recommend-allowable: 1\n" +
                "accept: */*\n" +
                "device-info: scale=2.0;screenwidth=828;screenheight=1792\n" +
                "eleparentsceneid: -1\n" +
                "user-agent: HomeLink 9.79.40;iPhone12,1;iOS 15.4;\n" +
                "cookie: lianjia_uuid=5D10EBCB-7CCA-4459-9095-41C70D78F6E9; lianjia_ssid=3EFB2EFD-3F51-4D0B-8979-00CF7CA8B507; lianjia_udid=00000000-0000-0000-0000-000000000000; lianjia_token=2.00117aef666d6ba9ad00d7c65758baeb09; latitude=(null); longitude=(null)\n" +
                "dynamic-sdk-version: 1.1.0\n" +
                "uuid: 5D10EBCB-7CCA-4459-9095-41C70D78F6E9\n" +
                "lianjia-version: 9.79.40\n" +
                "wifi_name: \n" +
                "channel: lianjia\n" +
                "beikebasedata: %7B%0A%20%20%22duid%22%20%3A%20%22D2%2B1IcEY6yNVnEhSHjkeZypPbeFmpKMv5uXn79RBiTsHYXf6%22%2C%0A%20%20%22appVersion%22%20%3A%20%229.79.40%22%0A%7D\n" +
                "systeminfo-s: iOS;15.4\n" +
                "wll-kgsa: LJAPPVI accessKeyId=AFalPXNCeS8yoPy8; nonce=TBaWZiSVx6OfdhLS1O1GxQuetTgOpdtG; timestamp=1680677231; signedHeaders=SystemInfo-s,Device-id-s,Channel-s,AppInfo-s,Hardware-s; signature=31rnTIYwULxsfC6Q5SqoKgFKGidKloXCkl2tqn4aKGI=\n" +
                "accept-language: zh-Hans-CN;q=1\n" +
                "device-id-s: 683F42F8-A77D-4BA4-B585-6D809B989373;;\n" +
                "page-schema: profile%2Fershoufollowing\n" +
                "lianjia-access-token: 2.00117aef666d6ba9ad00d7c65758baeb09\n" +
                "parentsceneid: 548601211264802817\n" +
                "lng: 0\n" +
                "channel-s: lianjia\n" +
                "authorization: MjAxNzAzMjRfaW9zOmUzMzRmOGFiNWU2MzE4NWNjNzRiMzc5ZTFiMjdhMTcyYzRhZmMwMTE=\n" +
                "lat: 0\n" +
                "appinfo-s: HomeLink;9.79.40;9.79.40.1\n" +
                "extension: lj_idfa=00000000-0000-0000-0000-000000000000&lj_idfv=A72306D0-009D-4EF3-A233-093300AE6490&lj_device_id_ios=5D10EBCB-7CCA-4459-9095-41C70D78F6E9&lj_keychain_id=683F42F8-A77D-4BA4-B585-6D809B989373&lj_duid=D2+1IcEY6yNVnEhSHjkeZypPbeFmpKMv5uXn79RBiTsHYXf6&ketoken=\n" +
                "lianjia-device-id: 683F42F8-A77D-4BA4-B585-6D809B989373\n" +
                "lianjia-city-id: 110000\n" +
                "ip: 111.201.52.124\n" +
                "lianjia-timestamp: 1680677231.207172\n" +
                "hardware-s: iPhone12,1\n" +
                "lianjia-im-version: 1\n" +
                "Connection: close\n";

        String[] split = headerStr.split("\n");

        Map<String, String> resultMap = new LinkedHashMap<>();

        for (String s : split) {
            String[] kv = s.split(":");
            resultMap.put(kv[0],kv[1]);
        }
        return resultMap;

    }
}


