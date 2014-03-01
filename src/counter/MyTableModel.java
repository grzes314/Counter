
package counter;

import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Grzegorz Los
 */
class MyTableModel implements TableModel
{
    int exercisesCount;
    ArrayList<ExercisePlan> exercises = new ArrayList<>();
    private final ArrayList<TableModelListener> tableModelListeners = new ArrayList<>();

    @Override
    public int getRowCount()
    {
        return exercisesCount;
    }

    @Override
    public int getColumnCount()
    {
        return 4;
    }

    @Override
    public String getColumnName(int i)
    {
        switch (i) {
            case 0:
                return "Exercise Nr";
            case 1:
                return "Repetitions";
            case 2:
                return "Rep. time (s)";
            case 3:
                return "Delay (s)";
            default:
                throw new RuntimeException("Internal error");
        }
    }

    @Override
    public Class<?> getColumnClass(int i)
    {
        if (i < 2) {
            return Integer.class;
        }
        else {
            return Double.class;
        }
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return col > 0;
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        switch (col) {
            case 0:
                return row + 1;
            case 1:
                if (row < exercisesCount) {
                    return exercises.get(row).repetitions;
                }
                else {
                    return null;
                }
            case 2:
                if (row < exercisesCount) {
                    return exercises.get(row).time;
                }
                else {
                    return null;
                }
            case 3:
                if (row < exercisesCount) {
                    return exercises.get(row).delay;
                }
                else {
                    return null;
                }
            default:
                throw new RuntimeException("Internal error");
        }
    }

    @Override
    public void setValueAt(Object o, int row, int col)
    {
        switch (col) {
            case 1:
                maybeSetRepetitions(o, row);
                break;
            case 2:
                maybeSetTime(o, row);
                break;
            case 3:
                maybeSetDelay(o, row);
                break;
            default:
                throw new RuntimeException("Internal error");
        }
    }

    private int getInt(Object o)
    {
        Integer n;
        try {
            n = (Integer) o;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Internal error");
        }
        return n;
    }

    private double getDouble(Object o)
    {
        Double d;
        try {
            d = (Double) o;
        } catch (ClassCastException ex) {
            throw new RuntimeException("Internal error");
        }
        return d;
    }

    private void maybeSetRepetitions(Object o, int row)
    {
        int n = getInt(o);
        if (n > 0) {
            exercises.get(row).repetitions = n;
            fireChange(new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS));
        }
    }

    private void maybeSetTime(Object o, int row)
    {
        double d = getDouble(o);
        if (d >= 0) {
            exercises.get(row).time = d;
            fireChange(new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS));
        }
    }

    private void maybeSetDelay(Object o, int row)
    {
        double d = getDouble(o);
        if (d >= 0) {
            exercises.get(row).delay = d;
            fireChange(new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS));
        }
    }

    public void addExercise()
    {
        int row = exercisesCount;
        exercisesCount++;
        exercises.add(new ExercisePlan(1, 0, 0));
        fireChange(new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }

    void addExercise(ExercisePlan ex)
    {
        int row = exercisesCount;
        exercisesCount++;
        exercises.add(new ExercisePlan(ex));
        fireChange(new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT));
    }

    public void removeExercise(int row)
    {
        exercises.remove(row);
        exercisesCount--;
        fireChange(new TableModelEvent(this, row, row, TableModelEvent.ALL_COLUMNS, TableModelEvent.DELETE));
    }

    void clear()
    {
        exercises.clear();
        exercisesCount = 0;
        fireChange(new TableModelEvent(this));
    }

    @Override
    public void addTableModelListener(TableModelListener l)
    {
        tableModelListeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l)
    {
        tableModelListeners.remove(l);
    }

    private void fireChange(TableModelEvent ev)
    {
        for (TableModelListener tml : tableModelListeners) {
            tml.tableChanged(ev);
        }
    }

}
