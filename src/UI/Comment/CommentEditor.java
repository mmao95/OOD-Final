package UI.Comment;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

/**
 * @Auther: Di Zhu
 * @Date: 04-23-2019 23:28
 * @Description:
 */
public class CommentEditor extends CommentPanel implements TableCellEditor {

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return false;
    }

    @Override
    public Object getCellEditorValue() {
        return getValue();
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        setValue((String) value);
        return this;
    }

    @Override
    public void addCellEditorListener(CellEditorListener l) {

    }

    @Override
    public void cancelCellEditing() {

    }

    @Override
    public void removeCellEditorListener(CellEditorListener l) {

    }

    @Override
    public boolean isCellEditable(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        return false;
    }
}
