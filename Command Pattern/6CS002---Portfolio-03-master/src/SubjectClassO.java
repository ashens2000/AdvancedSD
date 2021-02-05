import java.util.ArrayList;
import java.util.List;

public class SubjectClassO {
    private List<AbstractClassO> observers = new ArrayList<AbstractClassO>();

    public void attach(AbstractClassO observer){
        observers.add(observer);
    }

    public void observernotification(){
        for(AbstractClassO observer : observers){
            observer. displayFrame();
        }
    }
}
