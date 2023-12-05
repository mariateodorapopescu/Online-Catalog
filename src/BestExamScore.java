import java.util.ArrayList;

public class BestExamScore implements Strategy {
    ArrayList<Grade> grades;
    public BestExamScore(Course c) {
        grades = new ArrayList<>(c.getAllStudentGrades().values());
    }
        public Student getBestStudent(){
        Student stud = null;
            grades.sort(new SortingGradesE());
            for (Grade s : grades)
            {
                stud = s.getStudent();
            }
            return stud;
            //return grades.get(grades.size()-1).getStudent();
        }
}
