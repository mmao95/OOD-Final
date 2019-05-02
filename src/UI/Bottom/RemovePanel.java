package UI.Bottom;

import UI.MainFrame;
import UI.MainFrame;
import course.Course;
import personal.Student;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Di Zhu
 * @Date: 04-30-2019 10:26
 * @Description: Panel for removing rows and columns of JTable
 */
public class RemovePanel extends JPanel {

    private MainFrame mainFrame;
    private Course course;
    private JComboBox choiceComboBox;
    private JButton removeStudent;
    String[] choices = new String[]{"Selected Row", "Selected Column"};

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
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        choiceComboBox = new JComboBox(choices);
        choiceComboBox.setPreferredSize(new Dimension(128, 54));
        choiceComboBox.setSelectedIndex(-1);

        /*** Left Part ***/
        removeStudent = new JButton("Remove");
        removeStudent.addActionListener(e -> {
            JTable gradeTable = mainFrame.getGradeTable();

            if (choiceComboBox.getSelectedIndex() == 0) {
                

            } else if (choiceComboBox.getSelectedIndex() == 1) {
                List<Object> selections = mainFrame.getCheckBoxSelections();
                for (int i = 0 ; i < selections.size() ; i++) {
                    if (selections.get(i) != null) {
                        System.out.println("Delete this column!");
                    }
                }
            } else {

            }
            mainFrame.update(course);
        });
        removeStudent.setPreferredSize(new Dimension(96, 54));

        /*** Add components to main panel ***/

        this.add(removeStudent);
        this.add(choiceComboBox);
    }

    public void refreshPanel(Course newCourse) {
        this.course = course;
        choiceComboBox.setSelectedIndex(-1);
    }
}
