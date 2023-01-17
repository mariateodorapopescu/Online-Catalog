import java.util.Comparator;

public class SortingGradesP implements Comparator {
    public int compare(Object o1, Object o2) {
        Grade a = (Grade) o1;
        Grade b = (Grade) o2;
        if (a.getPartialScore() == b.getPartialScore()) {
            return 0;
        } else {
            if (a.getPartialScore() < b.getPartialScore()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
