package counter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Grzegorz Los
 */
public class SimpleSoundManager implements SoundManager
{
    
    private boolean initialized;
    private Clip[] clips;
    private final Map<String, Integer> nameToNr = new HashMap<>();
    
    
    public void init() throws LineUnavailableException, UnsupportedAudioFileException, IOException
    {
        nameToNr.put("series_start", 0);
        nameToNr.put("series_end", 1);
        nameToNr.put("exercise_start", 2);
        nameToNr.put("exercise_end", 3);
        nameToNr.put("repetition_end", 4);
        clips = new Clip[nameToNr.size()];
        readClips();
        initialized = true;
    }

    private void readClips() throws LineUnavailableException, UnsupportedAudioFileException, IOException
    {
        for (Map.Entry<String, Integer> pair: nameToNr.entrySet())
            readClip(pair.getKey(), pair.getValue());
    }
    

    private void readClip(String event, int nr) throws LineUnavailableException, UnsupportedAudioFileException, IOException
    {
        clips[nr] = AudioSystem.getClip();
        InputStream stream = getClass().getResourceAsStream("/sounds/" + event + ".wav");
        AudioInputStream ais = AudioSystem.getAudioInputStream( stream );
        clips[nr].open(ais);
    }
    
    private void ensureInitialized()
    {
        if (!initialized)
            throw new RuntimeException("SimpleSoundManager not initialized!");
    }
    
    
    @Override
    public void playSeriesStart()
    {
        ensureInitialized();
        play("series_start");
    }

    @Override
    public void playExerciseStart()
    {
        ensureInitialized();
        play("exercise_start");
    }

    @Override
    public void playSeriesEnd()
    {
        ensureInitialized();
        play("series_end");
    }

    @Override
    public void playExerciseEnd()
    {
        ensureInitialized();
        play("exercise_end");
    }

    @Override
    public void playRepetitionEnd()
    {
        ensureInitialized();
        play("repetition_end");
    }

    private void play(String event)
    {
        int nr = nameToNr.get(event);
        clips[nr].setFramePosition(0);
        clips[nr].start();
    }

}
