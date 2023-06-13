package dao;

import biz.HouseData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqlLiteHouseDataDaoTest {

    SqlLiteHouseDataDao sqlLiteHouseDataDao;

    @BeforeEach
    public void init(){
        sqlLiteHouseDataDao = new SqlLiteHouseDataDao();
    }

    @Test
    void updateHouseDataTest_when_given_empty_object_then_throw_exception() {
        assertThrows(IllegalArgumentException.class, () -> {
            sqlLiteHouseDataDao.updateHouseData(new HouseData());
        });
    }

    @Test
    void generateUpdateSqlTest_when_given_empty_object_then_return_sql() {
        HouseDataEntity houseDataEntity = new HouseDataEntity();
        houseDataEntity.setHouseCode("123");
        houseDataEntity.setArea("89");
        String sql = sqlLiteHouseDataDao.generateUpdateSql(houseDataEntity);
        assertEquals("update t_house_data_new set area = '89',house_code = '123' where house_code = '123';", sql);
    }




}