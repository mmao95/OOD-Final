package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import javax.swing.table.*;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import UI.Bottom.AddingPanel;
import UI.Bottom.RemovePanel;
import UI.Bottom.StatisticsPanel;
import course.Category;
import course.Course;
//import frame.AddCourse;
import course.NewCriterion;
import frame.CommentFrame;
import frame.SubCategory;
import grade.Grade;
import grade.GradeComp;
import personal.Student;

/**
 * @Auther: Di Zhu
 * @Date: 04-12-2019 12:19
 * @Description:
 */
public class MainFrameDeprecated extends JFrame {

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
//    private JLabel rowIndicator;
//    private JLabel columnIndicator;
//    private JTextArea commentArea = new JTextArea(5, 10);
    //Left panel components
    //JTree
    private JTree jTree;
    //Initialization of JTree Node
    private DefaultMutableTreeNode root = new DefaultMutableTreeNode("All courses");
    private DefaultTreeModel jMode = new DefaultTreeModel(root);

    //Bottom panel components
    private StatisticsPanel statisticsPanel;
    private RemovePanel removePanel;
    private AddingPanel addingPanel;

    private int windowWidth = 1280;
    private int windowHeight = 720;
    /*** Listener ***/
    //private TableMouseListener tableMouseListener;

    /*** Data ***/
    private Map<String, Map<String, Course>> courseMap = new HashMap<>();
    private Course currentCourse = null;
    private Class[] type = {};

