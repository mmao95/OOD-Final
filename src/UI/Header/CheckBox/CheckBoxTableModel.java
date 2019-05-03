package UI.Header.CheckBox;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.swing.table.DefaultTableModel;

/**
 * @Auther: Di Zhu
 * @Date: 05-02-2019 11:54
 * @Description:
 */
public class CheckBoxTableModel extends DefaultTableModel {

    public CheckBoxTableModel() {
        super();
    }

    public CheckBoxTableModel(int row, int column) {
        super(row, column);
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        if (getColumnCount() == 7)
            return String.class;
        else if (columnIndex <= 3 || columnIndex > getColumnCount() - 4)
            return String.class;
        else
            return Boolean.class;
    }

}
