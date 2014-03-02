
package counter.progress;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Grzegorz Los
 */
public class ProgressObservableSupport
{
    private final List<ProgressObserver> observers = new LinkedList<>();
    
    public void addProgressObserver(ProgressObserver ob)
    {
        observers.add(ob);
    }

    public void removeProgressObserver(ProgressObserver ob)
    {
        observers.remove(ob);
    }

    public void fireProgressInfo(ProgressInfo info)
    {
        for (ProgressObserver ob: observers)
            ob.update(info);
    }

}
