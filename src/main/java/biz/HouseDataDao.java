package biz;

public interface HouseDataDao {
    boolean saveHouseData(HouseData entity);

    boolean updateHouseData(HouseData entity);

    boolean selectByHouseCode(String houseCode);
}
