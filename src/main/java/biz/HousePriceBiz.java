package biz;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class HousePriceBiz {

    private DataNotifier dataNotifier;

    private SoldHouseDetailFetcher detailFetcher;

    private SoldHouseDataListFetcher listFetcher;


    public void acquireHouseData(String communityCode) {
        return ;
    }
}
