package biz;

public interface HouseOperator {

    int follow(String code,LoginContext loginContext);

    int unFollow(String code,LoginContext loginContext);
}
