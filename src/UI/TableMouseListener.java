package UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

/**
 * @Author Di Zhu
 * @Date 9:53 53/17/2019
 * @Description //TODO
 **/
public class TableMouseListener extends MouseAdapter {

    private JTable table;
    private JTextField weightingField;

    public TableMouseListener(JTable table, JTextField weightingField) {
        this.table = table;
        this.weightingField = weightingField;
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // selects the row at which point the mouse is clicked
        if (event.getClickCount() == 2) {
            weightingField.setText("Double Clicked!");
        } else if (event.getClickCount() == 1) {
            weightingField.setText(table.getColumnName(table.getSelectedColumn()));
        }
    }
}