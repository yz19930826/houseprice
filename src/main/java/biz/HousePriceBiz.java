package biz;


import lombok.Builder;
import utils.PartitionUtil;

import java.util.List;

@Builder
public class HousePriceBiz {

    private DataNotifier dataNotifier;

    private SoldHouseDetailFetcher detailFetcher;

    private SoldHouseDataListFetcher listFetcher;

    private LianJiaAppOperator lianJiaAppOperator;

    private LianJiaWebOperator lianJiaWebOperator;

    private LianJiaWebLoginContext webLoginContext;

    private AppFollowedQueryLoginContext appFollowedQueryLoginContext;

    private HouseDataDao houseDataDao;

    public void acquireHouseData(String communityCode) {
        // 清空收藏夹
        clearFollowedHouse();

        //查询小区的所有成交房源
        List<String> soldHouseCodeList = listFetcher.fetch(communityCode, webLoginContext);

        if (soldHouseCodeList.isEmpty()){
            return;
        }

        // 获取房源信息
        for (String houseCode : soldHouseCodeList) {
            HouseData fetch = detailFetcher.fetch(houseCode, webLoginContext);
            // 持久化房源信息
            houseDataDao.saveHouseData(fetch);
        }

        // 分20个关注房源
        List<List<String>> partition = PartitionUtil.partition(soldHouseCodeList, 20);
        for (List<String> houseCodeTwenty : partition) {
            for (String houseCode : houseCodeTwenty) {
                lianJiaWebOperator.follow(houseCode,webLoginContext);
            }
            // 获取关注的房源信息
            List<FollowedHouseDataEntity> followedHouseList = lianJiaAppOperator.queryFollowedHouse(appFollowedQueryLoginContext);
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
                houseDataEntity.setSoldPrice(entity.getSoldPrice());
                houseDataEntity.setSoldUnitPrice(entity.getSoldPriceUnit());
                houseDataDao.updateHouseData(houseDataEntity);
            }
        }

    }

    private void clearFollowedHouse() {
        while (true) {
            List<FollowedHouseDataEntity> entities =
                    lianJiaAppOperator.queryFollowedHouse(appFollowedQueryLoginContext);
            if (entities.isEmpty()) {
                return;
            }
            entities.forEach(entity -> lianJiaWebOperator.unFollow(entity.getHouseCode(), webLoginContext));
        }
    }
}
