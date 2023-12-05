import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class PartialCourse extends Course{
    public PartialCourse(CourseBuilder builder){
        super(builder);
    }
    public static class PartialCourseBuilder extends CourseBuilder{
        public PartialCourseBuilder(String course, Teacher teacher, TreeSet<Assistant> assistants, TreeSet<Grade> grades, HashMap<String, Group> groups) {
            super(course, teacher, assistants, grades, groups);
        }
        @Override
        public PartialCourse build() {
            return new PartialCourse(this);
        }
    }
    public ArrayList<Student> getGratuatedStudents() {
        ArrayList<Student> stud = new ArrayList<>();
        for (Grade gd : getAllStudentGrades().values())
        {
            if (gd.getTotal() >= 5) {
                stud.add(gd.getStudent());
            }
        }
        return stud;
    }
}
