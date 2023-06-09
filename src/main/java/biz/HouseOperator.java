package biz;

import java.util.List;

public interface HouseOperator {

    default int follow(String code,LoginContext loginContext){
        throw new UnsupportedOperationException();
    }

    default int unFollow(String code,LoginContext loginContext){
        throw new UnsupportedOperationException();
    }

    default List<FollowedHouseDataEntity> queryFollowedHouse(LoginContext loginContext){
        throw new UnsupportedOperationException();
    }
}
