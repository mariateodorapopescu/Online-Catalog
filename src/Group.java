import java.util.Comparator;
import java.util.TreeSet;

public class Group extends TreeSet<Student> {
    private String ID;
    private Assistant assistant;

    public Group(String ID, Assistant assistant) {
        super();
        this.ID = ID;
        this.assistant = assistant;
    }

    public Group(String ID, Assistant assistant, Comparator<Student> comp) {
        super(comp);
        this.ID = ID;
        this.assistant = assistant;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    public String getID() {
        return ID;
    }

    public Assistant getAssistant() {
        return assistant;
    }

}
