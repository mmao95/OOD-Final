package frame;

import UI.MainFrame;
import course.NewCriterion;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
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
    private NewCriterion newCriterion;
    private MainFrame mainFrame;
    private int itemNumber;

    public SubCategory(MainFrame inputMainFrame, NewCriterion inputNewCriterion, int index){
        this.mainFrame = inputMainFrame;
        this.newCriterion = inputNewCriterion;
        this.itemNumber = newCriterion.getCategories().get(index).getNumberOfTasks();

        okButton = new JButton("OK");
        okButton.addActionListener(this);
        String[] tableHead = {"Category", "Weight"};

        List<List<String>> data = new ArrayList<>();
        for (int i = 0; i < itemNumber; i++){
            List<String> tempList = new ArrayList<>();
            tempList.add(newCriterion.getCategories().get(index).getName() + " " + String.valueOf(i + 1));
            tempList.add(String.valueOf(newCriterion.getCategories().get(index).getCriComps().get(i).getWeights()));
            data.add(tempList);
        }

        Object[][] finalData = new Object[data.size() + 1][2];
        finalData[0][0] = newCriterion.getCategories().get(index).getName();
        finalData[0][1] = "Weight";
        for (int i = 1; i < itemNumber; i++){
            finalData[i][0] = data.get(i).get(0);
            finalData[i][1] = data.get(i).get(1);
        }

        table = new JTable(finalData, tableHead);
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

        weightString = new JLabel("");
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
        this.mainFrame.setEnabled(false);
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
        for (int i = 1; i <= itemNumber; i++){
            if(!isDouble(table.getValueAt(i, 1).toString()))
                returnValue = false;
        }
        return returnValue;
    }

    private boolean judgeWeightSum(){
        double sum = 0.0;
        for (int i = 1; i <= itemNumber; i++)
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
                {
                    this.mainFrame.updateCriterion(newCriterion);
                    this.mainFrame.setEnabled(true);
                    this.dispose();
                }
                else
                    JOptionPane.showMessageDialog(null, "The sum of item weight must be 1.0!", "Info", JOptionPane.WARNING_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(null, "Item weight must be float ranging from 0 to 1!", "Info", JOptionPane.WARNING_MESSAGE);
            }
        }

    }
}
