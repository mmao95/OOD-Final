package UI.Bottom;

import UI.MainFrame;
import course.Course;
import personal.Student;

import javax.swing.*;
import java.awt.*;

/**
 * @Auther: Di Zhu
 * @Date: 04-30-2019 10:26
 * @Description: Panel for removing rows and columns of JTable
 */
public class RemovePanel extends JPanel {

    private MainFrame mainFrame;
    private Course course;
    private JComboBox categoryBox;
    private JButton removeStudent;
    private JButton removeSubCategory;
    String[] Category = new String[]{"Assignment", "Exam", "Project"};

    public RemovePanel() {
        super();
        initialization();
    }

    public RemovePanel(MainFrame mainFrame, Course course) {
        super();
        this.mainFrame = mainFrame;
        this.course = course;
        initialization();
    }

    private void initialization() {
        this.setLayout(new BorderLayout(4, 8));

        /*** Left Part ***/
        removeStudent = new JButton("Remove student");
        removeStudent.addActionListener(e -> {
            course.enrollStudent(new Student());
            mainFrame.update(course);
        });

        /*** Right Part ***/
        JPanel rightPanel = new JPanel(new BorderLayout());
        removeSubCategory = new JButton("Remove column");
        removeSubCategory.addActionListener(e-> {
            String cate = Category[categoryBox.getSelectedIndex()].substring(0, 1).toLowerCase();
            if (cate.equals("a")) {
                course.getCcriterion().addAssignment();
            } else if (cate.equals("e")) {
                course.getCcriterion().addExam();
            } else if (cate.equals("p")) {
                course.getCcriterion().addProject();
            } else {

            }

            mainFrame.update(course);
        });
        categoryBox = new JComboBox<>(Category);
        rightPanel.add(categoryBox, BorderLayout.NORTH);
        rightPanel.add(removeSubCategory, BorderLayout.SOUTH);

        /*** Add components to main panel ***/
        //JPanel center = new JPanel();
        //center.add(removeStudent);
        //center.add(rightPanel);
        JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                removeStudent, rightPanel);
        pane.setDividerSize(1);
        //this.add(removeStudent, BorderLayout.WEST);
        //this.add(rightPanel, BorderLayout.EAST);
        this.add(pane, BorderLayout.CENTER);
    }
}
