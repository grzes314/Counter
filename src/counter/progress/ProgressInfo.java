
package counter.progress;

/**
 *
 * @author Grzegorz Los
 */
public class ProgressInfo
{
    public final int series;
    public final int exercise;
    public final int repetition;
    public final double remainingTime;
    public final Action action;
    
    public enum Action {
        REPETITION, EXERCISE_BREAK, SERIES_BREAK, FINISHED
    }

    public ProgressInfo(int series, int exercise, int repetition, Action action, double remainingTime)
    {
        this.series = series;
        this.exercise = exercise;
        this.repetition = repetition;
        this.action = action;
        this.remainingTime = remainingTime;
    }
    
}
