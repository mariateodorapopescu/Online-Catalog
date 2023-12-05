import java.util.Comparator;

public class Sortbyname implements Comparator<Course> {
    public int compare(Course a, Course b) {
        return a.getCourse().compareTo(b.getCourse());
    }
}
