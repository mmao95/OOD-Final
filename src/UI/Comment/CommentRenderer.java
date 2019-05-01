package UI.Comment;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

/**
 * @Auther: Di Zhu
 * @Date: 04-22-2019 17:39
 * @Description:
 */
public class CommentRenderer extends CommentPanel implements TableCellRenderer {

    public CommentRenderer() {
        super();
    }

    @Override public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setValue((String) value);
        return this;
    }
}
