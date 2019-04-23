import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;

import UI.*;
import course.Course;
import course.Criterion;
import frame.AddCourse;
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
    private JTextField weightingField;
    //Right panel components
    private JLabel label;
    private JList detailGradeList;
    //Left panel components
    //JTree
    private JTree jTree;
    //Initialization of JTree Node
    private DefaultMutableTreeNode root = new DefaultMutableTreeNode("All courses");
    private DefaultTreeModel jMode = new DefaultTreeModel(root);

    private int windowWidth = 1280;
    private int windowHeight = 720;

    /*** Data ***/
    private Map<String, Map<String, Course>> courseMap = new HashMap<>();
    private Course currentCourse = null;
    private Class[] type = { String.class, String.class, String.class, String.class, String.class, String.class, String.class,
            String.class, String.class, String.class, String.class, String.class, String.class, double.class};

    public void initialization() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();

        container = getContentPane();
        container.setLayout(new BorderLayout());

        //Setting up navigation bar
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File (F)");
        fileMenu.setMnemonic('f');
        final JMenuItem saveChangesItem = new JMenuItem("Save changes");
        saveChangesItem.addActionListener(e-> {
//            SortTableModel stm = (SortTableModel) gradeTable.getModel();
//            Vector vector = stm.getDataVector();
//            for (int i =0 ; i < vector.size() ; i++) {
//                System.out.println(vector.get(i));
//            }
        });
        final JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(saveChangesItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        JMenu courseMenu = new JMenu("Course (C)");
        final JMenuItem deleteCourse = new JMenuItem("Delete Selected Course");
        deleteCourse.addActionListener(e-> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) jTree.getLastSelectedPathComponent();
            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();

            if (courseMap.containsKey(node.getUserObject())) {
                courseMap.remove(node.getUserObject());
            } else {
                courseMap.get(parent.getUserObject()).get(node.getUserObject());
            }

            DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
            model.removeNodeFromParent(node);
        });
        final JMenuItem newCourseMenuItem = new JMenuItem("New Course");
        newCourseMenuItem.addActionListener(e-> {
            //Course course = new Course();
            //addCourse(course, root);
            try {
                List<Criterion> criterionList = new ArrayList<>();
                new AddCourse(criterionList);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } catch (ClassNotFoundException cnfe) {
                cnfe.printStackTrace();
            }
        });
        final JMenuItem importCourseMenuItem = new JMenuItem("Import Course from File");
        importCourseMenuItem.addActionListener(e-> {
            Course course = NavigationBarMethods.importCourse("b.txt");
            addCourse(course, root);
        });
        courseMenu.add(newCourseMenuItem);
        courseMenu.add(importCourseMenuItem);
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
                if (node == null)
                    return;
                if (node.isLeaf()) {
                    try {
                        Object nodeInfo = node.getUserObject();
                        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
                        Course course = courseMap.get(parent.getUserObject()).get(nodeInfo);
                        setUpGradeTable(course.getCcriterion(), course.getList());
                    } catch (NullPointerException exception) {
                        exception.printStackTrace();
                    }
                }
            }
        });

        JScrollPane treeScrollPane = new JScrollPane(jTree);
        treeScrollPane.setPreferredSize(new Dimension(windowWidth / 8, windowHeight));
        container.add(treeScrollPane, BorderLayout.WEST);

        /*** Middle Panel ***/
        weightingField = new JTextField();
        //Set up grade table
        gradeTable = new JTable() {
            protected JTableHeader createDefaultTableHeader() {
                return new GroupableTableHeader(columnModel);
            }
        };

        //Set up table model
        SortTableModel sortTableModel = new SortTableModel();
        sortTableModel.setClassList(Arrays.asList(type));
       // sortTableModel.addTableModelListener(new TableChangedListener());

        //Set up Selection model
        ListSelectionModel lsm = gradeTable.getSelectionModel();
        lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsm.addListSelectionListener(new GradeTableSelectionHandler());

        //Add models to table and set up mouse listener
        gradeTable.setModel(sortTableModel);
        gradeTable.addMouseListener(new TableMouseListener(gradeTable, weightingField));
        gradeTable.setSelectionModel(lsm);

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
        middlePanel.setPreferredSize(new Dimension(windowWidth * (6 / 8), windowHeight));
        container.add(middlePanel, BorderLayout.CENTER);

        /*** Right Panel ***/
        rightPanel = new JPanel(new BorderLayout(5, 5));
        rightPanel.setPreferredSize(new Dimension(windowWidth / 8, windowHeight));

        label = new JLabel("Index");
        detailGradeList = new JList();

        rightPanel.add(label, BorderLayout.NORTH);
        rightPanel.add(detailGradeList, BorderLayout.CENTER);
        container.add(rightPanel, BorderLayout.EAST);

        /*** Bottom Panel ***/
        bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(windowWidth * (6 / 8), windowHeight / 8));
        container.add(bottomPanel, BorderLayout.SOUTH);

        setTitle("Grading System");
        setBounds((int)(screenSize.getWidth() - windowWidth)/ 2,
                (int)(screenSize.getHeight() - windowHeight)/ 2,
                windowWidth, windowHeight);
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
        TreePath semesterPath;

        HashMap<Student, Grade> gradeMap = course.getList();

        Map<String, Course> subCourseMap = new HashMap<>();
        if (!courseMap.containsKey(courseName)) {
            //Set up new table model
            setUpGradeTable(course.getCcriterion(), gradeMap);
            //Update Tree
            DefaultMutableTreeNode courseNode = new DefaultMutableTreeNode(courseName);
            DefaultMutableTreeNode semesterNode = new DefaultMutableTreeNode(courseSemester);
            courseNode.add(semesterNode);
            root.add(courseNode);
            //Update Map
            subCourseMap.put(courseSemester, course);
            courseMap.put(courseName, subCourseMap);
        } else if (courseMap.containsKey(courseName)) {
            if (courseMap.get(courseName).containsKey(courseSemester)) {
                System.out.println("Already exists!");
            } else {
                //Set up new table model
                setUpGradeTable(course.getCcriterion(), gradeMap);
                //Update Tree
                DefaultMutableTreeNode courseNode = (DefaultMutableTreeNode) coursePath.getLastPathComponent();
                DefaultMutableTreeNode semesterNode = new DefaultMutableTreeNode(courseSemester);
                courseNode.add(semesterNode);
                //Update Map
                subCourseMap = courseMap.get(courseName);
                subCourseMap.put(courseSemester, course);
                courseMap.put(courseName, subCourseMap);
            }
        }
        currentCourse = course;
        jTree.setModel(new DefaultTreeModel(root));
        //Expand the newest node
        semesterPath = find(root, courseSemester);
        jTree.setSelectionPath(semesterPath);
        jTree.scrollPathToVisible(semesterPath);
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

    /*** Table Set-ups ***/
    private void setUpGradeTable(Criterion criterion, HashMap<Student, Grade> grade) {
        Vector<String> headers = setUpTableHeader(criterion);
        Vector<Object> grades = loadData(grade);

        SortTableModel dm = (SortTableModel) gradeTable.getModel();
        dm.setDataVector(grades, headers);

        groupingHeaders(criterion);

        dm.addTableModelListener(new TableChangedListener());

        //gradeTable.getColumnModel().getColumn(10).setCellRenderer(new CommentRenderer());
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
        gradeTable.setTableHeader(header);
    }

    /*** Table Methods **/
    private void saveTable(String id, String category, int index, String value) {
        Student student = currentCourse.getStudent(id);
        Grade grade = currentCourse.getsGrade(student);

        switch (category) {
            case "Name":
                //student.setName();
                break;
            case "Id":
                student.setId(value);
                break;
            case "Email":
                student.setEmail(value);
                break;
            case "Assignment":
                grade.setAssignment(value, index);
                break;
            case "Exams":
                grade.setExam(value, index);
                break;
            case "Projects":
                grade.setProject(value, index);
                break;
            case "Attendance":
                grade.setAttendence(value);
                break;
            default:
                break;

        }
        currentCourse.getList().put(student, grade);
        currentCourse.writeToFile("b.txt");
    }

    /*** Table Listener ***/
    //Display weighting in the textfield
    private class GradeTableSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            label.setText(String.valueOf(gradeTable.getSelectedColumn()));
        }
    }

    private class TableChangedListener implements TableModelListener {
        @Override
        public void tableChanged(TableModelEvent e) {
            GroupableTableHeader header = (GroupableTableHeader) gradeTable.getTableHeader();
            int selectedColumn = gradeTable.getSelectedColumn();
            int selectedRow = gradeTable.getSelectedRow();
            if (selectedColumn == -1 || selectedRow == -1) {
                System.out.println("Nothing is selected!");
            } else {
                //!!Only useful when student id is in the second column!!
                String id = gradeTable.getValueAt(selectedRow, 1).toString();
                String value = gradeTable.getValueAt(selectedRow, selectedColumn).toString();
                TableColumn tc = gradeTable.getColumnModel().getColumn(selectedColumn);
                List<ColumnGroup> list = header.getColumnGroups(tc);
                String category;
                int index = -1;
                if (list.size() != 0) {
                    category = list.get(0).getHeaderValue();
                    index = Integer.parseInt(gradeTable.getColumnName(selectedColumn));
                } else {
                    category = gradeTable.getColumnName(selectedColumn);
                }
                System.out.println("Changed!");
                saveTable(id, category, index - 1, value);
            }
        }
    }
}
