package biz;

import lombok.Data;

import java.util.Date;

@Data
public class FollowedHouseDataEntity {
    private String houseCode;
    private String cityId;
    private String communityId;
    private String communityName;
    private Date soldDate;
    private Integer soldPrice;
    private Integer soldPriceUnit;
}
