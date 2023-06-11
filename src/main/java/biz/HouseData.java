package biz;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class HouseData {
    private String title;
    private String houseCode;
    private String from;

    private String soldPrice;
    private String soldUnitPrice;
    private String soldDays;
    private String soldDate;

    private String listingPrice;
    private String listingUnitPrice;
    private String listingDate;

    private String pic;
    private String floor;
    private String orientation;
    private String communityId;
    private String communityName;
    private String houseYears;
    private String layout;
    private String insideArea;
    private String area;
    private String cityId;
}