    private void initialization() {
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
            String sb = "TEST CONTENT";
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            int retrival = chooser.showSaveDialog(null);
            if (retrival == JFileChooser.APPROVE_OPTION) {
                try {
                    currentCourse.writeToFile(chooser.getSelectedFile() + ".txt");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            currentCourse.writeToFile("c.txt");
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
//            try {
//                List<Criterion> criterionList = new ArrayList<>();
//                new AddCourse(criterionList, this);
//            } catch (IOException ioe) {
//                ioe.printStackTrace();
//            } catch (ClassNotFoundException cnfe) {
//                cnfe.printStackTrace();
//            }
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
        jTree.addTreeSelectionListener(e-> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)
                    jTree.getLastSelectedPathComponent();
            if (node == null)
                return;
            if (node.isLeaf()) {
                try {
                    //Clear selection first
                    //If index of the selected item does not exist in another table
                    gradeTable.getSelectionModel().clearSelection();
                    weightingField.setText("");
                    Object nodeInfo = node.getUserObject();
                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
                    Course course = courseMap.get(parent.getUserObject()).get(nodeInfo);
                    setUpGradeTable(course.getCcriterion(), course.getList());
                    currentCourse = course;
                } catch (NullPointerException exception) {
                    exception.printStackTrace();
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
        lsm.addListSelectionListener(new SelectionChangedListener());

        //Set up column model
        TableColumnModel tcm = gradeTable.getColumnModel();
        tcm.getSelectionModel().addListSelectionListener(new SelectionChangedListener());

        //Add models to table and set up mouse listener
        gradeTable.setModel(sortTableModel);
        TableMouseListener tableMouseListener = new TableMouseListener();
        gradeTable.addMouseListener(tableMouseListener);

        TableHeaderListener tableHeaderListener = new TableHeaderListener();
        gradeTable.getTableHeader().addMouseListener(tableHeaderListener);

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
//        rightPanel = new JPanel(new BorderLayout(4, 4));
//        rightPanel.setPreferredSize(new Dimension(windowWidth / 8, windowHeight));
//
//        rowIndicator = new JLabel("Row: ");
//        columnIndicator = new JLabel("Column: ");
//        JPanel rightNorth = new JPanel(new BorderLayout());
//        rightNorth.add(rowIndicator, BorderLayout.NORTH);
//        rightNorth.add(columnIndicator, BorderLayout.SOUTH);
//
//        commentArea.setLineWrap(true);
//        JScrollPane rightScrollPane = new JScrollPane(commentArea);
//
//        rightPanel.add(rightNorth, BorderLayout.NORTH);
//        rightPanel.add(rightScrollPane, BorderLayout.CENTER);
//        container.add(rightPanel, BorderLayout.EAST);

        /*** Bottom Panel ***/
        //bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel = new JPanel(new BorderLayout(8, 8));
        bottomPanel.setBorder(new EmptyBorder(16, 16, 16, 16));
        bottomPanel.setPreferredSize(new Dimension(windowWidth, windowHeight / 8));

        //Bottom Left Panel
        File logo = new File("bu.bmp");
        ImagePanel bottomLeft = new ImagePanel(new ImageIcon("bu.png").getImage());
        try {
            Image image = ImageIO.read(logo);
            bottomLeft = new ImagePanel(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
        bottomLeft.setPreferredSize(new Dimension(windowWidth / 10, windowHeight / 8));

        //Bottom Center Panel
        JPanel bottomCenter = new JPanel(new BorderLayout(8, 0));
        bottomCenter.setBorder(new EmptyBorder(0, 8, 0, 8));
        bottomCenter.setPreferredSize(new Dimension(windowWidth * (8 / 10), windowHeight / 8));

        statisticsPanel = new StatisticsPanel(currentCourse);
        removePanel = new RemovePanel(this, currentCourse);
        //removePanel.setSize(new Dimension(windowWidth * (8 / 30), windowHeight / 8));
        addingPanel = new AddingPanel(this, currentCourse);

        bottomCenter.add(statisticsPanel, BorderLayout.WEST);
        bottomCenter.add(removePanel, BorderLayout.CENTER);
        bottomCenter.add(addingPanel, BorderLayout.EAST);

//        //Bottom Right Panel
//        JPanel bottomRight = new JPanel(new BorderLayout());
//        bottomRight.setPreferredSize(new Dimension(windowWidth / 10, windowHeight / 8));

//        JPanel commentPanel = new JPanel(new BorderLayout(4, 4));
//
//        JButton addCommentButton = new JButton("Add comment");
//        addCommentButton.addActionListener(e-> {
//            int selectedRow = gradeTable.getSelectedRow();
//            int selectedColumn = gradeTable.getSelectedColumn();
//            GroupableTableHeader header = (GroupableTableHeader) gradeTable.getTableHeader();
//            TableColumn tc = gradeTable.getColumnModel().getColumn(selectedColumn);
//            List<ColumnGroup> columnGroups = header.getColumnGroups(tc);
//            if (columnGroups.size() == 0)
//                System.out.println("Cannot add a comment to non-grade items");
//            else {
//                String stuId = gradeTable.getValueAt(gradeTable.getSelectedRow(), 1).toString();
//
//                String category = getCategory(selectedColumn, columnGroups, true);
//                int subCategoryId  = getSubCategory(selectedColumn, true);
//
//                Student student = currentCourse.getStudent(stuId);
//                Grade grade = currentCourse.getsGrade(student);
//
////                if (category.equals("Assignments")) {
////                    grade.getaGrade().get(subCategoryId - 1).getNote().setNote(commentArea.getText());
////                } else if (category.equals("Exams")) {
////                    grade.geteGrade().get(subCategoryId - 1).getNote().setNote(commentArea.getText());
////                } else {
////                    grade.getpGrade().get(subCategoryId - 1).getNote().setNote(commentArea.getText());
////                }
//
//                currentCourse.getList().put(student, grade);
//                currentCourse.writeToFile("c.txt");
//            }
//        });

//        JButton deleteComment = new JButton("Delete comment");
//        deleteComment.addActionListener(e-> {
//
//        });

//        commentPanel.add(addCommentButton, BorderLayout.NORTH);
//        commentPanel.add(deleteComment, BorderLayout.SOUTH);
//
//        bottomRight.add(commentPanel, BorderLayout.CENTER);

        bottomPanel.add(bottomLeft, BorderLayout.WEST);
        bottomPanel.add(bottomCenter, BorderLayout.CENTER);
//        bottomPanel.add(bottomRight, BorderLayout.EAST);

        container.add(bottomPanel, BorderLayout.SOUTH);

        /*** Settings of UI.MainFrame ***/
        setTitle("Grading System");
        setBounds((int)(screenSize.getWidth() - windowWidth)/ 2,
                (int)(screenSize.getHeight() - windowHeight)/ 2,
                windowWidth, windowHeight);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    //Constructor
    public MainFrameDeprecated() {
        initialization();
    }

    /*** JTree Methods ***/
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
    //Interface for outside calling
    public void update(Course course) {
        update(course, root);
    }

    private void update(Course course, DefaultMutableTreeNode root) {
        String courseName = course.getInfo()[0];
        String courseSemester = course.getInfo()[2] + course.getInfo()[3];

        /*** Update Map ***/
        Map<String, Course> subCourseMap = courseMap.get(courseName);
        subCourseMap.put(courseSemester, course);
        courseMap.put(courseName, subCourseMap);

        /*** Update Table ***/
        setUpGradeTable(course.getCcriterion(), course.getList());

        /*** Update Components ***/
        currentCourse = course;
        statisticsPanel.refeshPanel(currentCourse);
        addingPanel.refeshPanel(currentCourse);
    }

    //Interface for outside calling
    public void addCourse(Course course) {
        //Clear selection first
        //If index of the selected item does not exist in another table
        gradeTable.getSelectionModel().clearSelection();
        weightingField.setText("");
        addCourse(course, root);
    }

    public void updateComment(String comment) {

    }

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
        statisticsPanel.refeshPanel(currentCourse);
        addingPanel.refeshPanel(currentCourse);

//        //Remove old listener
//        gradeTable.removeMouseListener(tableMouseListener);
//        //Add new listener
//        tableMouseListener = new TableMouseListener();
//        gradeTable.addMouseListener(tableMouseListener);

        jTree.setModel(new DefaultTreeModel(root));
        //Expand the newest node
        semesterPath = find(root, courseSemester);
        jTree.setSelectionPath(semesterPath);
        jTree.scrollPathToVisible(semesterPath);
    }

    //private void setUpGradeTable(Criterion criterion, HashMap<Student, Grade> grade) {
    private void setUpGradeTable(NewCriterion criterion, HashMap<Student, Grade> grade) {
        Vector<String> headers = setUpTableHeader(criterion);
        Vector<Object> grades = loadData(criterion.getCategories().size(), grade);

        setUpTypes(criterion);

        SortTableModel dm = (SortTableModel) gradeTable.getModel();
        dm.setClassList(Arrays.asList(type));
        dm.setDataVector(grades, headers);

        groupingHeaders(criterion);

        dm.addTableModelListener(new TableChangedListener());
    }

    //private Vector<String> setUpTableHeader(Criterion criterion) {
    private Vector<String> setUpTableHeader(NewCriterion criterion) {
        String[] basic = new String[]{"Name", "Id", "Email"};

//        int assignments = criterion.getNumberOfAssignments();
//        int exams = criterion.getNumberOfExams();
//        int projects = criterion.getNumberOfProjects();

        Vector<String> vector = new Vector<>();
        vector.addAll(Arrays.asList(basic));

        for (int i = 0 ; i < criterion.getCategories().size() ; i++) {
            Category currentCategory = criterion.getCategories().get(i);
            for (int j = 0 ; j < currentCategory.getNumberOfTasks() ; j++) {
                vector.add(String.valueOf(j + 1));
            }
        }

        vector.add("Extra");
        vector.add("Total");
        return vector;
    }

    //private Vector<Object> loadData(HashMap<Student, Grade> gradeMap) {
    private Vector<Object> loadData(int categoryNum, HashMap<Student, Grade> gradeMap) {
        Vector<Object> grades = new Vector<>();
        System.out.println(gradeMap.size());
        for (Student key : gradeMap.keySet()) {
            Vector<String> temp = new Vector<>();
            temp.add(key.getName().toString());
            temp.add(key.getId());
            temp.add(key.getEmail());
            Grade grade = gradeMap.get(key);
            for (int i = 0 ; i < categoryNum ; i++) {
                List<GradeComp> gradeComps = grade.getCategory(i);
                for (int j = 0 ; j < gradeComps.size() ; j++) {
                    temp.add(gradeComps.get(j).getScore());
                }
            }

            temp.add(grade.getExtra().getScore());
            temp.add(String.valueOf(grade.getTtscore()));
            grades.add(temp);
        }
        return grades;
    }

    //Types are for sorting
    //private void setUpTypes(Criterion criterion) {
    private void setUpTypes(NewCriterion criterion) {
        Vector<Class> classes = new Vector<>();
        classes.add(String.class);
        classes.add(String.class);
        classes.add(String.class);
//        int items = criterion.getNumberOfAssignments() + criterion.getNumberOfProjects() + criterion.getNumberOfExams();
//        for (int i = 0 ; i < items; i++) {
//            classes.add(String.class);
//        }

        for (int i = 0 ; i < criterion.getCategories().size() ; i++) {
            Category currentCategory = criterion.getCategories().get(i);
            for (int j = 0 ; j < currentCategory.getNumberOfTasks() ; j++) {
                classes.add(String.class);
            }
        }

        //classes.add(String.class);  //Attendance
        classes.add(double.class);  //Extra Credit
        classes.add(double.class);  //Total
        type = classes.toArray(new Class[]{});
    }

    //private void groupingHeaders(Criterion criterion) {
    private void groupingHeaders(NewCriterion criterion) {
//        int assignments = criterion.getNumberOfAssignments();
//        int exams = criterion.getNumberOfExams();
//        int projects = criterion.getNumberOfProjects();

        TableColumnModel cm = gradeTable.getColumnModel();
//        ColumnGroup g_assignment = new ColumnGroup("Assignments");
//        ColumnGroup g_exam = new ColumnGroup("Exams");
//        ColumnGroup g_project = new ColumnGroup("Projects");

        //Assignment Headers
        int startIndex = 3;
        GroupableTableHeader header = (GroupableTableHeader) gradeTable.getTableHeader();
        for (int i = 0 ; i < criterion.getCategories().size() ; i++) {
            Category currentCategory = criterion.getCategories().get(i);
            ColumnGroup categoryGroup = new ColumnGroup(currentCategory.getName());
            for (int j = 0 ; j < currentCategory.getNumberOfTasks() ; j++) {
                categoryGroup.add(cm.getColumn(startIndex));
                startIndex++;
            }
            header.addColumnGroup(categoryGroup);
        }
//        for (int i = 0 ; i < assignments ; i++) {
//            g_assignment.add(cm.getColumn(startIndex));
//            startIndex++;
//        }
//        //Exam headers
//        for (int i = 0 ; i < exams ; i++) {
//            g_exam.add(cm.getColumn(startIndex));
//            startIndex++;
//        }
//        //Project headers
//        for (int i = 0 ; i < projects ; i++) {
//            g_project.add(cm.getColumn(startIndex));
//            startIndex++;
//        }

//        header.addColumnGroup(g_assignment);
//        header.addColumnGroup(g_exam);
//        header.addColumnGroup(g_project);
        gradeTable.setTableHeader(header);
    }

    /*** Table Methods **/

    /**
     * Save changes of gradeTable to file.
     * @param id Id of the student whose information is changed.
     * @param category Which category is changed.
     * @param itemIndex Index of the changed item in the category. Already subtracted 1 when inputting.
     * @param value New value
     */
    //private void saveTableChanges(String id, String category, int index, String value) {
    private void saveTableChanges(String id, String category, int itemIndex, String value) {
        String courseName = currentCourse.getInfo()[0];
        String courseSemester = currentCourse.getInfo()[2] + currentCourse.getInfo()[3];

        GroupableTableHeader header = (GroupableTableHeader) gradeTable.getTableHeader();

        Student student = currentCourse.getStudent(id);
        Grade grade = currentCourse.getsGrade(student);

        switch (category) {
            case "Name":
                String[] name = value.split(" ");
                if (name.length == 2) {
                    student.setName(name[0], "", name[1]);
                } else if (name.length == 3) {
                    student.setName(name[0], name[1], name[2]);
                }
                break;
            case "Id":
                student.setId(value);
                break;
            case "Email":
                student.setEmail(value);
                break;
//            case "Assignments":
//                grade.setAssignment(value, index);
//                break;
//            case "Exams":
//                grade.setExam(value, index);
//                break;
//            case "Projects":
//                grade.setProject(value, index);
//                break;
//            case "Attendance":
//                grade.setAttendence(value);
//                break;
            default:
                for (int i = 0 ; i < currentCourse.getCcriterion().getCategories().size() ; i++) {
                    if (currentCourse.getCcriterion().getCategories().get(i).getName().equals(category)) {
                        int categoryIndex = header.findIndexOfGroup(category);
                        if (categoryIndex == -1) {
                            System.out.println("Category not found!");
                            break;
                        } else {
                            grade.getOne(categoryIndex, itemIndex).setScore(value);
                        }
                    }
                }
                break;
        }

        currentCourse.getList().put(student, grade);
        currentCourse.calculateAll();

        currentCourse.writeToFile("c.txt");

        /*** Update Map ***/
        Map<String, Course> subCourseMap = courseMap.get(courseName);
        subCourseMap.put(courseSemester, currentCourse);
        courseMap.put(courseName, subCourseMap);
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

    private String getCommentOfSelectedCell(int selectedRow, int selectedColumn) {
        //Get note
        String stuId = gradeTable.getValueAt(selectedRow, 1).toString();

        GroupableTableHeader header = (GroupableTableHeader) gradeTable.getTableHeader();

        TableColumn tc = gradeTable.getColumnModel().getColumn(selectedColumn);
        List<ColumnGroup> columnGroups = header.getColumnGroups(tc);

        if (columnGroups.size() != 0) {
            String category = columnGroups.get(0).getHeaderValue();
            int subCategory = Integer.parseInt(gradeTable.getColumnName(selectedColumn));
            Student student = currentCourse.getStudent(stuId);
            return currentCourse.getsGrade(student).
                    getCategory(header.findIndexOfGroup(category)).get(subCategory - 1).getNote().getNote();
        }
        return "";
    }

    private void setWeighting() {
        int selectedColumn = gradeTable.getSelectedColumn();

        String stuId = gradeTable.getValueAt(gradeTable.getSelectedRow(), 1).toString();

        GroupableTableHeader header = (GroupableTableHeader) gradeTable.getTableHeader();

        TableColumn tc = gradeTable.getColumnModel().getColumn(selectedColumn);
        List<ColumnGroup> columnGroups = header.getColumnGroups(tc);

        if (columnGroups.size() != 0) {
            String category = columnGroups.get(0).getHeaderValue();
            Student student = currentCourse.getStudent(stuId);
            weightingField.setText(currentCourse.getsGrade(student).getCategory(header.findIndexOfGroup(category)).toString());
        } else {
            weightingField.setText("");
        }

    }

//    public void setCommentAndWeighting() {
//        commentArea.setText("");
//        //Get note
//        int selectedColumn = gradeTable.getSelectedColumn();
//
//        String stuId = gradeTable.getValueAt(gradeTable.getSelectedRow(), 1).toString();
//
//        GroupableTableHeader header = (GroupableTableHeader) gradeTable.getTableHeader();
//
//        TableColumn tc = gradeTable.getColumnModel().getColumn(selectedColumn);
//        List<ColumnGroup> columnGroups = header.getColumnGroups(tc);
//        String comment = "";
//
//        if (columnGroups.size() != 0) {
//            String category = columnGroups.get(0).getHeaderValue();
//            int subCategory = Integer.parseInt(gradeTable.getColumnName(selectedColumn));
//            Student student = currentCourse.getStudent(stuId);
//            if (category.equals("Assignments")) {
//                comment = currentCourse.getsGrade(student).
//                        getAssignment(subCategory - 1).getNote().getNote();
//                weightingField.setText(currentCourse.getCcriterion().toString("a"));
//            } else if (category.equals("Exams")) {
//                comment = currentCourse.getsGrade(student).
//                        getExam(subCategory - 1).getNote().getNote();
//                weightingField.setText(currentCourse.getCcriterion().toString("e"));
//            } else {
//                comment = currentCourse.getsGrade(student).
//                        getProject(subCategory - 1).getNote().getNote();
//                weightingField.setText(currentCourse.getCcriterion().toString("p"));
//            }
//            commentArea.setText(comment);
//        } else {
//            if (selectedColumn == gradeTable.getColumnCount() - 1)
//                weightingField.setText(currentCourse.getCcriterion().toString());
//            else
//                weightingField.setText("");
//            commentArea.setText("No comment");
//        }
//    }

    /*** Table Listener ***/
    //Display weighting in the textfield
    private class SelectionChangedListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            if (gradeTable.getSelectedRow() != -1)
                setWeighting();
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

    private void createCommentFrame(int selectedRow, int selectedColumn) {
        new CommentFrame(this, getCommentOfSelectedCell(selectedRow, selectedColumn));
    }

    //Listen to mouse clicks
    private class TableMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            if (SwingUtilities.isRightMouseButton(event)) {
                System.out.println("Right clicked!");

                int selectedColumn = gradeTable.columnAtPoint(event.getPoint());
                int selectedRow = gradeTable.rowAtPoint(event.getPoint());
                gradeTable.changeSelection(selectedRow, selectedColumn, false, false);

                GroupableTableHeader header = (GroupableTableHeader) gradeTable.getTableHeader();
                TableColumn tc = gradeTable.getColumnModel().getColumn(selectedColumn);
                List<ColumnGroup> columnGroups = header.getColumnGroups(tc);

                if (columnGroups.size() != 0 ) {
                    createCommentFrame(selectedRow, selectedColumn);
                }

            } else {
                setWeighting();
            }
        }
    }

    //Listens to header clicked events
    private class TableHeaderListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event) {
            if (event.getClickCount() == 2) {
                int selectedColumn = gradeTable.columnAtPoint(event.getPoint());

                GroupableTableHeader header = (GroupableTableHeader) gradeTable.getTableHeader();
                TableColumn tc = gradeTable.getColumnModel().getColumn(selectedColumn);
                List<ColumnGroup> columnGroups = header.getColumnGroups(tc);

                if (columnGroups.size() != 0 ) {
                    String category = getCategory(selectedColumn, columnGroups,
                            (columnGroups.size() != 0));
                    new SubCategory(header.findIndexOfGroup(category));
                }
            }
        }
    }
}
