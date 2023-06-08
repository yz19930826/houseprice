package utils;

import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.cert.X509Certificate;

public class OKHttpUtil {


    private static final OkHttpClient client;

    static {
        // 创建信任所有证书的 TrustManager
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // 设置自定义的 SSLSocketFactory 和 HostnameVerifier
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        client = builder.build();
    }


    public static String get(String url, Headers headers){
        Request request = new Request.Builder()
                .headers(headers)
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String post(String url, Headers headers,FormBody formBody){

        // 创建请求对象
        Request request = new Request.Builder()
                .url(url)
                .headers(headers)
                .post(formBody)
                .build();

        // 发起请求
        try {
            Response response = client.newCall(request).execute();

            // 处理响应
            if (response.isSuccessful()) {
                // 获取响应体内容
                String responseBody = response.body().string();
                return responseBody;
            } else {
                throw new IllegalStateException();
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
