package UI.Bottom;

import UI.MainFrame;
import course.Category;
import course.Course;
import frame.AddStudentFrame;
import personal.Name;
import personal.Student;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Di Zhu
 * @Date: 04-29-2019 20:44
 * @Description: Panel for adding rows and columns of JTable
 */
public class AddingPanel extends JPanel {

    private JPanel cards;
    private final static String none = "None";
    private final static String student = "Student";
    private final static String category = "Category";
    private final static String subcategory = "SubCategory";

    private MainFrame mainFrame;
    private Course course;
    //private JComboBox categoryBox;
    private JComboBox addingBox;
    private JButton addStudent;
    //private JButton addSubCategory;
    private JButton addCategory;
    private JPanel rightPanel;
    //String[] categories = new String[]{};
    String[] addings = new String[]{"", "Student", "Category", "SubCategory"};

    private String Id = "";

    public AddingPanel() {
        super();
        initialization();
    }

    public AddingPanel(MainFrame mainFrame, Course course) {
        super();
        this.mainFrame = mainFrame;
        this.course = course;
//        if (course != null) {
//            setCategories(course.getCcriterion().getCategories());
//        }
        initialization();
    }

//    public void setCategories(List<Category> categoryList) {
//        List<String> temp = new ArrayList<>();
//        for (Category category : categoryList) {
//            temp.add(category.getName());
//        }
//        categories = temp.toArray(categories);
//    }

    private void initialization() {
        this.setLayout(new FlowLayout());
        addingBox = new JComboBox(addings);
        addingBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, (String) e.getItem());
            }
        });

        JPanel controller = new JPanel(new BorderLayout());
        controller.setPreferredSize(new Dimension(128, 54));
        controller.add(addingBox, BorderLayout.CENTER);

        /*** Left Part ***/
        addStudent = new JButton("Add student");
        addStudent.addActionListener(e -> {
            if (course != null) {
                new AddStudentFrame(mainFrame, course, this);
            }
        });
        addStudent.setPreferredSize(new Dimension(128, 54));

        addCategory = new JButton("Add category");
        addCategory.addActionListener(e -> {
            if (course != null) {
                Category category = new Category();
                course.getCcriterion().getCategories().add(category);
                //course.updateGrade();
                course.addCategory();
                mainFrame.update(course);
            }
        });
        addCategory.setPreferredSize(new Dimension(128, 54));

        /*** Right Part ***/
//        rightPanel = new JPanel(new BorderLayout());
//        addSubCategory = new JButton("Add column");
//        addSubCategory.addActionListener(e-> {
//            if (course == null)
//                return;
//            String cate = categories[categoryBox.getSelectedIndex()];
//            for (int i = 0 ; i < course.getCcriterion().getCategories().size() ; i++) {
//                Category temp = course.getCcriterion().getCategories().get(i);
//                if (temp.getName().equals(cate)) {
//                    course.getCcriterion().addTask(i);
//                }
//            }
//            course.updateGrade();
//            mainFrame.update(course);
//        });
//
//        categoryBox = new JComboBox<>(categories);
//        rightPanel.add(categoryBox, BorderLayout.NORTH);
//        rightPanel.add(addSubCategory, BorderLayout.SOUTH);
//        rightPanel.setPreferredSize(new Dimension(128, 54));

        cards = new JPanel();
        cards.setLayout(new CardLayout());
        cards.add(new JPanel(), none);
        cards.add(addStudent, student);
        cards.add(addCategory, category);
        cards.add(new AddSubCategory(mainFrame, course), subcategory);

        /*** Add components to main panel ***/
        this.add(controller);
        this.add(cards);
        //this.add(addStudent);
        //this.add(rightPanel);
        //this.add(addCategory);

    }

    public void setCourse(Course course) {
        this.course = course;
    }

//    public void refreshPanel(Course newCourse) {
//        setCourse(newCourse);
//        setCategories(newCourse.getCcriterion().getCategories());
//        categoryBox.removeAllItems();
//        for (String str : categories) {
//            categoryBox.addItem(str);
//        }
//    }

    public void setId(String id) {
        this.Id = id;
    }
}
