public interface Subject {
    public void addObserver(Observer observer); // subscribe

    public void removeObserver(Observer observer); // unsubscribe

    public static void notifyObservers(Grade grade) // notify
    {
        Notification notification = new Notification(grade);
        for (Parent p : Catalog.parents)
        {
            p.update(notification);
        }
    }
}
