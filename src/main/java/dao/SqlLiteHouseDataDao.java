package dao;

import biz.HouseDataDao;
import biz.HouseData;
import lombok.extern.slf4j.Slf4j;
import utils.DateUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * 在sqllite中持久化房源信息
 */
@Slf4j
public class SqlLiteHouseDataDao implements HouseDataDao {


    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    // 连接SQLLITE
    public Connection getConnection() {
        Connection connection = null;
        try {
            // 加载SQLite JDBC驱动程序
            Class.forName("org.sqlite.JDBC");
            // 建立数据库连接
            String projectPath = System.getProperty("user.dir");
            String fileSeparator = System.getProperty("file.separator");
            String sqlLiteDbFilePath = "jdbc:sqlite:" + projectPath + fileSeparator +"housedata.sqlite";

            log.info("get filepath of sqlite :{}" ,sqlLiteDbFilePath);
            connection = DriverManager.getConnection(sqlLiteDbFilePath);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
        return connection;
    }

    @Override
    public boolean saveHouseData(HouseData entity) {

        String sql = String.format("insert into t_house_data_new (house_code, listing_price, listing_unit_price, listing_date, title, sold_date,\n" +
                "                              orientation, floor_state, home_pic, community_id, community_name,  city_id,\n" +
                "                              house_layout, area, inside_area, sold_price, sold_unit_price, created_time, modify_time,\n" +
                "                              house_age_after_last_transaction)\n" +
                "values ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');", entity.getHouseCode(),entity.getListingPrice(),entity.getListingUnitPrice(),entity.getListingDate(),entity.getTitle(),entity. getSoldDate(),
                entity.getOrientation(),entity.getFloor(),entity.getPic(),entity.getCommunityId(),entity.getCommunityName(),entity.getCityId(),
                entity.getLayout(),entity.getArea(),entity.getInsideArea(),entity.getSoldPrice(),entity.getSoldUnitPrice(), DateUtil.format(LocalDateTime.now()),DateUtil.format(LocalDateTime.now()),
                        entity.getHouseYears());

        PreparedStatement preparedStatement = null;
        try(Connection connection = getConnection()) {
            preparedStatement = connection.prepareStatement(sql);
            int i = preparedStatement.executeUpdate();
            return i == 1;
        } catch (SQLException throwables) {
            log.error("saveHouseData error:{}",throwables);
            return false;
        }finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean updateHouseData(HouseData entity) {
        return true;
    }
}
