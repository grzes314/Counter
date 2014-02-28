
package counter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Grzegorz Los
 */
public class WorkoutPlan implements Serializable
{
    public WorkoutPlan()
    {
        exercises = new ArrayList<>();
    }
    public int series;
    public double betweenSeriesTime;
    public ArrayList<ExercisePlan> exercises;
}
