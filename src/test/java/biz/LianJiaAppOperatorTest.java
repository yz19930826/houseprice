package biz;

import assister.OkHttpApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LianJiaAppOperatorTest {

    private LianJiaAppOperator operator;

    private AppFollowedQueryLoginContext appFollowedQueryLoginContext;

    private OkHttpApi httpApi;


    @BeforeEach
    public void init(){
        appFollowedQueryLoginContext = new AppFollowedQueryLoginContext();
        httpApi = Mockito.mock(OkHttpApi.class);
        operator = new LianJiaAppOperator(httpApi);
    }

    @Test
    void queryFollowedHouseTest_when_given_null_then_throw_exception() {
        assertThrows(IllegalArgumentException.class, () -> operator.queryFollowedHouse(null));
    }


    @Test
    void queryFollowedHouseTest_when_given_loginContext_then_return_data() {
        Mockito.when(httpApi.get(Mockito.anyString(), Mockito.anyMap())).thenReturn("{\n" +
                "    \"request_id\": \"322fdb74-b6e3-4924-ab35-e21e7ca23deb\",\n" +
                "    \"uniqid\": \"010A1A0136EB12709E8801560D6550F5\",\n" +
                "    \"errno\": 0,\n" +
                "    \"error\": \"\",\n" +
                "    \"data\": {\n" +
                "        \"total_count\": 164,\n" +
                "        \"return_count\": 20,\n" +
                "        \"has_more_data\": 1,\n" +
                "        \"list\": [\n" +
                "            {\n" +
                "                \"city_id\": \"110000\",\n" +
                "                \"house_type\": \"ershoufang\",\n" +
                "                \"house_state\": \"yi_shou\",\n" +
                "                \"house_code\": \"101119072133\",\n" +
                "                \"cover_pic\": \"http://image1.ljcdn.com/hdic-frame/standard_dd92ed57-52f2-4da2-a5ec-960463829bd4.png.280x210.jpg\",\n" +
                "                \"bizcircle_name\": \"亦庄开发区其它\",\n" +
                "                \"community_id\": \"1111047382388\",\n" +
                "                \"community_name\": \"南海家园二里\",\n" +
                "                \"blueprint_hall_num\": 1,\n" +
                "                \"blueprint_bedroom_num\": 1,\n" +
                "                \"title\": \"南海家园二里 1室1厅 66.34㎡\",\n" +
                "                \"area\": 66.34,\n" +
                "                \"price\": 3780000,\n" +
                "                \"unit_price\": 56980,\n" +
                "                \"price_str\": \"378万成交\",\n" +
                "                \"unit_price_str\": \"56980元/平米\",\n" +
                "                \"orientation\": \"南 北\",\n" +
                "                \"m_url\": \"https://m.lianjia.com/bj/chengjiao/101119072133.html\",\n" +
                "                \"schema\": \"lianjia://ershou/detail?houseCode=101119072133&cityID=110000\",\n" +
                "                \"floor_state\": \"低楼层 共15层\",\n" +
                "                \"tags\": [\n" +
                "                    \"is_elevator\",\n" +
                "                    \"is_central_heating\",\n" +
                "                    \"is_five\",\n" +
                "                    \"new\"\n" +
                "                ],\n" +
                "                \"color_tags\": [\n" +
                "                    {\n" +
                "                        \"desc\": \"满五年\",\n" +
                "                        \"color\": \"ff8062\"\n" +
                "                    }\n" +
                "                ],\n" +
                "                \"is_focus\": false,\n" +
                "                \"new_price_str\": \"378万\",\n" +
                "                \"new_unit_price_str\": \"56980元/平米\",\n" +
                "                \"deal_time\": \"签约日期: 2023-05-03 20:05\",\n" +
                "                \"favorite_ctime\": \"2023-06-05 18:50:26\",\n" +
                "                \"splitHouseTitle\": {\n" +
                "                    \"communityName\": \"南海家园二里\",\n" +
                "                    \"appendInfo\": \"1室1厅 66.3m²\"\n" +
                "                },\n" +
                "                \"detailSchema\": \"lianjia://tradehistory/detail?housecode=101119072133&cityID=110000\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}");

        List<FollowedHouseDataEntity> followedHouseDataEntities =
                operator.queryFollowedHouse(appFollowedQueryLoginContext);

        assertEquals(followedHouseDataEntities.size(),1);

        assertEquals(followedHouseDataEntities.get(0).getCommunityName(),"南海家园二里");
    }


}