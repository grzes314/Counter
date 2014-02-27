
package counter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Grzegorz Los
 */
public class WorkoutPlan implements Serializable
{
    int series;
    double betweenSeriesTime;
    ArrayList<ExercisePlan> exercises;
}
