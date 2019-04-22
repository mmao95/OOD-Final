package frame;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @Auther: wangqitong
 * @Date: 04-12-2019 20:57
 * @Description:
 */
public class SubCategory extends JFrame{
    protected JButton okButton;
    protected JLabel weightString;
    protected JTable table;
    protected JScrollPane tableScrollPane;
    protected JPanel weightPanel, tablePanel, buttonPanel;
    protected TableColumn column;

    public SubCategory(){
        okButton = new JButton("OK");
        String[] tableHead = {"Assignment", "Score", "Weight"};
        Object[][] data = {
                {"Assignment", "Student Score", "Weight"},
                {"Assignment 1", new Double(91.3), new Double(0.6)},
                {"Assignment 2", new Double(97.5), new Double(0.2)},
                {"Assignment 3", new Double(94.1), new Double(0.2)}
        };
        table = new JTable(data, tableHead);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.setShowGrid(true);
        table.setGridColor(Color.gray);
        table.setRowHeight(50);// Setting the width of rol
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// Setting the width of column
        for (int i = 0; i < 3; i++) {
            column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth(200);
        }
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, center);

        weightString = new JLabel("Total = 0.6*a1 + 0.2*a2 + 0.2*a3");
        tableScrollPane = new JScrollPane(table);
//        add(tableScrollPane);
//        table.setFillsViewportHeight(true);

        buttonPanel = new JPanel();
        weightPanel = new JPanel();
        tablePanel = new JPanel();

        weightPanel.add(weightString);
        tablePanel.add(table);
        buttonPanel.add(okButton);

        weightPanel.setBounds(0, 20, 600, 50);
        tablePanel.setBounds(0, 50, 600, 250);
        buttonPanel.setBounds(250, 270, 100, 350);

        this.add(buttonPanel);
        this.add(tablePanel);
        this.add(weightPanel);

        this.setTitle("Sub-category");
//        this.setLayout(new GridLayout(3, 1));
        this.setSize(600, 350);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.setVisible(true);
        this.setResizable(false);
        }
}
