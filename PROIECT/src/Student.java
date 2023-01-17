public class Student extends User implements Comparable {
    private Parent mother;
    private Parent father;

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public void setMother(Parent mother) {
        this.mother = mother;
    }

    public Parent getFather() {
        return father;
    }

    public Parent getMother() {
        return mother;
    }

    public void setFather(Parent father) {
        this.father = father;
    }

    @Override
    public int compareTo(Object o) {
        Student st = (Student) o;
        if (getFirstName().compareTo(st.getFirstName()) > 0) {
            return 1;
        } else {
            if (getFirstName().compareTo(st.getFirstName()) < 0) {
                return -1;
            } else {
                if (getLastName().compareTo(st.getLastName()) == 0) {
                    return 0;
                } else {
                    if (getLastName().compareTo(st.getLastName()) > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            }
        }
    }
}
