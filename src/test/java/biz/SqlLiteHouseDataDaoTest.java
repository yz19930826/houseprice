package biz;

import dao.SqlLiteHouseDataDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SqlLiteHouseDataDaoTest {


    SqlLiteHouseDataDao dataDao ;

    @BeforeEach
    public void init(){
        dataDao = new SqlLiteHouseDataDao();
    }

    @Test
    void saveHouseData() {
        HouseData houseDataEntity = new HouseData();
        houseDataEntity.setTitle("1");
        houseDataEntity.setHouseCode("2");
        houseDataEntity.setFrom("3");
        houseDataEntity.setSoldPrice("4");
        houseDataEntity.setSoldUnitPrice("5");
        houseDataEntity.setSoldDays("6");
        houseDataEntity.setSoldDate("7");
        houseDataEntity.setListingPrice("8");
        houseDataEntity.setListingUnitPrice("9");
        houseDataEntity.setListingDate("10");
        houseDataEntity.setPic("11");
        houseDataEntity.setFloor("12");
        houseDataEntity.setOrientation("13");
        houseDataEntity.setCommunityId("14");
        houseDataEntity.setCommunityName("15");
        houseDataEntity.setHouseYears("16");
        houseDataEntity.setLayout("17");
        houseDataEntity.setInsideArea("18");
        houseDataEntity.setArea("19");
        houseDataEntity.setCityId("20");
        dataDao.saveHouseData(houseDataEntity);
    }

    @Test
    void updateHouseData() {
    }
}