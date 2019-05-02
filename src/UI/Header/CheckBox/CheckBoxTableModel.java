package UI.Header.CheckBox;

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
        return Boolean.class;
    }

}
