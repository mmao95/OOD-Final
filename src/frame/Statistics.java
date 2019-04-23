package frame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

import course.*;

/**
 * @Auther: wangqitong
 * @Date: 04-12-2019 21:50
 * @Description:
 */
public class Statistics extends JFrame implements ActionListener {
    protected JButton closeButton;
    protected JLabel meanString, maxString, minString, stdString, aboveMeanString, belowMeanString;
    protected JTable aboveMeanTable, belowMeanTable;
    protected JScrollPane aboveMeanTableScrollPane, belowMeanTableScrollPane;
    protected JPanel meanPanel, maxPanel, minPanel, stdPanel, aboveMeanStringPanel, aboveMeanTablePanel, belowMeanStringPanel, belowMeanTablePanel, buttonPanel, nullPanel;
    protected TableColumn aboveMeanTableColumn, belowMeanTableColumn;
    private String maxScore, minScore;
//    private Course courseInfo;

    String Acc_type = "";
    String weight_type = "";

    public Statistics(Course inputCourse) {
        // TODO Auto-generated constructor stub

        closeButton = new JButton("Close");
        closeButton.addActionListener(this);

        String [] pointScore = inputCourse.getAnalysis();
        String initMeanString = "Mean: " + pointScore[0] + "                                               ";
        String initMaxString = "Max Score: " + pointScore[1];
        String initMinString = "Min Score: " + pointScore[2];
        String initStdString = "Std: " + "5.2";

        String[] tableHead = {"Student Name"};
        Object[][] aboveMeanStuInfo = {
                {"Qitong Wang"},
                {"Di Zhu"},
                {"Maoxuan Zhu"},
                {"Zhizhou Qiu"}
        };
        aboveMeanTable = new JTable(aboveMeanStuInfo, tableHead){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        aboveMeanTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        aboveMeanTable.setShowGrid(true);
        aboveMeanTable.setGridColor(Color.gray);
        aboveMeanTable.setRowHeight(30);// 设置表格行宽
        aboveMeanTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// 以下设置表格列宽
        for (int i = 0; i < 1; i++) {
            aboveMeanTableColumn = aboveMeanTable.getColumnModel().getColumn(i);
            aboveMeanTableColumn.setPreferredWidth(250);
        }
        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        aboveMeanTable.setDefaultRenderer(Object.class, center);

//        String[] tableHead = {"Student Name"};
        Object[][] belowMeanStuInfo = {
                {"Andy"},
                {"Bob"},
                {"Cindy"},
                {"David"}
        };
        belowMeanTable = new JTable(belowMeanStuInfo, tableHead){
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        belowMeanTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        belowMeanTable.setShowGrid(true);
        belowMeanTable.setGridColor(Color.gray);
        belowMeanTable.setRowHeight(30);// 设置表格行宽
        belowMeanTable.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);// 以下设置表格列宽
        for (int i = 0; i < 1; i++) {
            belowMeanTableColumn = belowMeanTable.getColumnModel().getColumn(i);
            belowMeanTableColumn.setPreferredWidth(250);
        }
//        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
//        center.setHorizontalAlignment(JLabel.CENTER);
        belowMeanTable.setDefaultRenderer(Object.class, center);

        meanString = new JLabel(initMeanString);
        maxString = new JLabel(initMaxString);
        minString = new JLabel(initMinString);
        stdString = new JLabel(initStdString);
        aboveMeanString = new JLabel("3 Students above the mean                   ");
        belowMeanString = new JLabel("3 Students below the mean                   ");
        aboveMeanTableScrollPane = new JScrollPane(aboveMeanTable);
        belowMeanTableScrollPane = new JScrollPane(belowMeanTable);
//        add(tableScrollPane);
//        table.setFillsViewportHeight(true);

        meanPanel = new JPanel();
        maxPanel = new JPanel();
        minPanel = new JPanel();
        stdPanel = new JPanel();
        aboveMeanStringPanel = new JPanel();
        aboveMeanTablePanel = new JPanel();
        belowMeanStringPanel = new JPanel();
        belowMeanTablePanel = new JPanel();
        buttonPanel = new JPanel();
        nullPanel = new JPanel();

        meanPanel.add(meanString);
        maxPanel.add(maxString);
        minPanel.add(minString);
        stdPanel.add(stdString);
        aboveMeanStringPanel.add(aboveMeanString);
        aboveMeanTablePanel.add(aboveMeanTable);
        belowMeanStringPanel.add(belowMeanString);
        belowMeanTablePanel.add(belowMeanTable);
        buttonPanel.add(closeButton);

        nullPanel.setBounds(0, 0, 300, 10);
        meanPanel.setBounds(25, 25, 250, 25);
        maxPanel.setBounds(25, 50, 250, 25);
        minPanel.setBounds(25, 75, 250, 25);
        stdPanel.setBounds(25, 100, 250, 25);
        aboveMeanStringPanel.setBounds(25, 125, 250, 25);
        aboveMeanTablePanel.setBounds(25, 150, 250, 150);
        belowMeanStringPanel.setBounds(25, 325, 250, 25);
        belowMeanTablePanel.setBounds(25, 350, 250, 150);
        buttonPanel.setBounds(100, 500, 100, 50);

        this.add(buttonPanel);
        this.add(belowMeanTablePanel);
        this.add(belowMeanStringPanel);
        this.add(aboveMeanTablePanel);
        this.add(aboveMeanStringPanel);
        this.add(stdPanel);
        this.add(minPanel);
        this.add(maxPanel);
        this.add(meanPanel);
        this.add(nullPanel);

        this.setTitle("Statistics");
//        this.setLayout(new GridLayout(3, 1));
        this.setSize(300, 575);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand()=="Close") {
            this.dispose();
//            System.exit(0);
        }
    }
}
