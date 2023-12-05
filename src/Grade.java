public class Grade implements Comparable, Cloneable {
    private Double partialScore, examScore;
    private Student student;
    private String course;
    public Grade(Double partialScore, Double examScore, String course, Student student){
        this.partialScore = partialScore;
        this.examScore = examScore;
        this.course = course;
        this.student = student;
        this.ok = 0;
    }
    public Grade()
    {
    }
    public Double getPartialScore() {
        return partialScore;
    }
    public Double getExamScore() {
        return examScore;
    }
    public Student getStudent() {
        return student;
    }
    public String getCourse() {
        return course;
    }

    public void setPartialScore(Double partialScore) {
        this.partialScore = partialScore;
    }
    public void setExamScore(Double examScore) {
        this.examScore = examScore;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public void setCourse(String course) {
        this.course = course;
    }
    public Double getTotal() {
        return (partialScore + examScore);
    }
    @Override
    public int compareTo(Object o) {
        Grade gd = (Grade) o;
        if (getTotal() == gd.getTotal()) {
            return 0;
        } else {
            if (getTotal() > gd.getTotal()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
    private int ok;

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
