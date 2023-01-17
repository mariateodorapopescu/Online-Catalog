import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeSet;

public class Catalog implements Subject{
    private static Catalog obj = null; //singleton
    public static String name; // asa e clasa
    public TreeSet<Course> courses; // asa e clasa
    static ArrayList<Parent> parents; //observer
    private Grade grade; //observer
    private Catalog(String name, TreeSet<Course> courses) {
        this.name = name;
        this.courses = courses; // asa e clasa
        parents = new ArrayList<>(); //observer
    }
    public ArrayList<Parent> getParents() {
        return parents;
    }
    public TreeSet<Course> getCourses() {
        return courses; // I think I'll need this
    }

    public static Catalog getInstance(String name, TreeSet<Course> courses) {
        if (obj == null) // singleton
            obj = new Catalog(name, courses);
        return obj;
    }
    public void showCourses() {
        int i;
        for (Course course : courses) {
            System.out.print(course.getCourse() + " ");
        }
    }
    public Course addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course); // asa e clasa
        }
        return course;
    }
    public void removeCourse(Course course) {
        if (courses.contains(course)) {
            courses.remove(course); // asa e clasa
        }
    }
    public void addObserver(Observer observer) {
        parents.add((Parent)observer);
    }
    public void removeObserver(Observer observer) {
        parents.remove((Parent)observer);
    }
    public void notifyObservers(Grade grade) {
        for (Parent i : parents)
        {
            if (i.toString().compareTo(grade.getStudent().getMother().toString())==0 ||
                    i.toString().compareTo(grade.getStudent().getFather().toString())==0)
            {
                i.update(new Notification(grade));
            }
        }
    }
    public void getNotified(Notification notification)
    {
        notifyObservers(notification.getGrade());
    }
}
