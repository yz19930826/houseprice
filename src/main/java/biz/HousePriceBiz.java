package biz;


import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class HousePriceBiz {

    private final DataNotifier dataNotifier;

    private final SoldHouseDetailFetcher detailFetcher;

    private final SoldHouseDataListFetcher listFetcher;

    private final HouseOperator houseOperator;

    private final LianJiaWebLoginContext webLoginContext;

    private final AppFollowedQueryLoginContext appFollowedQueryLoginContext;


    public void acquireHouseData(String communityCode) {
        // 清空收藏夹
        clearFollowedHouse(appFollowedQueryLoginContext);

        //查询小区的所有成交房源
        List<String> soldHouseCodeList = listFetcher.fetch(communityCode, webLoginContext);

        if (soldHouseCodeList.isEmpty()){
            return;
        }

        // 关注房源 并获取房源详情 分批20次
//        partition();
    }

    private void clearFollowedHouse(AppFollowedQueryLoginContext context) {
        while (true) {
            List<FollowedHouseDataEntity> entities =
                    houseOperator.queryFollowedHouse(context);
            if (entities.isEmpty()) {
                return;
            }
            entities.forEach(entity -> houseOperator.unFollow(entity.getHouseCode(), context));
        }
    }
}
