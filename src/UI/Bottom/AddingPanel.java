package UI.Bottom;

import UI.MainFrame;
import course.Course;
import course.Criterion;
import personal.Student;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.nio.ByteOrder;

/**
 * @Auther: Di Zhu
 * @Date: 04-29-2019 20:44
 * @Description: Panel for adding rows and columns of JTable
 */
public class AddingPanel extends JPanel {

    private MainFrame mainFrame;
    private Course course;
    private JComboBox categoryBox;
    private JButton addStudent;
    private JButton addSubCategory;
    String[] Category = new String[]{"Assignment", "Exam", "Project"};

    public AddingPanel() {
        super();
        initialization();
    }

    public AddingPanel(MainFrame mainFrame, Course course) {
        super();
        this.mainFrame = mainFrame;
        this.course = course;
        initialization();
    }

    private void initialization() {
        this.setLayout(new BorderLayout(4, 8));

        /*** Left Part ***/
        addStudent = new JButton("Add student");
        addStudent.addActionListener(e -> {
            course.enrollStudent(new Student());
            mainFrame.update(course);
        });

        /*** Right Part ***/
        JPanel rightPanel = new JPanel(new BorderLayout());
        addSubCategory = new JButton("Add column");
        addSubCategory.addActionListener(e-> {
            String cate = Category[categoryBox.getSelectedIndex()].substring(0, 1).toLowerCase();
            if (cate.equals("a")) {
                course.getCcriterion().addAssignment();
            } else if (cate.equals("e")) {
                course.getCcriterion().addExam();
            } else if (cate.equals("p")) {
                course.getCcriterion().addProject();
            } else {

            }
            course.updateGrade();
            //System.out.println("APA: " + course.getCcriterion().getNumberOfAssignments());
            mainFrame.update(course);
        });
        categoryBox = new JComboBox<>(Category);
        rightPanel.add(categoryBox, BorderLayout.NORTH);
        rightPanel.add(addSubCategory, BorderLayout.SOUTH);

        /*** Add components to main panel ***/
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(addStudent, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);

        this.add(panel, BorderLayout.WEST);
        this.add(Box.createHorizontalStrut(8));
        this.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.EAST);

    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
