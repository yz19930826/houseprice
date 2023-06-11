package biz;


import lombok.RequiredArgsConstructor;
import utils.PartitionUtil;

import java.util.List;

@RequiredArgsConstructor
public class HousePriceBiz {

    private final DataNotifier dataNotifier;

    private final SoldHouseDetailFetcher detailFetcher;

    private final SoldHouseDataListFetcher listFetcher;

    private final HouseOperator houseOperator;

    private final LianJiaWebLoginContext webLoginContext;

    private final AppFollowedQueryLoginContext appFollowedQueryLoginContext;

    private HouseDataDao houseDataDao;

    public void acquireHouseData(String communityCode) {
        // 清空收藏夹
        clearFollowedHouse(appFollowedQueryLoginContext);

        //查询小区的所有成交房源
        List<String> soldHouseCodeList = listFetcher.fetch(communityCode, webLoginContext);

        if (soldHouseCodeList.isEmpty()){
            return;
        }

        // 获取房源信息
        for (String houseCode : soldHouseCodeList) {
            HouseData fetch = detailFetcher.fetch(houseCode);
            // 持久化房源信息
            houseDataDao.saveHouseData(fetch);
        }

        // 分20个关注房源
        List<List<String>> partition = PartitionUtil.partition(soldHouseCodeList, 20);
        for (List<String> houseCodeTwenty : partition) {
            for (String houseCode : houseCodeTwenty) {
                houseOperator.follow(houseCode,webLoginContext);
            }
            // 获取关注的房源信息
            List<FollowedHouseDataEntity> followedHouseList = houseOperator.queryFollowedHouse(appFollowedQueryLoginContext);
            // 更新房源信息
            for (FollowedHouseDataEntity entity : followedHouseList) {

                //    private String houseCode;
                //    private String cityId;
                //    private String communityId;
                //    private String communityName;
                //    private Date soldDate;
                //    private Integer soldPrice;
                //    private Integer soldPriceUnit;
                HouseData houseDataEntity = new HouseData();
                houseDataEntity.setHouseCode(entity.getHouseCode());
                houseDataEntity.setCityId(entity.getCityId());
                houseDataEntity.setCommunityId(entity.getCommunityId());
                houseDataEntity.setCommunityName(entity.getCommunityName());
                houseDataEntity.setSoldDate(entity.getSoldDate());
//                houseDataDao.updateHouseData();
            }
        }

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
