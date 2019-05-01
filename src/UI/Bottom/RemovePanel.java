package UI.Bottom;

import UI.MainFrame;
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
    //private JComboBox categoryBox;
    private JButton removeStudent;
    //private JButton removeSubCategory;
    //String[] Category = new String[]{"Assignment", "Exam", "Project"};

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

            mainFrame.update(course);
        });

//        removeSubCategory = new JButton("Remove column");
//        removeSubCategory.addActionListener(e-> {
//            mainFrame.update(course);
//        });

        /*** Add components to main panel ***/

        this.add(removeStudent, BorderLayout.CENTER);
    }
}
