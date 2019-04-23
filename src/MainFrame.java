import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.*;

import UI.*;
import course.Course;
import course.Criterion;
import grade.Grade;
import personal.Student;

/**
 * @Auther: Di Zhu
 * @Date: 04-12-2019 12:19
 * @Description:
 */
public class MainFrame extends JFrame {

    //Containers
    private Container container;

    //Panels
    private JPanel leftPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    private JPanel rightPanel;

    /*** Components ***/
    private JMenuBar menuBar;
    private JTable gradeTable;
    private JTree jTree;
    private JTextField weightingField;
    //Right panel components
    private JLabel label;
    private JList detailGradeList;

    private int width = 1280;
    private int height = 720;

    //Sample Data

    public void initialization() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();

        container = getContentPane();
        container.setLayout(new BorderLayout());

        //Initialization of JTree Node
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("All courses");
        DefaultTreeModel jMode = new DefaultTreeModel(root);

        //Setting up navigation bar
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File (F)");
        fileMenu.setMnemonic('f');
        final JMenuItem newCourseMenuItem = new JMenuItem("New Course");
        newCourseMenuItem.addActionListener(e-> {
            //root.add(new DefaultMutableTreeNode("Test"));
        });
        final JMenuItem importCourseMenuItem = new JMenuItem("Import Course from File");
        importCourseMenuItem.addActionListener(e-> {
            Course course = NavigationBarMethods.importCourse("a.txt");
            addCourse(course, root);
        });
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(newCourseMenuItem);
        fileMenu.add(importCourseMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        JMenu courseMenu = new JMenu("Course (C)");
        final JMenuItem deleteCourse = new JMenuItem("Delete Selected Course");
        deleteCourse.addActionListener(e-> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
            DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
            model.removeNodeFromParent(node);
        });
        courseMenu.add(deleteCourse);

        JMenu studentMenu = new JMenu("Student (S)");
        JMenu gradeMenu = new JMenu("Grade (G)");
        JMenu settingsMenu = new JMenu("Settings (E)");
        menuBar.add(fileMenu);
        menuBar.add(courseMenu);
        menuBar.add(studentMenu);
        menuBar.add(gradeMenu);
        menuBar.add(settingsMenu);
        this.setJMenuBar(menuBar);

