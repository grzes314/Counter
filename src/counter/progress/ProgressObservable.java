
package counter.progress;

/**
 *
 * @author Grzegorz Los
 */
public interface ProgressObservable
{
    public void addProgressObserver(ProgressObserver ob);
    public void removeProgressObserver(ProgressObserver ob);
    public void fireProgressInfo(ProgressInfo info);
}
