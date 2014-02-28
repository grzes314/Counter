
package counter;

import java.io.Serializable;

/**
 *
 * @author Grzegorz Los
 */
public class ExercisePlan implements Serializable
{
    public int repetitions;
    public double time;
    public double delay;

    public ExercisePlan(int repetitions, double time, double delay)
    {
        this.repetitions = repetitions;
        this.time = time;
        this.delay = delay;
    }

    public ExercisePlan(ExercisePlan original)
    {
        this.repetitions = original.repetitions;
        this.time = original.time;
        this.delay = original.delay;
    }
}
