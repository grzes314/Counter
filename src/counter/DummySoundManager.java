
package counter;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Grzegorz Los
 */
public class DummySoundManager implements SoundManager
{
    private Calendar time = new GregorianCalendar();
    
    private void updateTime()
    {
        time.setTimeInMillis(System.currentTimeMillis());
    }
    
    private String currentTime()
    {
        return time.get(Calendar.HOUR_OF_DAY) + ":" +
            time.get(Calendar.MINUTE) + ":" +
            time.get(Calendar.SECOND);
    }

    @Override
    public void playSeriesStart()
    {
        updateTime();
        System.out.println(currentTime() + " | Series Start");
    }

    @Override
    public void playExerciseStart()
    {
        updateTime();
        System.out.println(currentTime() + " | Exercise start");  
    }

    @Override
    public void playSeriesEnd()
    {
        updateTime();
        System.out.println(currentTime() + " | Series End"); 
    }

    @Override
    public void playExerciseEnd()
    {
        updateTime();
        System.out.println(currentTime() + " | Exercise end"); 
    }

    @Override
    public void playRepetitionEnd()
    {
        updateTime();
        System.out.println(currentTime() + " | Repetition end"); 
    }

}
