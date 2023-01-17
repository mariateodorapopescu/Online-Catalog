import java.util.ArrayList;

public class BestPartialScore implements Strategy{
    ArrayList<Grade> grades;
    public BestPartialScore(Course c) {
        grades = new ArrayList<>(c.getAllStudentGrades().values());
    }

    public Student getBestStudent(){
        grades.sort(new SortingGradesP());
        return grades.get(0).getStudent();
    }
}
