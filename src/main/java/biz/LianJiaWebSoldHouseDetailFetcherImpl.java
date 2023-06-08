package biz;

import okhttp3.Headers;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import utils.OKHttpUtil;

import java.math.BigDecimal;

/**
 *
 */
public class LianJiaWebSoldHouseDetailFetcherImpl implements SoldHouseDetailFetcher {

    private static final String SOLD_DETAIL_URL = "https://bj.lianjia.com/chengjiao/%s.html";

    private LianJiaWebLoginContext context;

    public LianJiaWebSoldHouseDetailFetcherImpl(LianJiaWebLoginContext context) {
        this.context = context;
    }

    @Override
    public HouseDataEntity fetch(String houseCode) {
        String fullApi = String.format(SOLD_DETAIL_URL, houseCode);
        String content = OKHttpUtil.get(fullApi, Headers.of(context.headers()));
        Document document = Jsoup.parse(content);

        HouseDataEntity entity = new HouseDataEntity();

        entity.setFrom(FromTypeEnum.LIAN_JIA.getCode());


        String title = document.getElementsByTag("title").get(0).text();
        entity.setTitle(title);

        entity.setHouseCode(houseCode);

        String soldDate =  getSoldDate(document);
        entity.setSoldDate(soldDate);

//        entity.setSellerPrice();
//        entity.setSellerUnitPrice();

        Elements soldLabelElements = document.getElementsByClass("info fr").get(0).getElementsByTag("label");
        BigDecimal listingPrice = new BigDecimal(soldLabelElements.get(0).text());
        entity.setListingPrice(listingPrice);

        Integer soldDays = Integer.valueOf(soldLabelElements.get(1).text());
        entity.setSoldDays(soldDays);

        Elements houseInfoElement = document.getElementsByClass("houseContentBox")
                .get(0)
                .getElementsByClass("base")
                .get(0)
                .getElementsByTag("label");

        String text = houseInfoElement.get(0).text();
        entity.setLayout(text);

        String floorState = houseInfoElement.get(1).text();
        entity.setFloor(floorState);

        entity.setArea(houseInfoElement.get(2).text());

//        entity.setOrientation();
//        entity.setCommunityId();
//        entity.setCommunityName();
//        entity.setHouseYears();

        return null;

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
