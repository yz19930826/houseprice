package dao;

import biz.HouseData;
import biz.HouseDataDao;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import utils.DateUtil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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


        HouseDataEntity convert = convert(entity);

        Map<String, Object> nonNullValueFromEntity = getNonNullValueFromEntity(convert);

        String sql = generateInsertSql(nonNullValueFromEntity);


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

    private String generateInsertSql(Map<String, Object> nonNullValueFromEntity) {
        if (nonNullValueFromEntity == null || nonNullValueFromEntity.isEmpty()){
            throw new IllegalArgumentException();
        }
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("insert into t_house_data_new (");
        StringBuilder valueBuilder = new StringBuilder();
        valueBuilder.append(" values (");
        for (Map.Entry<String, Object> entry : nonNullValueFromEntity.entrySet()) {
            sqlBuilder.append(entry.getKey()).append(",");
            valueBuilder.append("'").append(entry.getValue()).append("'").append(",");
        }
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
        valueBuilder.deleteCharAt(valueBuilder.length() - 1);
        sqlBuilder.append(")");
        valueBuilder.append(")");
        sqlBuilder.append(valueBuilder);
        return sqlBuilder.toString();
    }

    @Override
    public boolean updateHouseData(HouseData entity) {

        HouseDataEntity dataEntity = convert(entity);

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

    @NotNull
    private HouseDataEntity convert(HouseData houseData) {
        HouseDataEntity dataEntity = new HouseDataEntity();
        dataEntity.setTitle(houseData.getTitle());
        dataEntity.setHouseCode(houseData.getHouseCode());
        dataEntity.setFetchFrom(houseData.getFetchFrom());
        dataEntity.setSoldPrice(houseData.getSoldPrice());
        dataEntity.setSoldUnitPrice(houseData.getSoldUnitPrice());
        dataEntity.setSoldDays(houseData.getSoldDays());
        dataEntity.setSoldDate(houseData.getSoldDate());
        dataEntity.setListingPrice(houseData.getListingPrice());
        dataEntity.setListingUnitPrice(houseData.getListingUnitPrice());
        dataEntity.setListingDate(houseData.getListingDate());
        dataEntity.setHomePic(houseData.getPic());
        dataEntity.setFloorState(houseData.getFloor());
        dataEntity.setOrientation(houseData.getOrientation());
        dataEntity.setCommunityId(houseData.getCommunityId());
        dataEntity.setCommunityName(houseData.getCommunityName());
        dataEntity.setHouseAgeAfterLastTransaction(houseData.getHouseYears());
        dataEntity.setHouseLayout(houseData.getLayout());
        dataEntity.setInsideArea(houseData.getInsideArea());
        dataEntity.setArea(houseData.getArea());
        dataEntity.setCityId(houseData.getCityId());
        return dataEntity;
    }

    public String generateUpdateSql(HouseDataEntity dataEntity) {
        StringBuilder sb = new StringBuilder("update t_house_data_new set ");
        // 反射获取所有字段
        Map<String, Object> sqlParamMap = getNonNullValueFromEntity(dataEntity);
        if (sqlParamMap.isEmpty()){
            throw new IllegalArgumentException("dataEntity is empty");
        }
        sqlParamMap.forEach((s, o) -> sb.append(s).append(" = ").append("'").append(o).append("'").append(","));
        sb.deleteCharAt(sb.length() - 1);

        sb.append(" where house_code = '").append(dataEntity.getHouseCode()).append("';");
        return sb.toString();
    }

    @NotNull
    private Map<String, Object> getNonNullValueFromEntity(HouseDataEntity dataEntity) {
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
        return sqlParamMap;
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
