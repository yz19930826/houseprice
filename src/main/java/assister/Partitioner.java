package assister;

import java.util.ArrayList;
import java.util.List;

public class Partitioner {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");

        List<List<String>> partition = new Partitioner().partition(list, 2);
        System.out.println(partition);
    }
    public <T> List<List<T>> partition(List<T> list, int size) {
        List<List<T>> result = new ArrayList<>();
        int total = list.size();
        int count = total / size;
        if (total % size != 0) {
            count++;
        }
        for (int i = 0; i < count; i++) {
            int start = i * size;
            int end = (i + 1) * size;
            if (end > total) {
                end = total;
            }
            result.add(list.subList(start, end));
        }
        return result;
    }
}
