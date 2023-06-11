package biz;

import java.util.List;

public interface DataNotifier {
    void send(List<HouseData> houseDataList);
}
