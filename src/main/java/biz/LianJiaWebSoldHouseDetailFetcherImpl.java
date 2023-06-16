package biz;

import okhttp3.Headers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.OKHttpUtil;

import java.util.Objects;

/**
 *
 */
public class LianJiaWebSoldHouseDetailFetcherImpl implements SoldHouseDetailFetcher {

    private static final String SOLD_DETAIL_URL = "https://bj.lianjia.com/chengjiao/%s.html";

    @Override
    public HouseData fetch(String houseCode, LianJiaWebLoginContext webLoginContext) {
        String fullApi = String.format(SOLD_DETAIL_URL, houseCode);
        String content = OKHttpUtil.get(fullApi, Headers.of(webLoginContext.headers()));
        Document document = Jsoup.parse(content);

        HouseData entity = new HouseData();

        entity.setFetchFrom(FromTypeEnum.LIAN_JIA.getCode());


        String title = document.getElementsByTag("title").get(0).text();
        entity.setTitle(title);

        entity.setHouseCode(houseCode);

        String soldDate =  getSoldDate(document);
        entity.setSoldDate(soldDate);

//        entity.setSellerPrice();
//        entity.setSellerUnitPrice();

        Elements soldLabelElements = document.getElementsByClass("info fr").get(0).getElementsByTag("label");
        entity.setListingPrice(soldLabelElements.get(0).text());

        entity.setSoldDays(soldLabelElements.get(1).text());

        Elements houseInfoElement = document.getElementsByClass("houseContentBox")
                .get(0)
                .getElementsByClass("base")
                .get(0)
                .getElementsByTag("li");

        String text = Objects.requireNonNull(houseInfoElement.get(0).lastChild()).toString();
        entity.setLayout(text);

        String floorState = houseInfoElement.get(1).lastChild().toString();
        entity.setFloor(floorState);

        entity.setArea(houseInfoElement.get(2).lastChild().toString());

        entity.setOrientation(houseInfoElement.get(6).lastChild().toString());


        Elements transactionLi = document.getElementsByClass("transaction")
                .get(0).getElementsByTag("li");

        String listingDate = transactionLi.get(2).lastChild().toString();
        entity.setListingDate(listingDate);


        String houseYears = transactionLi.get(4).lastChild().toString();
        entity.setHouseYears(houseYears);

        entity.setFetchFrom(FromTypeEnum.LIAN_JIA.getCode());

        return entity;

    }

    private String getSoldDate(Document document) {
        String text = document.getElementsByClass("house-title LOGVIEWDATA LOGVIEW")
                .get(0)
                .getElementsByClass("wrapper")
                .get(0)
                .getElementsByTag("span")
                .get(0)
                .text();
        return text;
    }
    
}
