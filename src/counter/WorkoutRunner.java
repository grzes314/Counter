
package counter;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Grzegorz Los
 */
public class WorkoutRunner
{
    private final SoundManager sManager;
    private WorkoutPlan currentWP;

    public WorkoutRunner()
    {
        sManager = new DummySoundManager();
    }

    public WorkoutRunner(SoundManager sManager)
    {
        this.sManager = sManager;
    }
    
    public void run(WorkoutPlan wp)
    {
        currentWP = wp;
        for (int i = 0; i < wp.series; ++i)
        {
            runSeries();
            sleep(wp.betweenSeriesTime);
        }
    }
    
    public void runInNewThread(final WorkoutPlan wp)
    {
        new Thread(new Runnable() {
            @Override public void run() {
                WorkoutRunner.this.run(wp);
            }
        }).start();
    }
    
    private void runSeries()
    {
        sManager.playSeriesStart();
        for (ExercisePlan ex: currentWP.exercises)
        {
            sleep(ex.delay);
            runExcercise(ex);
        }
        sManager.playSeriesEnd();
    }

    private void runExcercise(ExercisePlan ex)
    {
        sManager.playExerciseStart();
        for (int i = 0; i < ex.repetitions; ++i)
        {
            sleep(ex.time);
            sManager.playRepetitionEnd();
        }
        sManager.playExerciseEnd();
    }
    
    private synchronized void sleep(double seconds)
    {
        try {
            wait((long)(1000 * seconds));
        } catch (InterruptedException ex) {
            Logger.getLogger(WorkoutRunner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
