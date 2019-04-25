import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.*;
import java.util.List;

import UI.*;
import UI.Bottom.StatisticsPanel;
import UI.Comment.CommentEditor;
import UI.Comment.CommentRenderer;
import course.Course;
import course.Criterion;
import frame.AddCourse;
import frame.SubCategory;
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
    private JTextArea commentArea = new JTextArea(5, 10);
    //Left panel components
    //JTree
    private JTree jTree;
    //Initialization of JTree Node
    private DefaultMutableTreeNode root = new DefaultMutableTreeNode("All courses");
    private DefaultTreeModel jMode = new DefaultTreeModel(root);

    //Bottom panel components
    private StatisticsPanel statisticsPanel;

    private int windowWidth = 1280;
    private int windowHeight = 720;
    /*** Listener ***/
    private TableMouseListener tableMouseListener;

    /*** Data ***/
    private Map<String, Map<String, Course>> courseMap = new HashMap<>();
    private Course currentCourse = null;
    private Class[] type = {};

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
            JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir"));
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            jFileChooser.showDialog(this, "Select");

            Course course = NavigationBarMethods.importCourse(jFileChooser.getSelectedFile().getAbsolutePath());
            if (course != null)
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

        //Set up selection model
        ListSelectionModel lsm = gradeTable.getSelectionModel();
        lsm.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lsm.addListSelectionListener(new GradeTableSelectionHandler());

        //Set up column model
        TableColumnModel tcm = gradeTable.getColumnModel();
        tcm.getSelectionModel().addListSelectionListener(new GradeTableSelectionHandler());

        //Add models to table and set up mouse listener
        gradeTable.setModel(sortTableModel);
        tableMouseListener = new TableMouseListener();
        gradeTable.addMouseListener(tableMouseListener);
        gradeTable.setSelectionModel(lsm);
        gradeTable.setColumnModel(tcm);

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
        //commentArea = new JTextArea(5, 10);
        commentArea.setLineWrap(true);
        JScrollPane rightScrollPane = new JScrollPane(commentArea);

        JPanel southSubPanel = new JPanel(new BorderLayout(4, 4));

        JButton addCommentButton = new JButton("Add comment");
        addCommentButton.addActionListener(e-> {
            int selectedRow = gradeTable.getSelectedRow();
            int selectedColumn = gradeTable.getSelectedColumn();
            GroupableTableHeader header = (GroupableTableHeader) gradeTable.getTableHeader();
            TableColumn tc = gradeTable.getColumnModel().getColumn(selectedColumn);
            List<ColumnGroup> columnGroups = header.getColumnGroups(tc);
            if (columnGroups.size() == 0)
                System.out.println("Cannot add a comment to non-grade items");
            else {
                String stuId = gradeTable.getValueAt(gradeTable.getSelectedRow(), 1).toString();

                String category = getCategory(selectedColumn, columnGroups, true);
                int subCategoryId  = getSubCategory(selectedColumn, true);

                Student student = currentCourse.getStudent(stuId);
                Grade grade = currentCourse.getsGrade(student);

                if (category.equals("Assignments")) {
                    grade.getaGrade().get(subCategoryId - 1).getNote().setNote(commentArea.getText());
                } else if (category.equals("Exams")) {
                    grade.geteGrade().get(subCategoryId - 1).getNote().setNote(commentArea.getText());
                } else {
                    grade.getpGrade().get(subCategoryId - 1).getNote().setNote(commentArea.getText());
                }

                currentCourse.getList().put(student, grade);
                //CommentRenderer renderer = (CommentRenderer) gradeTable.getCellRenderer(selectedRow, selectedColumn);
                //renderer.setComment(commentArea.getText());
                //renderer.setBackground();
            }
        });

        JButton deleteComment = new JButton("Delete comment");
        deleteComment.addActionListener(e-> {

        });

        southSubPanel.add(addCommentButton, BorderLayout.NORTH);
        southSubPanel.add(deleteComment, BorderLayout.SOUTH);

        rightPanel.add(label, BorderLayout.NORTH);
        rightPanel.add(rightScrollPane, BorderLayout.CENTER);
        rightPanel.add(southSubPanel, BorderLayout.SOUTH);
        container.add(rightPanel, BorderLayout.EAST);

        /*** Bottom Panel ***/
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.setPreferredSize(new Dimension(windowWidth * (6 / 8), windowHeight / 8));
        statisticsPanel = new StatisticsPanel(currentCourse);
        bottomPanel.add(statisticsPanel);
        JButton button = new JButton("Add new student");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SortTableModel sortTableModel1 = (SortTableModel) gradeTable.getModel();
            }
        });

        container.add(bottomPanel, BorderLayout.SOUTH);

        /*** Settings of MainFrame ***/
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
        statisticsPanel.setCourse(currentCourse);
        //Remove old listener
        gradeTable.removeMouseListener(tableMouseListener);
        //Add new listener
        tableMouseListener = new TableMouseListener();
        gradeTable.addMouseListener(tableMouseListener);

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

        setUpTypes(criterion);

        SortTableModel dm = (SortTableModel) gradeTable.getModel();
        dm.setClassList(Arrays.asList(type));
        dm.setDataVector(grades, headers);

        groupingHeaders(criterion);

        dm.addTableModelListener(new TableChangedListener());
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

    //Types are for sorting
    private void setUpTypes(Criterion criterion) {
        Vector<Class> classes = new Vector<>();
        classes.add(String.class);
        classes.add(String.class);
        classes.add(String.class);
        int items = criterion.getNumberOfAssignments() + criterion.getNumberOfProjects() + criterion.getNumberOfExams();
        for (int i = 0 ; i < items; i++) {
            classes.add(String.class);
        }
        classes.add(String.class);
        classes.add(double.class);
        type = classes.toArray(new Class[]{});
    }

    private void groupingHeaders(Criterion criterion) {
        int assignments = criterion.getNumberOfAssignments();
        int exams = criterion.getNumberOfExams();
        int projects = criterion.getNumberOfProjects();

        TableColumnModel cm = gradeTable.getColumnModel();
        ColumnGroup g_assignment = new ColumnGroup("Assignments");
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
    private void saveTableChanges(String id, String category, int index, String value) {
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

    private String getCategory(int selectedColumn, List<ColumnGroup> columnGroups, boolean headerGrouped) {
        if (headerGrouped) {
            return columnGroups.get(0).getHeaderValue();
        } else {
            return gradeTable.getColumnName(selectedColumn);
        }
    }

    private int getSubCategory(int selectedColumn, boolean headerGrouped) {
        if (headerGrouped)
            return Integer.parseInt(gradeTable.getColumnName(selectedColumn));
        else
            return -1;
    }

    public void setCommentAndWeighting() {
        commentArea.setText("");
        //Get note
        int selectedColumn = gradeTable.getSelectedColumn();

        String stuId = gradeTable.getValueAt(gradeTable.getSelectedRow(), 1).toString();

        GroupableTableHeader header = (GroupableTableHeader) gradeTable.getTableHeader();

        TableColumn tc = gradeTable.getColumnModel().getColumn(selectedColumn);
        List<ColumnGroup> columnGroups = header.getColumnGroups(tc);
        String comment = "";

        if (columnGroups.size() != 0) {
            String category = columnGroups.get(0).getHeaderValue();
            int subCategory = Integer.parseInt(gradeTable.getColumnName(selectedColumn));
            Student student = currentCourse.getStudent(stuId);
            if (category.equals("Assignments")) {
                comment = currentCourse.getsGrade(student).
                        getAssignment(subCategory - 1).getNote().getNote();
                weightingField.setText(currentCourse.getCcriterion().toString("a"));
            } else if (category.equals("Exams")) {
                comment = currentCourse.getsGrade(student).
                        getExam(subCategory -1).getNote().getNote();
                weightingField.setText(currentCourse.getCcriterion().toString("e"));
            } else {
                comment = currentCourse.getsGrade(student).
                        getProject(subCategory - 1).getNote().getNote();
                weightingField.setText(currentCourse.getCcriterion().toString("p"));
            }
            commentArea.setText(comment);
        } else {
            if (selectedColumn == gradeTable.getColumnCount() - 1)
                weightingField.setText(currentCourse.getCcriterion().toString());
            else
                weightingField.setText("");
            commentArea.setText("No comment");
        }
    }

    /*** Table Listener ***/
    //Display weighting in the textfield
    private class GradeTableSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            label.setText(String.valueOf(gradeTable.getSelectedColumn()));
            setCommentAndWeighting();
        }
    }

    //Listen to table value changed events
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
                String stu_id = gradeTable.getValueAt(selectedRow, 1).toString();
                String value = gradeTable.getValueAt(selectedRow, selectedColumn).toString();
                TableColumn tc = gradeTable.getColumnModel().getColumn(selectedColumn);
                List<ColumnGroup> columnGroups = header.getColumnGroups(tc);

                String category = getCategory(selectedColumn, columnGroups,
                        (columnGroups.size() != 0));
                int index = getSubCategory(selectedColumn,
                        (columnGroups.size() != 0));
                saveTableChanges(stu_id, category, index - 1, value);
            }
        }
    }

    //Listen to mouse clicks
    private class TableMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            // selects the row at which point the mouse is clicked
            if (event.getClickCount() == 2) {
                weightingField.setText("Double Clicked!");
                new SubCategory();
            } else if (event.getClickCount() == 1) {
                setCommentAndWeighting();
            }
        }
    }

    /*** Data Passing ***/
    public void setCurrentCourse(Course course) {
        this.currentCourse = course;
    }

}
