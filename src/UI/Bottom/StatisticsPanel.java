package UI.Bottom;

import course.Course;
import frame.Statistics;
import javafx.scene.control.Separator;

import javax.swing.*;
import java.awt.*;

/**
 * @Auther: Di Zhu
 * @Date: 04-23-2019 10:01
 * @Description:
 */
public class StatisticsPanel extends JPanel {

    private Course course;
    private JButton statBy;
    private JComboBox categoryBox;
    private JComboBox subCategoryBox;
    String[] Category = new String[]{"Total", "Assignment", "Exam", "Project"};

    public StatisticsPanel() {
        super();
        initialization();
    }

    public StatisticsPanel(Course course) {
        super();
        this.course = course;
        initialization();
    }

    private void initialization() {
        this.setLayout(new BorderLayout(4, 8));

        /*** Left part ***/
        JPanel westPanel = new JPanel(new BorderLayout());

        statBy = new JButton("Show statistics of");
        statBy.addActionListener(e-> {
            if (categoryBox.getSelectedIndex() == 0) {
                new Statistics(course);
            } else {
                String cate = Category[categoryBox.getSelectedIndex()].substring(0, 1).toLowerCase();
                new Statistics(course, cate, subCategoryBox.getSelectedIndex());
            }
        });


        /*** Right part ***/
        JPanel rightPanel = new JPanel(new BorderLayout(4, 8));
        categoryBox = new JComboBox<>(Category);
        subCategoryBox = new JComboBox();
        rightPanel.add(categoryBox, BorderLayout.NORTH);
        rightPanel.add(subCategoryBox, BorderLayout.SOUTH);

        this.add(statBy, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.EAST);

        /*** Listener for ComboBox ***/
        categoryBox.addActionListener(e-> {
            subCategoryBox.removeAllItems();
            if (categoryBox.getSelectedIndex() == 0) {
                subCategoryBox.setEnabled(false);
            } else {
                subCategoryBox.setEnabled(true);
                String categotyAbb = Category[categoryBox.getSelectedIndex()].substring(0, 1).toLowerCase();
                int numSubCategory;

                if (categotyAbb.equals("a"))
                    numSubCategory = course.getCcriterion().getNumberOfAssignments();
                else if (categotyAbb.equals("e"))
                    numSubCategory = course.getCcriterion().getNumberOfExams();
                else
                    numSubCategory = course.getCcriterion().getNumberOfProjects();

                for (int i = 0 ; i < numSubCategory ; i++) {
                    subCategoryBox.addItem(String.valueOf(i + 1));
                }
            }
        });
    }

    /*** Getter and Setter ***/
    public void setCourse(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }
}
