import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreVisitor implements Visitor{
    HashMap<Teacher,Tuple> examScores = new HashMap<>();
    HashMap<Assistant,Tuple> partialScores = new HashMap<>();
    Catalog catalog;
    public void setCatalog(Catalog catalog)
    {
        this.catalog = catalog;
    }
    @Override
    public void visit(Assistant assistant) {
        Course course = null;
        Group group = null;
        for (Course crs : catalog.getCourses())
        {
            if (crs.getAssistants().contains(assistant))
            {
                course = crs;
                break;
            }
        }
        for (Course crs : catalog.getCourses())
        {
            for (Group g : crs.getGroups().values())
            {
                if (g.getAssistant().toString().compareTo(assistant.toString())==0)
                {
                    group = g;
                    break;
                }
            }
        }
        for (Student s : group)
        {
            Grade g = course.getGrade(s);
            Tuple tuple = new Tuple(g.getStudent().toString(),g.getCourse(),g.getPartialScore());
            partialScores.put(assistant,tuple);
            catalog.notifyObservers(g);
        }
    }
    @Override
    public void visit(Teacher teacher) {
        Course course = null;
        Group group = null;
        for (Course crs : catalog.getCourses())
        {
            if (crs.getTeacher().toString().compareTo(teacher.toString())==0)
            {
                course = crs;
                break;
            }
        }
        Grade g = new Grade(0.0,0.0,"",new Student("", ""));
        for (Map.Entry<Student,Grade> i : course.getAllStudentGrades().entrySet())
        {
            g.setExamScore(i.getValue().getExamScore());
            g.setPartialScore(i.getValue().getPartialScore());
            g.setCourse(i.getValue().getCourse());
            g.setStudent(i.getValue().getStudent());
            Tuple tuple = new
                    Tuple(i.getValue().getStudent().toString(),i.getValue().getCourse(),i.getValue().getTotal());
            examScores.put(teacher,tuple);
            if (g.getExamScore() != null)
            {
                catalog.notifyObservers(g);
            }
        }
    }
    private class Tuple {
        private String student;
        private String course;
        private double grade;
        public Tuple(String student, String course, double grade)
        {
            this.student = student;
            this.course = course;
            this.grade = grade;
        }
        private void setCourse(String course) {
            this.course = course;
        }
        private void setStudent(String student) {
            this.student = student;
        }
        public void setGrade(double grade) {
            this.grade = grade;
        }
        public double getGrade() {
            return grade;
        }
        private String getStudent() {
            return student;
        }
        private String getCourse() {
            return course;
        }
    }

}
