package dao;

import biz.HouseDataDao;
import biz.HouseData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.StringUtils;
import utils.DateUtil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

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
            String sqlLiteDbFilePath = "jdbc:sqlite:" + projectPath + fileSeparator + "housedata.sqlite";

            log.info("get filepath of sqlite :{}", sqlLiteDbFilePath);
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
                        "values ('%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s');", entity.getHouseCode(), entity.getListingPrice(), entity.getListingUnitPrice(), entity.getListingDate(), entity.getTitle(), entity.getSoldDate(),
                entity.getOrientation(), entity.getFloor(), entity.getPic(), entity.getCommunityId(), entity.getCommunityName(), entity.getCityId(),
                entity.getLayout(), entity.getArea(), entity.getInsideArea(), entity.getSoldPrice(), entity.getSoldUnitPrice(), DateUtil.format(LocalDateTime.now()), DateUtil.format(LocalDateTime.now()),
                entity.getHouseYears());

        PreparedStatement preparedStatement = null;
        try (Connection connection = getConnection()) {
            preparedStatement = connection.prepareStatement(sql);
            int i = preparedStatement.executeUpdate();
            return i == 1;
        } catch (SQLException throwables) {
            log.error("saveHouseData error:{}", throwables);
            return false;
        } finally {
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

        HouseDataEntity dataEntity = new HouseDataEntity();
        dataEntity.setTitle(entity.getTitle());
        dataEntity.setHouseCode(entity.getHouseCode());
        dataEntity.setFetchFrom(entity.getFetchFrom());
        dataEntity.setSoldPrice(entity.getSoldPrice());
        dataEntity.setSoldUnitPrice(entity.getSoldUnitPrice());
        dataEntity.setSoldDays(entity.getSoldDays());
        dataEntity.setSoldDate(entity.getSoldDate());
        dataEntity.setListingPrice(entity.getListingPrice());
        dataEntity.setListingUnitPrice(entity.getListingUnitPrice());
        dataEntity.setListingDate(entity.getListingDate());
        dataEntity.setHomePic(entity.getPic());
        dataEntity.setFloorState(entity.getFloor());
        dataEntity.setOrientation(entity.getOrientation());
        dataEntity.setCommunityId(entity.getCommunityId());
        dataEntity.setCommunityName(entity.getCommunityName());
        dataEntity.setHouseAgeAfterLastTransaction(entity.getHouseYears());
        dataEntity.setHouseLayout(entity.getLayout());
        dataEntity.setInsideArea(entity.getInsideArea());
        dataEntity.setArea(entity.getArea());
        dataEntity.setCityId(entity.getCityId());

        String updateSql = generateUpdateSql(dataEntity);

        PreparedStatement preparedStatement = null;
        try (Connection connection = getConnection()) {
            preparedStatement = connection.prepareStatement(updateSql);
            int i = preparedStatement.executeUpdate();
            return i == 1;
        } catch (SQLException throwables) {
            log.error("saveHouseData error:{}", throwables);
            return false;
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public String generateUpdateSql(HouseDataEntity dataEntity) {
        StringBuilder sb = new StringBuilder("update t_house_data_new set ");
        // 反射获取所有字段
        Map<String, Object> sqlParamMap = new HashMap<>();
        Field[] fields = HouseDataEntity.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            String name = field.getName();
            Object object = getValue(dataEntity, field);
            if (object == null) {
                continue;
            }
            sqlParamMap.put(camelToUnderscore(name), object);
        }
        if (sqlParamMap.isEmpty()){
            throw new IllegalArgumentException("dataEntity is empty");
        }
        sqlParamMap.forEach((s, o) -> sb.append(s).append(" = ").append("'").append(o).append("'").append(","));
        sb.deleteCharAt(sb.length() - 1);

        sb.append(" where house_code = '").append(dataEntity.getHouseCode()).append("';");
        return sb.toString();
    }

    private Object getValue(HouseDataEntity dataEntity, Field field) {
        try {
            return field.get(dataEntity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static String camelToUnderscore(String input) {
        StringBuilder result = new StringBuilder();
        if (input != null && input.length() > 0) {
            result.append(input.charAt(0));
            for (int i = 1; i < input.length(); i++) {
                char currentChar = input.charAt(i);
                if (Character.isUpperCase(currentChar)) {
                    result.append("_");
                    result.append(Character.toLowerCase(currentChar));
                } else {
                    result.append(currentChar);
                }
            }
        }
        return result.toString();
    }

}
