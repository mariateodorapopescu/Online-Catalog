public class Parent extends User implements Observer {
    private Catalog catalog; // asta ca sa fie pentru subscribe
    public Parent(String firstName, String lastName) {
        super(firstName, lastName);
    }
    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }
    @Override
    public void update(Notification notification) {
        if (notification.getGrade() == null) {
            System.out.println("No new notification");
        } else {
            if ((notification.getGrade().getStudent().getMother().toString().compareTo(toString()) == 0) ||
                    (notification.getGrade().getStudent().getFather().toString().compareTo(toString()) == 0))
            // daca chiar s-a pus nota copilului parintelui aluia
            {
                System.out.println("Your child has been graded! => " + notification.toString());
            }
            else
            {
                System.out.println("No new notification");
            }
        }
    }
    public void setObservator(Catalog catalog) {
        catalog.addObserver(this);
    }
}
