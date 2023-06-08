package biz;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.OKHttpUtil;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class LianJiaWebSoldHouseDataListFetcher implements SoldHouseDataListFetcher {


    private static final String SOLD_LIST_API = "https://bj.lianjia.com/chengjiao/pg%sc%s/";

    @Override
    public List<String> fetch(String communityId,LoginContext context) {

        List<String> resultList = new ArrayList<>();
        int index = 1;

        while(true){
            String fullApi = String.format(SOLD_LIST_API, index++, communityId);
            String content = OKHttpUtil.get(fullApi, Headers.of(context.headers()));

            // 解析content
            Document document = Jsoup.parse(content);
            Elements listContent = document.getElementsByClass("listContent").get(0).children();
            if(listContent.isEmpty()){
                log.info("遍历完成，共{}页", index-1);
                break;
            }
            for (Element element : listContent) {
                String houseCode = element.getElementsByClass("LOGCLICKDATA").get(0).attr("data-lj_action_house_code");
                resultList.add(houseCode);
            }
        }
        return resultList;
    }
}
