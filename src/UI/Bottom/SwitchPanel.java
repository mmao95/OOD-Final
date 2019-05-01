package UI.Bottom;

import UI.MainFrame;
import course.Course;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * @Auther: Di Zhu
 * @Date: 05-01-2019 12:26
 * @Description:
 */
public class SwitchPanel extends JPanel {

    private JPanel cards;

    private MainFrame mainFrame;
    private Course course;
    private JComboBox categoryBox;
    private JLabel instruction;
    private final static String none = "None";
    private final static String adding = "Adding";
    private final static String removing = "Removing";
    private final static String statistics = "Statistics";
    private String[] categories = new String[]{none, adding, removing, statistics};

    public SwitchPanel() {
        super();
    }

    public SwitchPanel(MainFrame mainFrame, Course course) {
        super();
        this.mainFrame = mainFrame;
        this.course = course;
        initialization();
    }

    private void initialization() {
        this.setLayout(new FlowLayout());
        this.add(Box.createHorizontalStrut(5));
        this.add(new JSeparator(SwingConstants.VERTICAL));
        this.add(Box.createHorizontalStrut(5));

        instruction = new JLabel("Select operations:");

        JPanel controller = new JPanel(new BorderLayout());
        categoryBox = new JComboBox<>(categories);
        categoryBox.setSelectedIndex(0);
        categoryBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, (String) e.getItem());
            }
        });

        controller.add(instruction, BorderLayout.NORTH);
        controller.add(categoryBox, BorderLayout.SOUTH);
        controller.setPreferredSize(new Dimension(128, 54));

        cards = new JPanel();
        cards.setLayout(new CardLayout());
        cards.add(new JPanel(), none);
        cards.add(new AddingPanel(mainFrame, course), adding);
        cards.add(new RemovePanel(mainFrame, course), removing);
        cards.add(new StatisticsPanel(course), statistics);

        this.add(controller);
        this.add(cards);
    }

    public void refreshPanel(Course newCourse) {
        this.course = newCourse;
        cards.removeAll();
        cards.revalidate();
        cards.repaint();

        cards.add(new JPanel(), none);
        cards.add(new AddingPanel(mainFrame, course), adding);
        cards.add(new RemovePanel(mainFrame, course), removing);
        cards.add(new StatisticsPanel(course), statistics);
    }

}
