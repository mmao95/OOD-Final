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
    protected JLabel meanString, maxString, minString, stdString, medianString;
    protected JPanel meanPanel, maxPanel, minPanel, stdPanel, medianPanel, buttonPanel, nullPanel;

    public Statistics(Course inputCourse) {
        // TODO Auto-generated constructor stub

        closeButton = new JButton("Close");
        closeButton.addActionListener(this);

        String [] pointScore = inputCourse.getAnalysis();
        String initMeanString = "Mean: " + pointScore[0];
        String initMaxString = "Max Score: " + pointScore[1];
        String initMinString = "Min Score: " + pointScore[2];
        String initStdString = "Std: " + pointScore[3];
        String initMedianString = "Median: " + pointScore[4];

        meanString = new JLabel(initMeanString);
        maxString = new JLabel(initMaxString);
        minString = new JLabel(initMinString);
        stdString = new JLabel(initStdString);
        medianString = new JLabel(initMedianString);

        meanPanel = new JPanel();
        maxPanel = new JPanel();
        minPanel = new JPanel();
        stdPanel = new JPanel();
        medianPanel = new JPanel();
        buttonPanel = new JPanel();
        nullPanel = new JPanel();

        meanPanel.add(meanString);
        maxPanel.add(maxString);
        minPanel.add(minString);
        stdPanel.add(stdString);
        medianPanel.add(medianString);
        buttonPanel.add(closeButton);

        nullPanel.setBounds(0, 0, 300, 10);
        meanPanel.setBounds(25, 25, 250, 25);
        maxPanel.setBounds(25, 50, 250, 25);
        minPanel.setBounds(25, 75, 250, 25);
        stdPanel.setBounds(25, 100, 250, 25);
        medianPanel.setBounds(25, 125, 250, 25);
        buttonPanel.setBounds(100, 160, 100, 30);

        this.add(buttonPanel);
        this.add(medianPanel);
        this.add(stdPanel);
        this.add(minPanel);
        this.add(maxPanel);
        this.add(meanPanel);
        this.add(nullPanel);

        this.setTitle("Statistics");
//        this.setLayout(new GridLayout(3, 1));
        this.setSize(300, 230);
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
