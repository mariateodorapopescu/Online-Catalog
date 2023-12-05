public class Assistant extends User implements Element, Comparable{
    public Assistant(String firstName, String lastName) {
        super(firstName, lastName);
    }
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    @Override
    public int compareTo(Object o) {
        Assistant st = (Assistant) o;
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
