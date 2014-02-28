
package counter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Grzegorz Los
 */
public class Counter extends JFrame
{
    PlanIO io = new PlanIO();
    MainPanel mainPanel = new MainPanel();
    JFileChooser fileChooser;
    
    public static void main(String[] args)
    {
        new Counter().setVisible(true);
    }
    
    public Counter()
    {
        super("Counter");
        setSize(800,600);
        add(mainPanel);
        setJMenuBar(createMenuBar());
        makeFileChooser();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private JMenuBar createMenuBar()
    {
        JMenuBar jmb = new JMenuBar();
        JMenu menu = new JMenu("File");
        menu.add(makeLoadItem());
        menu.add(makeSaveItem());
        menu.add(makeClearItem());
        menu.add(makeQuitItem());
        jmb.add(menu);
        return jmb;
    }

    private JMenuItem makeLoadItem()
    {
        JMenuItem item = new JMenuItem("Load");
        item.addActionListener(new ActionListener() { 
            @Override public void actionPerformed(ActionEvent ae) {
                loadClicked();
            }
        });
        return item;
    }
    private JMenuItem makeSaveItem()
    {
        JMenuItem item = new JMenuItem("Save");
        item.addActionListener(new ActionListener() { 
            @Override public void actionPerformed(ActionEvent ae) {
                saveClicked();
            }
        });
        return item;
    }
    private JMenuItem makeClearItem()
    {
        JMenuItem item = new JMenuItem("Clear");
        item.addActionListener(new ActionListener() { 
            @Override public void actionPerformed(ActionEvent ae) {
                mainPanel.clear();
            }
        });
        return item;
    }
    private JMenuItem makeQuitItem()
    {
        JMenuItem item = new JMenuItem("Quit");
        item.addActionListener(new ActionListener() { 
            @Override public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        });
        return item;
    }
    
    private void loadClicked()
    {
        try {
            int res = fileChooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION)
            {
                WorkoutPlan wp = io.readPlan(fileChooser.getSelectedFile());
                mainPanel.setPlan(wp);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void saveClicked()
    {
        int res = fileChooser.showSaveDialog(this);
        if (res == JFileChooser.APPROVE_OPTION)
        {
            try {
                WorkoutPlan wp = mainPanel.getPlan();
                File file = fileChooser.getSelectedFile();
                if (!file.getName().endsWith(".wop"))
                    file = new File(file.getPath() + ".wop");
                io.writePlan(file, wp);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void makeFileChooser()
    {
        fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override public boolean accept(File file) {
                return file.getName().endsWith(".wop");
            }
            @Override public String getDescription() {
                return "Workout plans (*.wop)";
            }
        });
    }
}
