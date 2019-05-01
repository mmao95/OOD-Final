import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * @Auther: wangqitong
 * @Date: 04-12-2019 20:57
 * @Description:
 */
public class SubCategory extends JFrame implements ActionListener {
    protected JButton okButton;
    protected JLabel weightString;
    protected JTable table;
    protected JScrollPane tableScrollPane;
    protected JPanel weightPanel, tablePanel, buttonPanel;
    protected TableColumn column;
    private int itemNumber = 0;

    public SubCategory(int num){
        this.itemNumber = num;

        okButton = new JButton("OK");
        okButton.addActionListener(this);
        String[] tableHead = {"Assignment", "Weight"};
        Object[][] data = {
                {"Assignment", "Weight"},
                {"Assignment 1", new Double(0.6)},
                {"Assignment 2", new Double(0.2)},
                {"Assignment 3", new Double(0.2)}
        };
        table = new JTable(data, tableHead);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        table.setShowGrid(true);
        table.setGridColor(Color.gray);
        table.setRowHeight(50);// Setting the width of rol
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// Setting the width of column
        for (int i = 0; i < 2; i++) {
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

        weightPanel.setBounds(0, 20, 400, 50);
        tablePanel.setBounds(0, 50, 400, 250);
        buttonPanel.setBounds(150, 270, 100, 350);

        this.add(buttonPanel);
        this.add(tablePanel);
        this.add(weightPanel);

        this.setTitle("Sub-category");
//        this.setLayout(new GridLayout(3, 1));
        this.setSize(400, 350);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.setVisible(true);
        this.setResizable(false);
    }

    private boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        if(pattern.matcher(str).matches()){
            if(Double.parseDouble(str) >= 0.0 && Double.parseDouble(str) <= 1.0)
                return true;
            else
                return false;
        }
        return false;
    }

    private boolean judgeWeightNumber(){
        boolean returnValue = true;
        for (int i = 1; i < itemNumber; i++){
            if(!isDouble(table.getValueAt(i, 1).toString()))
                returnValue = false;
        }
        return returnValue;
    }

    private boolean judgeWeightSum(){
        double sum = 0.0;
        for (int i = 1; i < itemNumber; i++)
            sum += Double.parseDouble(table.getValueAt(i, 1).toString());
        if(sum == 1.0)
            return true;
        else
            return false;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand()=="OK") {
            if(judgeWeightNumber()) {
                if(judgeWeightSum())
                    System.out.println("233");
                else
                    JOptionPane.showMessageDialog(null, "The sum of item weight must be 1.0!", "Info", JOptionPane.WARNING_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null, "Item weight must be float ranging from 0 to 1!", "Info", JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
