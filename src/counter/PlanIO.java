
package counter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Grzegorz Los
 */
public class PlanIO
{
    public WorkoutPlan readPlan(File file) throws IOException
    {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            WorkoutPlan wp = (WorkoutPlan) in.readObject();
            in.close();
            fileIn.close();
            return wp;
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }
    
    public void writePlan(File file, WorkoutPlan wp) throws IOException
    {
        FileOutputStream fileOut = new FileOutputStream(file);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(wp);
        out.close();
        fileOut.close();
    }

}
