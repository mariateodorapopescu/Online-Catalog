import java.util.Comparator;

public class SortingGradesT implements Comparator {
    public int compare(Object o1, Object o2) {
        Grade a = (Grade) o1;
        Grade b = (Grade) o2;
        if (a.getTotal() == b.getTotal()) {
            return 0;
        } else {
            if (a.getTotal() > b.getTotal()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
