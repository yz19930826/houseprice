package biz;

public interface SoldHouseDetailFetcher {
    HouseData fetch(String houseCode, LianJiaWebLoginContext webLoginContext);
}
