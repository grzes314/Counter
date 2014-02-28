
package counter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Grzegorz Los
 */
public class WorkoutPlan implements Serializable
{
    private final long seriaVersionUID = 1000001L;
    public int series;
    public double betweenSeriesTime;
    public ArrayList<ExercisePlan> exercises;
    
    public WorkoutPlan()
    {
        exercises = new ArrayList<>();
    }
    
    public double getTotalTime()
    {
        return series * getSeriesTime() + (series - 1) * betweenSeriesTime;
    }
    
    public double getSeriesTime()
    {
        double sum = 0;
        for (ExercisePlan ex: exercises)
            sum += ex.getTotalTime();
        return sum;
    }
}
