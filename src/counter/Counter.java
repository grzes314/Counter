
package counter;

import javax.swing.JFrame;

/**
 *
 * @author Grzegorz Los
 */
public class Counter extends JFrame
{
    public static void main(String[] args)
    {
        new Counter().setVisible(true);
    }
    
    public Counter()
    {
        super("Counter");
        setSize(800,600);
        add(new MainPanel());
    }
}
