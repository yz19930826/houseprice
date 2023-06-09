package biz;

import assister.HttpApi;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
public class LianJiaAppOperator implements HouseOperator{

    @NotNull
    private HttpApi httpApi;


    private static final String FOLLOWED_QUERY_FIRST_PAGE = "https://apps.api.lianjia.com/user/house/followedListV3?city_id=110000&house_type=ershoufang&limit_count=20&limit_offset=0&request_ts=1680710320";
    @Override
    public List<FollowedHouseDataEntity> queryFollowedHouse(LoginContext loginContext) {
        if (loginContext == null){
            throw new IllegalArgumentException();
        }

        String resultJson = httpApi.get(FOLLOWED_QUERY_FIRST_PAGE, loginContext.headers());

        JSONArray jsonArray = JSON.parseObject(resultJson).getJSONObject("data").getJSONArray("list");
        if (jsonArray.isEmpty()){
            return Collections.emptyList();
        }

        List<FollowedHouseDataEntity> resultList = new ArrayList<>();

        for (Object o : jsonArray) {

            JSONObject jsonObject = (JSONObject) o;

            FollowedHouseDataEntity entity = new FollowedHouseDataEntity();
            entity.setCityId(jsonObject.getString("city_id"));
            entity.setCommunityId(jsonObject.getString("community_id"));
            entity.setCommunityName(jsonObject.getString("community_name"));
            entity.setSoldPrice(jsonObject.getInteger("price"));
            entity.setSoldPriceUnit(jsonObject.getInteger("unit_price"));

            resultList.add(entity);
        }
        return resultList;
    }
}
