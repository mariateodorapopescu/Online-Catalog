import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.*;

public abstract class Course implements Comparable {
    private String course;
    private Teacher teacher;
    private TreeSet<Assistant> assistants = new TreeSet<>(); // colectie fara duplicate
    private TreeSet<Grade> grades = new TreeSet<>(); // colectie odonata
    private HashMap<String, Group> groups = new HashMap<>(); // asta e aia cu grupa si id grupa

    public String getCourse() {
        return course;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public TreeSet<Assistant> getAssistants() {
        return assistants;
    }
    public TreeSet<Grade> getGrades() {
        return grades;
    }
    public HashMap<String, Group> getGroups() {
        return groups;
    }

    //builder start
    public static abstract class CourseBuilder {
        private String course;
        private Teacher teacher;
        private TreeSet<Assistant> assistants = new TreeSet<>(); // colectie fara duplicate
        private TreeSet<Grade> grades = new TreeSet<>(); // colectie odonata
        private HashMap<String, Group> groups = new HashMap<>(); // asta e aia cu grupa si id grupa
        public CourseBuilder(String course, Teacher teacher, TreeSet<Assistant> assistants,
                             TreeSet<Grade> grades, HashMap<String, Group> groups) {
            this.course = course;
            this.teacher = teacher;
            this.assistants = assistants;
            this.grades = grades;
            this.groups = groups;
        }
        public void setCourse(String course) {
            this.course = course;
        }
        public void setTeacher(Teacher teacher) {
            this.teacher = teacher;
        }
        public void setAssistants(TreeSet assistants) {
            this.assistants = assistants;
        }
        public void setGrades(TreeSet<Grade> grades) {
            this.grades = grades;
        }
        public void setGroups(HashMap<String, Group> groups) {
            this.groups = groups;
        }
        public abstract Course build();
    }
//builder end
    protected Course(final CourseBuilder builder) {
        this.course = builder.course;
        this.teacher = builder.teacher;
        this.assistants = builder.assistants;
        this.grades = builder.grades;
        this.groups = builder.groups;
    }
    public void addAssistant(String ID, Assistant assistant) {
        if (!assistants.contains(assistant)) {
            assistants.add(assistant);
        }
    }
    public void addStudent(String ID, Student student) {
        for (Group g : groups.values())
        {
            if (!g.contains(student)&&g.getID().compareTo(ID) == 0)
            {
                g.add(student);
            }
        }
    }
    public void addGroup(Group group) {
        if (!groups.values().contains(group)) {
            groups.put(group.getID(), group);
        }
    }
    public void addGroup(String ID, Assistant assistant) {
        Group g = new Group(ID, assistant);
        if (!groups.containsKey(ID) || !groups.containsKey(g))
        {
            groups.put(ID, g);
        }
    }
    public void addGroup(String ID, Assistant assist, Comparator<Student> comp) {
        Group g = new Group(ID, assist, comp);
        if (!groups.values().contains(g)) {
            groups.values().add(g);
        }
    }
    public Grade getGrade(Student student) {
        Grade gd = new Grade(0.0,0.0," ",null);
        for (Grade i : grades) {
            if (i.getStudent().toString().compareTo(student.toString()) == 0)
            {
                gd.setCourse(course);
                gd.setPartialScore(i.getPartialScore());
                gd.setExamScore(i.getExamScore());
                gd.setStudent(student);
            }
        }
        return gd;
    }
    public void addGrade(Grade grade) {
        if (!grades.contains(grade)) {
            grades.add(grade);
        }
    }
    public ArrayList<Student> getAllStudents() {
        ArrayList<Student> studs = new ArrayList<>();
        Collection <Group> g = new HashSet<>();
        g = groups.values();
        for (Group i : g) {
            for (Student j : i)
            {
                studs.add(j);
            }
        }
        return studs;
    }
    public HashMap<Student, Grade> getAllStudentGrades() {
        HashMap<Student, Grade> ceva = new HashMap<>();
        ArrayList<Student> cv = getAllStudents();
        for (Student stud : cv) {
            ceva.put(stud, getGrade(stud));
        }
        return ceva;
    }

    public abstract ArrayList<Student> getGratuatedStudents();
public Student getBestStudent(Strategy strategy){
    return strategy.getBestStudent();
}
Snapshot newgrades = new Snapshot(grades);
public Snapshot getSnap() {
        return newgrades;
}
public void makeBackup()
{
    newgrades = new Snapshot(grades);
}
public void undo()
{
    grades = newgrades.getGrades();
}
private final class Snapshot{
    private TreeSet<Grade> grades; // colectie odonata
    public Snapshot (TreeSet<Grade> grades)
    {
        this.grades = grades;
    }
    public TreeSet<Grade> getGrades() {
        return grades;
    }
}
    public Snapshot getInnerInstance(TreeSet<Grade> grades)
    {
        Snapshot in = new Snapshot(grades);
        return in;
    }
    private Stack<Snapshot> history = new Stack<>();
    public void hitSave()
    {
        makeBackup();
            history.push(newgrades);
    }
    public void hitUndo()
    {
            newgrades = history.pop();
            undo();
    }
    public int compareTo(Object o) {
        Course course = (Course) o;
        return getCourse().compareTo(course.getCourse());
    }
}


