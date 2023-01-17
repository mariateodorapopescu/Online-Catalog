public class Notification {
    private Grade grade;
    public Notification(Grade grade)
    {
        this.grade = grade;
    }
    public void setGrade(Grade grade) {
        this.grade = grade;
    }
    public Grade getGrade() {
        return grade;
    }
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Nota studentului " + grade.getStudent().toString() + " este ");
        sb.append(grade.getTotal());
        sb.append(" la cursul ");
        sb.append(grade.getCourse());
        String s = new String(sb);
        return s;
    }
}