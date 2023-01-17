import java.util.ArrayList;
import java.util.Comparator;

public class BestTotalScore implements Strategy{
    ArrayList<Grade> grades;
    public BestTotalScore(Course c) {
        grades = new ArrayList<>(c.getAllStudentGrades().values());
    }
    public Student getBestStudent(){
       grades.sort(new SortingGradesT());
       return grades.get(grades.size()-1).getStudent();
    }
}
