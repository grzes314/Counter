package counter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Clip clip;
    AudioInputStream streams[];
    private final Map<String, Integer> nameToNr = new HashMap<>();
    
    
    public void init() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        nameToNr.put("series_start", 0);
        nameToNr.put("series_end", 1);
        nameToNr.put("exercise_start", 2);
        nameToNr.put("exercise_end", 3);
        nameToNr.put("repetition_end", 4);
        streams = new AudioInputStream[nameToNr.size()];
        clip = AudioSystem.getClip();
        readStreams();
        initialized = true;
    }

    private void readStreams() throws LineUnavailableException, UnsupportedAudioFileException, IOException
    {
        for (Map.Entry<String, Integer> pair: nameToNr.entrySet())
            readStream(pair.getKey(), pair.getValue());
    }
    

    private void readStream(String event, int nr) throws LineUnavailableException, UnsupportedAudioFileException, IOException
    {
        InputStream stream = getClass().getResourceAsStream("/sounds/" + event + ".wav");
        stream = new BufferedInputStream(stream);
        streams[nr] = AudioSystem.getAudioInputStream( stream );
        streams[nr].mark(1000000000);
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
        try {
            int nr = nameToNr.get(event);
            AudioInputStream stream = streams[nr];
            stream.reset();
            clip.close();
            clip.open(stream);
            clip.start();
        } catch (LineUnavailableException | IOException ex) {
            Logger.getLogger(SimpleSoundManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
