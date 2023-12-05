import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

public class FullCourse extends Course {
    public FullCourse(CourseBuilder builder) {
        super(builder);
    }
    public static class FullCourseBuilder extends CourseBuilder {
        public FullCourseBuilder(String course, Teacher teacher, TreeSet<Assistant> assistants, TreeSet<Grade> grades, HashMap<String, Group> groups) {
            super(course, teacher, assistants, grades, groups);
        }
        @Override
        public FullCourse build() {
            return new FullCourse(this);
        }
    }
    public ArrayList<Student> getGratuatedStudents() {
        ArrayList<Student> stud = new ArrayList<>();
        for (Grade gd : getAllStudentGrades().values())
        {
            if (gd.getPartialScore() >= 3 && gd.getExamScore() >= 2) {
                stud.add(gd.getStudent());
            }
        }
        return stud;
    }
}
