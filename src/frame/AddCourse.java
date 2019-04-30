//package frame;
//
//import UI.MainFrame;
//import course.*;
////import MainFrame;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ItemListener;
//import java.awt.event.ItemEvent;
//
//import javax.swing.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.*;
//
//import java.io.*;
//
///**
// * @Auther: wangqitong
// * @Date: 04-12-2019 12:48
// * @Description:
// */
//public class AddCourse extends JFrame implements ActionListener, ItemListener {
//    protected JButton okButton, resetButton;
//    protected JLabel courseIDLabel, courseNameLabel, semesterLabel, weightSettingLabel, previousYearLabel,
//            assignmentNumberAndWeightLabel, examNumberAndWeightLabel, projectNumberAndWeightLabel, participationNumberAndWeightLabel;
//    protected JTextField courseIDTextField, courseNameTextField, semesterTextField,
//            assignmentNumberTextField, assignmentWeightTextField,
//            examNumberTextField, examWeightTextField,
//            projectNumberTextField, projectWeightTextField,
//            participationNumberTextField, participationWeightTextField;
//    protected JPanel courseIDPanel, courseNamePanel, semesterPanel, weightSettingLabelPanel, weightSettingRadioButtonPanel, previousYearPanel,
//            assignmentPanel, examPanel, projectPanel, participationPanel, buttonPanel;
//    protected ButtonGroup weightGroup;
//    protected JRadioButton defaultWeightRadioButton, previousWeightRadioButton, customizedWeightRadioButton;
//    protected JComboBox<String> yearComboBox, previousCourseComboBox;
//    private int radioButtonIndex;
//    private List<Criterion> savedCriterion;
//    private MainFrame mainFrame;
//
//    public AddCourse(List<Criterion> sampleList, MainFrame inputMainFrame) throws IOException, ClassNotFoundException{
//        // TODO Auto-generated constructor stub
//
//        this.mainFrame = inputMainFrame;
//
//        savedCriterion = deepCopy(sampleList);
//
//        okButton = new JButton("OK");
//        okButton.addActionListener(this);
//        resetButton = new JButton("Reset");
//        resetButton.addActionListener(this);
//
//        weightGroup = new ButtonGroup();
//        defaultWeightRadioButton = new JRadioButton("Default (Average) ");
//        previousWeightRadioButton = new JRadioButton("Previous ");
//        customizedWeightRadioButton = new JRadioButton("Customized ");
//        defaultWeightRadioButton.addItemListener(this);
//        previousWeightRadioButton.addItemListener(this);
//        customizedWeightRadioButton.addItemListener(this);
//        weightGroup.add(defaultWeightRadioButton);
//        weightGroup.add(previousWeightRadioButton);
//        weightGroup.add(customizedWeightRadioButton);
//        radioButtonIndex = 0;
//
//        courseIDLabel = new JLabel("Course ID                           ");
//        courseNameLabel = new JLabel("Course Name                      ");
//        semesterLabel = new JLabel("Semester                                ");
//        weightSettingLabel = new JLabel("Weight Setting                                                               ");
//        previousYearLabel = new JLabel("Saved criterion                                     ");
//        assignmentNumberAndWeightLabel = new JLabel("Assignment Number/Weight:        ");
//        examNumberAndWeightLabel = new JLabel("Exam Number/Weight:                  ");
//        projectNumberAndWeightLabel = new JLabel("Project Number/Weight:                ");
//        participationNumberAndWeightLabel = new JLabel("Participation Number/Weight:       ");
//
//        String[] yearList = new String[]{"2018", "2019", "2020", "2021"};
//        yearComboBox = new JComboBox<String>(yearList);
//
//        List<String> previousCourseList = new ArrayList<>();
//        for (int i = 0; i < savedCriterion.size(); i++) {
//            previousCourseList.add(String.valueOf(savedCriterion.get(i).getName()));
//        }
////        String[] previousCourseList = new String[]{"cs530", "cs542", "cs585", "cs640"};
//        previousCourseComboBox = new JComboBox<String>(previousCourseList.toArray(new String[0]));
//        previousCourseComboBox.addActionListener(this);
//
//        courseIDTextField = new JTextField(13);
//        courseNameTextField = new JTextField(13);
//        semesterTextField = new JTextField(4);
//        assignmentNumberTextField = new JTextField(4);
//        assignmentWeightTextField = new JTextField(4);
//        examNumberTextField = new JTextField(4);
//        examWeightTextField = new JTextField(4);
//        projectNumberTextField = new JTextField(4);
//        projectWeightTextField = new JTextField(4);
//        participationNumberTextField = new JTextField(4);
//        participationWeightTextField = new JTextField(4);
//
//
//        courseIDPanel = new JPanel();
//        courseNamePanel = new JPanel();
//        semesterPanel = new JPanel();
//        weightSettingLabelPanel = new JPanel();
//        weightSettingRadioButtonPanel = new JPanel();
//        previousYearPanel = new JPanel();
//        assignmentPanel = new JPanel();
//        examPanel = new JPanel();
//        projectPanel = new JPanel();
//        participationPanel = new JPanel();
//        buttonPanel = new JPanel();
//
//        courseIDPanel.add(courseIDLabel);
//        courseIDPanel.add(courseIDTextField);
//
//        courseNamePanel.add(courseNameLabel);
//        courseNamePanel.add(courseNameTextField);
//
//        semesterPanel.add(semesterLabel);
//        semesterPanel.add(semesterTextField);
//        semesterPanel.add(yearComboBox);
//
//        weightSettingLabelPanel.add(weightSettingLabel);
//
//        weightSettingRadioButtonPanel.add(defaultWeightRadioButton);
//        weightSettingRadioButtonPanel.add(previousWeightRadioButton);
//        weightSettingRadioButtonPanel.add(customizedWeightRadioButton);
//
//        previousYearPanel.add(previousYearLabel);
//        previousYearPanel.add(previousCourseComboBox);
//
//        assignmentPanel.add(assignmentNumberAndWeightLabel);
//        assignmentPanel.add(assignmentNumberTextField);
//        assignmentPanel.add(assignmentWeightTextField);
//
//        examPanel.add(examNumberAndWeightLabel);
//        examPanel.add(examNumberTextField);
//        examPanel.add(examWeightTextField);
//
//        projectPanel.add(projectNumberAndWeightLabel);
//        projectPanel.add(projectNumberTextField);
//        projectPanel.add(projectWeightTextField);
//
//        participationPanel.add(participationNumberAndWeightLabel);
//        participationPanel.add(participationNumberTextField);
//        participationPanel.add(participationWeightTextField);
//
//        buttonPanel.add(okButton);
//        buttonPanel.add(resetButton);
//
//        weightGroup.add(defaultWeightRadioButton);
//        weightGroup.add(previousWeightRadioButton);
//        weightGroup.add(customizedWeightRadioButton);
//
//        this.add(courseIDPanel);
//        this.add(courseNamePanel);
//        this.add(semesterPanel);
//        this.add(weightSettingLabelPanel);
//        this.add(weightSettingRadioButtonPanel);
//        this.add(previousYearPanel);
//        this.add(assignmentPanel);
//        this.add(examPanel);
//        this.add(projectPanel);
//        this.add(participationPanel);
//        this.add(buttonPanel);
//
//        this.setTitle("Add Course");
//        this.setLayout(new GridLayout(11, 1));
//        this.setSize(400, 440);
//        this.setLocationRelativeTo(null);
//        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//
//        this.setVisible(true);
//        this.setResizable(false);
//    }
//
//    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
//        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//        ObjectOutputStream out = new ObjectOutputStream(byteOut);
//        out.writeObject(src);
//
//        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
//        ObjectInputStream in = new ObjectInputStream(byteIn);
//        @SuppressWarnings("unchecked")
//        List<T> dest = (List<T>) in.readObject();
//        return dest;
//    }
//
//    private int useNameFindIndex(String name){
//        for (int i = 0; i < savedCriterion.size(); i++){
//            if(savedCriterion.get(i).getName() == name)
//                return i;
//        }
//        return -1;
//    }
//
//    // When adding course work is finished (Click OK), a "Course" object is initialized.
//    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand() == "OK") {
//            boolean index = judgeInput();
//            if (index)
//                switch (radioButtonIndex){
//                    case 1:
//                        Criterion tempCriterion1 = new Criterion(0.25, 0.25, 0.25, 0.25,
//                                Integer.valueOf(assignmentNumberTextField.getText()), Integer.valueOf(examNumberTextField.getText()),
//                                Integer.valueOf(projectNumberTextField.getText()), courseNameTextField.getText());
//                        Course returnCourse1 = new Course(courseNameTextField.getText(), courseIDTextField.getText(), semesterTextField.getText(), yearComboBox.getSelectedItem().toString(),
//                                tempCriterion1);
//                        //mainFrame.setCurrentCourse(returnCourse1);
//                        mainFrame.refresh(returnCourse1);
//                        this.dispose();
//                        break;
//                    case 2:
//                        Course returnCourse2 = new Course(courseNameTextField.getText(), courseIDTextField.getText(), semesterTextField.getText(), yearComboBox.getSelectedItem().toString(),
//                                savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())));
//                        //mainFrame.setCurrentCourse(returnCourse2);
//                        mainFrame.refresh(returnCourse2);
//                        this.dispose();
//                        break;
//                    case 3:
//                        Criterion tempCriterion3 = new Criterion(Double.valueOf(assignmentWeightTextField.getText()), Double.valueOf(examWeightTextField.getText()),
//                                Double.valueOf(projectWeightTextField.getText()), Double.valueOf(participationWeightTextField.getText()),
//                                Integer.valueOf(assignmentNumberTextField.getText()), Integer.valueOf(examNumberTextField.getText()),
//                                Integer.valueOf(projectNumberTextField.getText()), courseNameTextField.getText());
//                        Course returnCourse3 = new Course(courseNameTextField.getText(), courseIDTextField.getText(), semesterTextField.getText(), yearComboBox.getSelectedItem().toString(),
//                                tempCriterion3);
//                        //mainFrame.setCurrentCourse(returnCourse3);
//                        mainFrame.refresh(returnCourse3);
//                        this.dispose();
//                        break;
//                }
//        }
//        else if (e.getActionCommand() == "Reset") {
//            courseIDTextField.setText("");
//            courseNameTextField.setText("");
//            semesterTextField.setText("");
//            assignmentNumberTextField.setText("");
//            assignmentWeightTextField.setText("");
//            examNumberTextField.setText("");
//            examWeightTextField.setText("");
//            projectNumberTextField.setText("");
//            projectWeightTextField.setText("");
//            participationNumberTextField.setText("");
//            participationWeightTextField.setText("");
//            weightGroup.clearSelection();
//            radioButtonIndex = 0;
//            previousCourseComboBox.setEnabled(true);
//        }
//        else if (e.getSource () == previousCourseComboBox){
//            clearCourseItems();
//            assignmentNumberTextField.setEditable(false);
//            assignmentWeightTextField.setEditable(false);
//            examNumberTextField.setEditable(false);
//            examWeightTextField.setEditable(false);
//            projectNumberTextField.setEditable(false);
//            projectWeightTextField.setEditable(false);
//            participationNumberTextField.setEditable(false);
//            participationWeightTextField.setEditable(false);
//            radioButtonIndex = 2;
//            previousCourseComboBox.setEnabled(true);
//
//            assignmentNumberTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getNumberOfAssignments()));
//            assignmentWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfAssignments()));
//            examNumberTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getNumberOfExams()));
//            examWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfExams()));
//            projectNumberTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getNumberOfProjects()));
//            projectWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfProjects()));
//            participationNumberTextField.setText(String.valueOf(1));
//            participationWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfAttendance()));
//
//        }
//    }
//
//    private void clearCourseItems() {
//        assignmentNumberTextField.setText("");
//        assignmentWeightTextField.setText("");
//        examNumberTextField.setText("");
//        examWeightTextField.setText("");
//        projectNumberTextField.setText("");
//        projectWeightTextField.setText("");
//        participationNumberTextField.setText("");
//        participationWeightTextField.setText("");
//    }
//
//    public void itemStateChanged(ItemEvent e){
//        if(e.getSource() == defaultWeightRadioButton){
//            clearCourseItems();
//            assignmentNumberTextField.setEditable(true);
//            assignmentWeightTextField.setEditable(false);
//            examNumberTextField.setEditable(true);
//            examWeightTextField.setEditable(false);
//            projectNumberTextField.setEditable(true);
//            projectWeightTextField.setEditable(false);
//            participationNumberTextField.setEditable(true);
//            participationWeightTextField.setEditable(false);
//            radioButtonIndex = 1;
//            previousCourseComboBox.setEnabled(false);
//        }
//        else if(e.getSource() == previousWeightRadioButton){
//            clearCourseItems();
//            assignmentNumberTextField.setEditable(false);
//            assignmentWeightTextField.setEditable(false);
//            examNumberTextField.setEditable(false);
//            examWeightTextField.setEditable(false);
//            projectNumberTextField.setEditable(false);
//            projectWeightTextField.setEditable(false);
//            participationNumberTextField.setEditable(false);
//            participationWeightTextField.setEditable(false);
//            radioButtonIndex = 2;
//            previousCourseComboBox.setEnabled(true);
//
//            assignmentNumberTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getNumberOfAssignments()));
//            assignmentWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfAssignments()));
//            examNumberTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getNumberOfExams()));
//            examWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfExams()));
//            projectNumberTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getNumberOfProjects()));
//            projectWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfProjects()));
//            participationNumberTextField.setText(String.valueOf(1));
//            participationWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfAttendance()));
//
//        }
//        else if(e.getSource() == customizedWeightRadioButton){
//            clearCourseItems();
//            assignmentNumberTextField.setEditable(true);
//            assignmentWeightTextField.setEditable(true);
//            examNumberTextField.setEditable(true);
//            examWeightTextField.setEditable(true);
//            projectNumberTextField.setEditable(true);
//            projectWeightTextField.setEditable(true);
//            participationNumberTextField.setEditable(true);
//            participationWeightTextField.setEditable(true);
//            radioButtonIndex = 3;
//            previousCourseComboBox.setEnabled(false);
//        }
//    }
//
//    private static boolean isNumeric(String str){
//        Pattern pattern = Pattern.compile("[0-9]*");
//        return pattern.matcher(str).matches();
//    }
//
//    private boolean isDouble(String str) {
//        if (null == str || "".equals(str)) {
//            return false;
//        }
//        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
//        if(pattern.matcher(str).matches()){
//            if(Double.parseDouble(str) >= 0.0 && Double.parseDouble(str) <= 1.0)
//                return true;
//            else
//                return false;
//        }
//        return false;
//    }
//
//    private boolean judgeInput() {
////        System.out.println(courseIDTextField.getText().equals(""));
//        if(radioButtonIndex == 1) {
//            if(courseIDTextField.getText().equals("") ||
//                    courseNameTextField.getText().equals("") ||
//                    semesterTextField.getText().equals("") ||
//                    assignmentNumberTextField.getText().equals("") ||
//                    examNumberTextField.getText().equals("") ||
//                    projectNumberTextField.getText().equals("") ||
//                    participationNumberTextField.getText().equals("")){
//                JOptionPane.showMessageDialog(null, "Inputted information is incomlplete!", "Info",JOptionPane.WARNING_MESSAGE);
//                return false;
//            }
//            else {
//                if(isNumeric(assignmentNumberTextField.getText()) &&
//                isNumeric(examNumberTextField.getText()) &&
//                isNumeric(projectNumberTextField.getText()) &&
//                isNumeric(participationNumberTextField.getText()))
//                    return true;
//                else {
//                    JOptionPane.showMessageDialog(null, "Item number must be intager!", "Info", JOptionPane.WARNING_MESSAGE);
//                    return false;
//                }
//            }
//        }
//        else if(radioButtonIndex == 2){
//            if(courseIDTextField.getText().equals("") ||
//                    courseNameTextField.getText().equals("") ||
//                    semesterTextField.getText().equals("")){
//                JOptionPane.showMessageDialog(null, "Inputted information is incomlplete!", "Info",JOptionPane.WARNING_MESSAGE);
//                return false;
//            }
//            else
//                return true;
//        }
//        else if(radioButtonIndex == 3){
//            if(courseIDTextField.getText().equals("") ||
//                    courseNameTextField.getText().equals("") ||
//                    semesterTextField.getText().equals("") ||
//                    assignmentNumberTextField.getText().equals("") ||
//                    assignmentWeightTextField.getText().equals("") ||
//                    examNumberTextField.getText().equals("") ||
//                    examWeightTextField.getText().equals("") ||
//                    projectNumberTextField.getText().equals("") ||
//                    projectWeightTextField.getText().equals("") ||
//                    participationNumberTextField.getText().equals("") ||
//                    participationWeightTextField.getText().equals("")){
//                JOptionPane.showMessageDialog(null, "Inputted information is incomlplete!", "Info",JOptionPane.WARNING_MESSAGE);
//                return false;
//            }
//            else{
//                if(isNumeric(assignmentNumberTextField.getText()) &&
//                        isNumeric(examNumberTextField.getText()) &&
//                        isNumeric(projectNumberTextField.getText()) &&
//                        isNumeric(participationNumberTextField.getText())){
//                    if(isDouble(assignmentWeightTextField.getText()) &&
//                            isDouble(examWeightTextField.getText()) &&
//                            isDouble(projectWeightTextField.getText()) &&
//                            (isDouble(participationWeightTextField.getText()))){
//                        if(Double.parseDouble(assignmentWeightTextField.getText()) +
//                                Double.parseDouble(examWeightTextField.getText()) +
//                                Double.parseDouble(projectWeightTextField.getText()) +
//                                Double.parseDouble(participationWeightTextField.getText()) == 1.0)
//                            return true;
//                        else {
//                            JOptionPane.showMessageDialog(null, "The sum of item weight must be 1.0!", "Info", JOptionPane.WARNING_MESSAGE);
//                            return false;
//                        }
//                    }
//                    else{
//                        JOptionPane.showMessageDialog(null, "Item weight must be float ranging from 0 to 1!", "Info", JOptionPane.WARNING_MESSAGE);
//                        return false;
//                    }
//                }
//                else {
//                    JOptionPane.showMessageDialog(null, "Item number must be intager!", "Info", JOptionPane.WARNING_MESSAGE);
//                    return false;
//                }
//            }
//        }
//        else{
//            JOptionPane.showMessageDialog(null, "Inputted information is incomlplete!", "Info",JOptionPane.WARNING_MESSAGE);
//            return false;
//        }
//    }
//}
