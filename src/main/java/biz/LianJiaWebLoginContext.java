package biz;

import java.util.HashMap;
import java.util.Map;

public class LianJiaWebLoginContext implements LoginContext{

    @Override
    public Map<String, String> headers() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("cookie","lianjia_uuid=1760312a-4287-4832-83e2-4621c035c11d; _smt_uid=61a98b7c.4f36a0ca; _ga=GA1.2.1107032390.1639536011; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%2217d7e48dfe7232-0dabb06906a1c8-1f396452-2007040-17d7e48dfe8c08%22%2C%22%24device_id%22%3A%2217d7e48dfe7232-0dabb06906a1c8-1f396452-2007040-17d7e48dfe8c08%22%2C%22props%22%3A%7B%22%24latest_traffic_source_type%22%3A%22%E7%9B%B4%E6%8E%A5%E6%B5%81%E9%87%8F%22%2C%22%24latest_referrer%22%3A%22%22%2C%22%24latest_referrer_host%22%3A%22%22%2C%22%24latest_search_keyword%22%3A%22%E6%9C%AA%E5%8F%96%E5%88%B0%E5%80%BC_%E7%9B%B4%E6%8E%A5%E6%89%93%E5%BC%80%22%7D%7D; Hm_lvt_efa595b768cc9dc7d7f9823368e795f1=1685444695; _jzqx=1.1644487127.1686222728.13.jzqsr=google%2Ecom%2Ehk|jzqct=/.jzqsr=user%2Elianjia%2Ecom|jzqct=/; select_city=110000; lianjia_ssid=65dc1768-f750-423b-b54a-86629fcbee89; _jzqa=1.3922949061776099000.1638501245.1686222728.1690536948.30; _jzqc=1; _jzqckmp=1; _gid=GA1.2.778560383.1690536949; _gat=1; _gat_past=1; _gat_global=1; _gat_new_global=1; _gat_dianpu_agent=1; login_ucid=2000000091892519; lianjia_token=2.0012cfb7c06edef10b03629ef1cdd33ff0; lianjia_token_secure=2.0012cfb7c06edef10b03629ef1cdd33ff0; security_ticket=TEZphjQnWPN+CApn3mS007SU1COT1mktYDtLNngI5ttLhyzz3mVEWEivJA/72JVqUKPKEN6E+C7POwfk8pzMrKjkjes3P3OyAV86RxOl5AkkPzIDf9vj6xw2WfT/JbZpZxoxrp2CrKNS4586RyrIp4uANpvDyb5WER6mPLBX+uM=; lfrc_=a326a676-84a8-4d10-95ef-639cee67f4cf; Hm_lvt_9152f8221cb6243a53c83b956842be8a=1690536987; _jzqb=1.4.10.1690536948.1; Hm_lpvt_9152f8221cb6243a53c83b956842be8a=1690536991; _ga_KJTRWRHDL1=GS1.2.1690536950.1.1.1690536995.0.0.0; _ga_QJN1VP0CMS=GS1.2.1690536950.1.1.1690536995.0.0.0");
        return hashMap;
    }
}
