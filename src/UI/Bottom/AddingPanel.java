package UI.Bottom;

import UI.MainFrame;
import course.Category;
import course.Course;
import personal.Student;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

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
    String[] categories = new String[]{};

    public AddingPanel() {
        super();
        initialization();
    }

    public AddingPanel(MainFrame mainFrame, Course course) {
        super();
        this.mainFrame = mainFrame;
        this.course = course;
        if (course != null) {
            setCategories(course.getCcriterion().getCategories());
        }
        initialization();
    }

    public void setCategories(List<Category> categoryList) {
        List<String> temp = new ArrayList<>();
        for (Category category : categoryList) {
            temp.add(category.getName());
        }
        categories = temp.toArray(categories);
    }

    private void initialization() {
        this.setLayout(new FlowLayout());

        /*** Left Part ***/
        addStudent = new JButton("Add student");
        addStudent.addActionListener(e -> {
            course.enrollStudent(new Student());
            mainFrame.update(course);
        });
        addStudent.setPreferredSize(new Dimension(128, 54));

        /*** Right Part ***/
        JPanel rightPanel = new JPanel(new BorderLayout());
        addSubCategory = new JButton("Add column");
        addSubCategory.addActionListener(e-> {
            String cate = categories[categoryBox.getSelectedIndex()];
            for (int i = 0 ; i < course.getCcriterion().getCategories().size() ; i++) {
                Category temp = course.getCcriterion().getCategories().get(i);
                if (temp.getName().equals(cate)) {
                    course.getCcriterion().addTask(i);
                }
            }
            course.updateGrade();
            mainFrame.update(course);
        });
        categoryBox = new JComboBox<>(categories);
        rightPanel.add(categoryBox, BorderLayout.NORTH);
        rightPanel.add(addSubCategory, BorderLayout.SOUTH);
        rightPanel.setPreferredSize(new Dimension(128, 54));

        /*** Add components to main panel ***/
        this.add(addStudent, BorderLayout.WEST);
        this.add(rightPanel, BorderLayout.CENTER);

    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void refreshPanel(Course newCourse) {
        setCourse(newCourse);
        setCategories(newCourse.getCcriterion().getCategories());
        categoryBox.removeAllItems();
        for (String str : categories) {
            categoryBox.addItem(str);
        }
    }
}
