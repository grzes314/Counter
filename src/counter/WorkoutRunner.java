
package counter;

import counter.progress.ProgressInfo;
import counter.progress.ProgressInfo.Action;
import static counter.progress.ProgressInfo.Action.*;
import counter.progress.ProgressObservable;
import counter.progress.ProgressObservableSupport;
import counter.progress.ProgressObserver;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;

/**
 *
 * @author Grzegorz Los
 */
public class WorkoutRunner implements ProgressObservable
{
    private final SoundManager sManager;
    private WorkoutPlan wp;
    private ExercisePlan currEx;
    private Thread thread;
    private boolean paused = false;
    private int series = 1;
    private int exercise = 1;
    private int repetition = 1;
    private Action action = STARTING;
    private long begTime;
    private long endTime;
    private long pauseTime;
    private final ProgressObservableSupport pos = new ProgressObservableSupport();
    private Timer reporter;
    private boolean stopped;
    private boolean finished;
    
    public WorkoutRunner()
    {
        this(new DummySoundManager());
    }

    public WorkoutRunner(SoundManager sManager)
    {
        this.sManager = sManager;
    }
            
    public void runInNewThread(final WorkoutPlan wp)
    {
        if (thread != null)
            throw new RuntimeException("WorkoutRunner may be run only once.");
        this.wp = wp;
        thread = new Thread(new Runnable() {
            @Override public void run() {
                WorkoutRunner.this.run();
            }
        });
        initTimer();
        thread.start();
    }
    
    private void run()
    {
        initRun();
        while (!(stopped || finished))
        {
            step();
        }
        clean();
    }
    
    private void initRun()
    {
        if (wp.exercises.isEmpty()) {
            finished = true;
            return;
        }
        sManager.playSeriesStart();
        currEx = wp.exercises.get(0);
        action = EXERCISE_BREAK;
        sleep(currEx.delay);
        if (stopped)
            return;
        sManager.playExerciseStart();
    }
    
    private void step()
    {
        action = REPETITION;
        sleep(currEx.time);
        if (stopped || finished)
            return;
        sManager.playRepetitionEnd();
        if (isLastRepetition())
        {
            sManager.playExerciseEnd();
            repetition = 1;
            startNextExercise();
        }
        else repetition++;
    }
    
    private boolean isLastRepetition()
    {
        return repetition >= currEx.repetitions;
    }
    
    private void startNextExercise()
    {
        if (isLastExercise())
        {
            sManager.playSeriesEnd();
            exercise = 1;
            startNextSeries();
        }
        else exercise++;
        if (stopped || finished)
            return;
        currEx = wp.exercises.get(exercise-1);
        action = EXERCISE_BREAK;
        sleep(currEx.delay);
        sManager.playExerciseStart();
    }
    
    private boolean isLastExercise()
    {
        return exercise >= wp.exercises.size();
    }
    
    private void startNextSeries()
    {
        if (isLastSeries())
        {
            finished = true;
            return;
        }
        else series++;
        action = SERIES_BREAK;
        sleep(wp.betweenSeriesTime);
        if (stopped)
            return;
        sManager.playSeriesStart();
    }
    
    public boolean isLastSeries()
    {
        return series  == wp.series;
    }
    
    public boolean hasFinished()
    {
        return finished;
    }

    private void initTimer()
    {
        reporter = new Timer(500, new ActionListener() {
            @Override public void actionPerformed(ActionEvent ae) {
                WorkoutRunner.this.fireProgressInfo(new ProgressInfo(
                    series, exercise, repetition, action, getRemainingTime()));
            }
        });
        reporter.setInitialDelay(0);
        reporter.start();
    }
    
    private double getRemainingTime()
    {
        return (endTime - System.currentTimeMillis()) / 1000.0;
    }
        
    public synchronized void pause()
    {
        paused = true;
        reporter.stop();
        notify();
    }
    
    public synchronized void resume()
    {
        paused = false;
        reporter.restart();
        notify();
    }
    
    private synchronized void sleep(double seconds)
    {
        begTime = System.currentTimeMillis();
        endTime = begTime + (long)(1000 * seconds);
        long toWait = endTime - begTime;
        do {
            try {
                wait(Math.max(1, toWait));
            } catch (InterruptedException ex) {
                Logger.getLogger(WorkoutRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (paused)
            {
                pauseTime = System.currentTimeMillis();
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(WorkoutRunner.class.getName()).log(Level.SEVERE, null, ex);
                }
                long elapsed = pauseTime - begTime;
                toWait -= elapsed;
                begTime = System.currentTimeMillis();
                endTime = begTime + toWait;
            }
        } while (!stopped && System.currentTimeMillis() < endTime);
    }

    @Override
    public void addProgressObserver(ProgressObserver ob)
    {
        pos.addProgressObserver(ob);
    }

    @Override
    public void removeProgressObserver(ProgressObserver ob)
    {
        pos.removeProgressObserver(ob);
    }

    @Override
    public void fireProgressInfo(ProgressInfo info)
    {
        pos.fireProgressInfo(info);
    }

    public boolean isPaused()
    {
        return paused;
    }

    public synchronized void stop()
    {
        stopped = true;
        notify();
    }
    
    private void clean()
    {
        action = Action.FINISHED;
        reporter.setRepeats(false);
        //reporter.stop();
    }
}
