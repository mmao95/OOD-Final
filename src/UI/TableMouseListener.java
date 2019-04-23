package UI;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.TableColumnModel;

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
        //Point point = event.getPoint();
       // int currentRow = table.rowAtPoint(point);
        //table.setRowSelectionInterval(currentRow, currentRow);
    }
}