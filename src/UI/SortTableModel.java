package UI;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Di Zhu
 * @Date: 04-16-2019 21:05
 * @Description:
 */
public class SortTableModel extends DefaultTableModel {

    private List<Class> classList = new ArrayList<>();

    public SortTableModel() {
        super();
    }

    public SortTableModel(Object[][] data, Object[] columnNames) {
        super(data, columnNames);
    }


    public void addOneClass(Class type) {
        this.classList.add(type);
    }


    public void addColumn(Object columnName, Class type) {
        this.addOneClass(type);
        super.addColumn(columnName);
    }

    @Override
    public Class getColumnClass(int columnIndex) {
        return this.classList.get(columnIndex);
    }

    /*** Setter and Getter ***/
    public List<Class> getClassList() {
        return classList;
    }

    public void setClassList(List<Class> classList) {
        this.classList = classList;
    }

}