        /*** Left Panel ***/
        //Set up JTree
        jTree = new JTree(jMode);
        jTree.setVisibleRowCount(0);
        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                        jTree.getLastSelectedPathComponent();
            /* if nothing is selected */
                if (node == null) return;
            /* retrieve the node that was selected */
                if (node.isLeaf()) {
                    Object nodeInfo = node.getUserObject();
                    System.out.println(nodeInfo);
            }
            /* React to the node selection. */
            }
        });

        JScrollPane treeScrollPane = new JScrollPane(jTree);
        treeScrollPane.setPreferredSize(new Dimension(width / 8, height));
        container.add(treeScrollPane, BorderLayout.WEST);

        /*** Middle Panel ***/
        weightingField = new JTextField();
        //Set up grade table
        gradeTable = new JTable() {
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };

        Class[] types = { String.class, String.class, String.class, String.class, String.class,
                String.class, String.class, String.class, String.class, String.class, double.class};
        SortTableModel sortTableModel = new SortTableModel();
        sortTableModel.setClassList(Arrays.asList(types));
        gradeTable.setModel(sortTableModel);
        gradeTable.addMouseListener(new TableMouseListener(gradeTable, weightingField));

        //Pop up menu of table
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItemAdd = new JMenuItem("Add Comment");
        JMenuItem menuItemShow = new JMenuItem("Show Comment");
        JMenuItem menuItemRemove = new JMenuItem("Remove Comment");
        popupMenu.add(menuItemAdd);
        popupMenu.add(menuItemShow);
        popupMenu.add(menuItemRemove);
        gradeTable.setComponentPopupMenu(popupMenu);

        gradeTable.setFillsViewportHeight(true);
        gradeTable.setAutoCreateRowSorter(true);
        gradeTable.setRowSelectionAllowed(true);
        gradeTable.setColumnSelectionAllowed(true);

        JPanel middleSubPanel = new JPanel(new BorderLayout());
        middleSubPanel.add(weightingField, BorderLayout.NORTH);

        JScrollPane tableScrollPane = new JScrollPane(gradeTable);
        middleSubPanel.add(tableScrollPane, BorderLayout.CENTER);

        middlePanel = new JPanel(new BorderLayout());
        middlePanel.add(middleSubPanel, BorderLayout.CENTER);
        middlePanel.setPreferredSize(new Dimension(width * (5 / 8), height));
        container.add(middlePanel, BorderLayout.CENTER);

        /*** Right Panel ***/
        rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setPreferredSize(new Dimension(width / 8, height));

        label = new JLabel("Index");
        detailGradeList = new JList();

        rightPanel.add(label, BorderLayout.NORTH);
        rightPanel.add(detailGradeList, BorderLayout.CENTER);
        container.add(rightPanel, BorderLayout.EAST);

        /*** Bottom Panel ***/
        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(width * (6 / 8), height / 8));
        container.add(bottomPanel, BorderLayout.SOUTH);

        setTitle("Grading System");
        setBounds((int)(screenSize.getWidth() - width)/ 2,
                (int)(screenSize.getHeight() - height)/ 2,
                width, height);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //Constructor
    public MainFrame() {
        initialization();
    }

    /*** JTree Methods ***/
    private void addCourse(Course course, DefaultMutableTreeNode root) {
        String courseName = course.getInfo()[0];
        String courseSemester = course.getInfo()[2] + course.getInfo()[3];
        TreePath coursePath = find(root, courseName);
        TreePath semesterPath = find(root, courseSemester);
        DefaultMutableTreeNode courseNode = new DefaultMutableTreeNode(courseName);
        DefaultMutableTreeNode semesterNode = new DefaultMutableTreeNode(courseSemester);
        courseNode.add(semesterNode);
        root.add(courseNode);

        HashMap<Student, Grade> grade = course.getList();

        if (coursePath == null && semesterPath == null) {
            setUpGradeTable(course.getCcriterion(), grade);
        } else if (coursePath != null && semesterPath == null) {

        } else {
            System.out.println("All exists!");
            jTree.setSelectionPath(semesterPath);
            jTree.scrollPathToVisible(semesterPath);
        }
    }

    private TreePath find(DefaultMutableTreeNode root, String s) {
        @SuppressWarnings("unchecked")
        Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            if (node.toString().equalsIgnoreCase(s)) {
                return new TreePath(node.getPath());
            }
        }
        return null;
    }

    /*** Table Methods ***/
    private void setUpGradeTable(Criterion criterion, HashMap<Student, Grade> grade) {
        Vector<String> headers = setUpTableHeader(criterion);
        Vector<Object> grades = loadData(grade);

        SortTableModel dm = (SortTableModel) gradeTable.getModel();
        dm.setDataVector(grades, headers);

        groupingHeaders(criterion);

        gradeTable.setModel(dm);
        gradeTable = new JTable(dm);
    }

    private Vector<String> setUpTableHeader(Criterion criterion) {

        String[] basic = new String[]{"Name", "Id", "Email"};

        int assignments = criterion.getNumberOfAssignments();
        int exams = criterion.getNumberOfExams();
        int projects = criterion.getNumberOfProjects();

        Vector<String> vector = new Vector<>();
        vector.addAll(Arrays.asList(basic));

        //Assignment Headers
        for (int i = 0 ; i < assignments ; i++) {
            vector.add(String.valueOf(i + 1));

        }
        //Exam headers
        for (int i = 0 ; i < exams ; i++) {
            vector.add(String.valueOf(i + 1));
        }
        //Project headers
        for (int i = 0 ; i < projects ; i++) {
            vector.add(String.valueOf(i + 1));
        }

        vector.add("Attendance");
        vector.add("Total");
        return vector;
    }

    private Vector<Object> loadData(HashMap<Student, Grade> gradeMap) {
        Vector<Object> grades = new Vector<>();
        for (Student key : gradeMap.keySet()) {
            Vector<String> temp = new Vector<>();
            temp.add(key.getName().toString());
            temp.add(key.getId());
            temp.add(key.getEmail());
            Grade grade = gradeMap.get(key);
            for (int i = 0 ; i < grade.getaGrade().size() ; i++) {
                temp.add(grade.getAssignment(i).getScore());
            }
            for (int i = 0 ; i < grade.geteGrade().size() ; i++) {
                temp.add(grade.getExam(i).getScore());
            }
            for (int i = 0 ; i < grade.getpGrade().size() ; i++) {
                temp.add(grade.getProject(i).getScore());
            }
            temp.add(grade.getAttendence().getScore());
            temp.add(String.valueOf(grade.getTtscore()));
            grades.add(temp);
        }
        return grades;
    }

    private void groupingHeaders(Criterion criterion) {
        int assignments = criterion.getNumberOfAssignments();
        int exams = criterion.getNumberOfExams();
        int projects = criterion.getNumberOfProjects();

        TableColumnModel cm = gradeTable.getColumnModel();
        ColumnGroup g_assignment = new ColumnGroup("Assignment");
        ColumnGroup g_exam = new ColumnGroup("Exams");
        ColumnGroup g_project = new ColumnGroup("Projects");

        //Assignment Headers
        int startIndex = 3;
        for (int i = 0 ; i < assignments ; i++) {
            g_assignment.add(cm.getColumn(startIndex));
            startIndex++;
        }

        //Exam headers
        for (int i = 0 ; i < exams ; i++) {
            g_exam.add(cm.getColumn(startIndex));
            startIndex++;
        }

        //Project headers
        for (int i = 0 ; i < projects ; i++) {
            g_project.add(cm.getColumn(startIndex));
            startIndex++;
        }

        GroupableTableHeader header = (GroupableTableHeader) gradeTable.getTableHeader();
        header.addColumnGroup(g_assignment);
        header.addColumnGroup(g_exam);
        header.addColumnGroup(g_project);
    }

    private Object getValueOfSelectedCell() {
        int row = gradeTable.getSelectedRow();
        int col = gradeTable.getSelectedColumn();
        return gradeTable.getValueAt(row, col);
    }

    private void hideColumn(int column) {
        TableColumn tc = gradeTable.getTableHeader().getColumnModel().getColumn(column);
        tc.setMaxWidth(5);
        tc.setPreferredWidth(5);
        tc.setWidth(5);
        tc.setMinWidth(5);
        gradeTable.getTableHeader().getColumnModel().getColumn(column).setMaxWidth(5);
        gradeTable.getTableHeader().getColumnModel().getColumn(column).setMinWidth(5);
    }

    private void showColumn(int column, int width) {
        TableColumn tc = gradeTable.getColumnModel().getColumn(column);
        tc.setMaxWidth(width);
        tc.setPreferredWidth(width);
        tc.setWidth(width);
        tc.setMinWidth(width);
        gradeTable.getTableHeader().getColumnModel().getColumn(column).setMaxWidth(width);
        gradeTable.getTableHeader().getColumnModel().getColumn(column).setMinWidth(width);
    }

    //Display weighting in the textfield
    private class GradeTableSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            weightingField.setText(String.valueOf(gradeTable.getSelectedColumn()));
            label.setText(String.valueOf(gradeTable.getSelectedColumn()));
        }
    }

}
