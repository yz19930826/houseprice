package biz;

import assister.OkHttpApi;
import dao.SqlLiteHouseDataDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HousePriceBizTest {

    HousePriceBiz housePriceBiz;

    @BeforeEach
    public void init(){
        housePriceBiz = HousePriceBiz.builder()
                .houseDataDao(new SqlLiteHouseDataDao())
                .lianJiaWebOperator(new LianJiaWebOperator())
                .lianJiaAppOperator(new LianJiaAppOperator(new OkHttpApi()))
                .webLoginContext(new LianJiaWebLoginContext())
                .appFollowedQueryLoginContext(new AppFollowedQueryLoginContext())
                .detailFetcher(new LianJiaWebSoldHouseDetailFetcherImpl())
                .listFetcher(new LianJiaWebSoldHouseDataListFetcher())
                .build();


    }

    @Test
    void acquireHouseData() {
        List<String> communityIdList =  new ArrayList<String>();
        communityIdList.add("1111027381528");
        for (String id : communityIdList) {
            housePriceBiz.acquireHouseData(id);
        }
    }
}