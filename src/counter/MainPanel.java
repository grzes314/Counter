/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package counter;

import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author grzes
 */
public class MainPanel extends javax.swing.JPanel
{

    /**
     * Creates new form MainPanel
     */
    public MainPanel()
    {
        initComponents();
    }
    
    public WorkoutPlan getPlan()
    {
        throw new RuntimeException("TODO!");
    }
    
    public void setPlan(WorkoutPlan wp)
    {
        throw new RuntimeException("TODO!");
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT
     * modify this code. The content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabel1 = new javax.swing.JLabel();
        series = new javax.swing.JSpinner();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        jLabel1.setText("Number of series: ");

        series.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));

        table.setModel(new MyTableModel());
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(series, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(series, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                .addGap(130, 130, 130))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner series;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}

class MyTableModel implements TableModel
{
    int exercises;
    ArrayList<Integer> repetitions = new ArrayList<>();
    private final ArrayList<TableModelListener> tableModelListeners = new ArrayList<>();
    
    @Override
    public int getRowCount()
    {
        return exercises + 1;
    }

    @Override
    public int getColumnCount()
    {
        return 2;
    }

    @Override
    public String getColumnName(int i)
    {
        switch(i)
        {
            case 0:
                return "Exercise";
            case 1:
                return "Repetitions";
            default:
                throw new RuntimeException("Internal error");
        }
    }

    @Override
    public Class<?> getColumnClass(int i)
    {
        return Integer.class;
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return col == 1;
    }

    @Override
    public Object getValueAt(int row, int col)
    {
        switch(col)
        {
            case 0:
                return row + 1;
            case 1:
                if (row < exercises) return repetitions.get(row);
                else return null;
            default:
                throw new RuntimeException("Internal error");
        }
    }

    @Override
    public void setValueAt(Object o, int row, int col)
    {
        if (col != 1)
                throw new RuntimeException("Internal error");
        Integer n;
        try {
            n = (Integer) o;
        } catch (ClassCastException ex) {
                throw new RuntimeException("Internal error");
        }
        maybeSetRepetitions(n, row);
    }
    
    private void maybeSetRepetitions(int n, int row)
    {
        if (n < 0)
            return;
        if (row == exercises) // next exercise
        {
            if (n > 0)
            {
                repetitions.add(n);
                exercises++;
                fireChange(new TableModelEvent(this, row, row, 1, TableModelEvent.INSERT));
            }
        }
        else if (n > 0)
        {
            repetitions.set(row, n);
            fireChange(new TableModelEvent(this, row, row, 1));
        }
        else
        {
            repetitions.remove(row);
            exercises--;
            fireChange(new TableModelEvent(this, row, row, 1, TableModelEvent.DELETE));
        }
        
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
        for (TableModelListener tml: tableModelListeners)
            tml.tableChanged(ev);
    }
}