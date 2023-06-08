package biz;

import java.util.List;

public interface SoldHouseDataListFetcher {
    List<String> fetch(String communityId,LoginContext context);
}
