import java.util.Comparator;

public class SortingGradesE implements Comparator {
    public int compare(Object o1, Object o2) {
        Grade a = (Grade) o1;
        Grade b = (Grade) o2;
        if (a.getExamScore() == b.getExamScore()) {
            return 0;
        } else {
            if (a.getExamScore() > b.getExamScore()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
