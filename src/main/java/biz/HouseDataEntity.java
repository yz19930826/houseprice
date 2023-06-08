package biz;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class HouseDataEntity {
    private String title;
    private String houseCode;
    private String from;
    private BigDecimal sellerPrice;
    private BigDecimal sellerUnitPrice;
    private BigDecimal listingPrice;
    private Integer soldDays;
    private String soldDate;
    private String floor;
    private String orientation;
    private String communityId;
    private String communityName;
    private String houseYears;
    private String layout;
    private String area;
}
