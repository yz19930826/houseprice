package dao;


import lombok.Data;

@Data
public class HouseDataEntity {

    private String title;
    private String houseCode;
    private String fetchFrom;

    private String soldPrice;
    private String soldUnitPrice;
    private String soldDays;
    private String soldDate;

    private String listingPrice;
    private String listingUnitPrice;
    private String listingDate;

    private String homePic;
    private String floorState;
    private String orientation;
    private String communityId;
    private String communityName;
    private String houseAgeAfterLastTransaction;
    private String houseLayout;
    private String insideArea;
    private String area;
    private String cityId;
}
