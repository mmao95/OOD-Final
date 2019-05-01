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
//* @Auther: wangqitong
//* @Date: 04-12-2019 12:48
//* @Description:
//*/
//public class AddCourse extends JFrame implements ActionListener, ItemListener {
//   protected JButton okButton, resetButton;
//   protected JLabel courseIDLabel, courseNameLabel, semesterLabel, weightSettingLabel, previousYearLabel,
//            categoryLabel, numberLabel, weightLabel;
//   protected JTextField courseIDTextField, courseNameTextField, semesterTextField;
//   protected JTextArea categoryTextArea, numberTextArea, weightTextArea;
//   protected JPanel courseIDPanel, courseNamePanel, semesterPanel, weightSettingLabelPanel, weightSettingRadioButtonPanel, previousYearPanel,
//            categoryPanel, numberPanel, weightPanel, buttonPanel, nullPanel;
//   protected ButtonGroup weightGroup;
//   protected JRadioButton defaultWeightRadioButton, previousWeightRadioButton, customizedWeightRadioButton;
//   protected JComboBox<String> yearComboBox, previousCourseComboBox;
//   private int radioButtonIndex;
//   private List<Criterion> savedCriterion;
//   private MainFrame mainFrame;
//
//   public AddCourse(List<Criterion> sampleList, MainFrame inputMainFrame) throws IOException, ClassNotFoundException{
//       // TODO Auto-generated constructor stub
//
//       this.mainFrame = inputMainFrame;
//
//       savedCriterion = deepCopy(sampleList);
//
//       okButton = new JButton("OK");
//       okButton.addActionListener(this);
//       resetButton = new JButton("Reset");
//       resetButton.addActionListener(this);
//
//       weightGroup = new ButtonGroup();
//       defaultWeightRadioButton = new JRadioButton("Default (Average) ");
//       previousWeightRadioButton = new JRadioButton("Previous ");
//       customizedWeightRadioButton = new JRadioButton("Customized ");
//       defaultWeightRadioButton.addItemListener(this);
//       previousWeightRadioButton.addItemListener(this);
//       customizedWeightRadioButton.addItemListener(this);
//       weightGroup.add(defaultWeightRadioButton);
//       weightGroup.add(previousWeightRadioButton);
//       weightGroup.add(customizedWeightRadioButton);
//       radioButtonIndex = 0;
//
//       courseIDLabel = new JLabel("Course ID                           ");
//       courseNameLabel = new JLabel("Course Name                      ");
//       semesterLabel = new JLabel("Semester                                ");
//       weightSettingLabel = new JLabel("Weight Setting                                                               ");
//       previousYearLabel = new JLabel("Saved criterion                                     ");
//       categoryLabel = new JLabel("Category");
//       numberLabel = new JLabel("Number");
//       weightLabel = new JLabel("Weight");
//
//       String[] yearList = new String[]{"2018", "2019", "2020", "2021"};
//       yearComboBox = new JComboBox<String>(yearList);
//
//       List<String> previousCourseList = new ArrayList<>();
//       for (int i = 0; i < savedCriterion.size(); i++) {
//           previousCourseList.add(String.valueOf(savedCriterion.get(i).getName()));
//       }
////        String[] previousCourseList = new String[]{"cs530", "cs542", "cs585", "cs640"};
//       previousCourseComboBox = new JComboBox<String>(previousCourseList.toArray(new String[0]));
//       previousCourseComboBox.addActionListener(this);
//
//       courseIDTextField = new JTextField(13);
//       courseNameTextField = new JTextField(13);
//       semesterTextField = new JTextField(4);
//
//       categoryTextArea = new JTextArea("", 5, 10);
//       categoryTextArea.setEditable(true);
//       categoryTextArea.setEnabled(true);
//       numberTextArea = new JTextArea("", 5, 10);
//       numberTextArea.setEditable(true);
//       numberTextArea.setEnabled(true);
//       weightTextArea = new JTextArea("", 5, 10);
//       weightTextArea.setEditable(true);
//       weightTextArea.setEnabled(true);
//
//       courseIDPanel = new JPanel();
//       courseNamePanel = new JPanel();
//       semesterPanel = new JPanel();
//       weightSettingLabelPanel = new JPanel();
//       weightSettingRadioButtonPanel = new JPanel();
//       previousYearPanel = new JPanel();
//       categoryPanel = new JPanel();
//       numberPanel = new JPanel();
//       weightPanel = new JPanel();
//       buttonPanel = new JPanel();
//       nullPanel = new JPanel();
//
//       courseIDPanel.add(courseIDLabel);
//       courseIDPanel.add(courseIDTextField);
//
//       courseNamePanel.add(courseNameLabel);
//       courseNamePanel.add(courseNameTextField);
//
//       semesterPanel.add(semesterLabel);
//       semesterPanel.add(semesterTextField);
//       semesterPanel.add(yearComboBox);
//
//       weightSettingLabelPanel.add(weightSettingLabel);
//
//       weightSettingRadioButtonPanel.add(defaultWeightRadioButton);
//       weightSettingRadioButtonPanel.add(previousWeightRadioButton);
//       weightSettingRadioButtonPanel.add(customizedWeightRadioButton);
//
//       previousYearPanel.add(previousYearLabel);
//       previousYearPanel.add(previousCourseComboBox);
//
//       buttonPanel.add(okButton);
//       buttonPanel.add(resetButton);
//
//       weightGroup.add(defaultWeightRadioButton);
//       weightGroup.add(previousWeightRadioButton);
//       weightGroup.add(customizedWeightRadioButton);
//
//       nullPanel.setBounds(0, 0, 1, 1);
//       courseIDPanel.setBounds(0, 10, 405, 40);
//       courseNamePanel.setBounds(0, 50, 405, 40);
//       semesterPanel.setBounds(0, 90, 405, 40);
//       weightSettingLabelPanel.setBounds(0, 130, 405, 35);
//       weightSettingRadioButtonPanel.setBounds(0, 165, 405, 45);
//       previousYearPanel.setBounds(0, 210, 405, 40);
//
//
//       categoryPanel.add(categoryLabel);
//       categoryPanel.add(categoryTextArea);
//       numberPanel.add(numberLabel);
//       numberPanel.add(numberTextArea);
//       weightPanel.add(weightLabel);
//       weightPanel.add(weightTextArea);
//
//       categoryPanel.setBounds(0, 250, 135, 120);
//       numberPanel.setBounds(135, 250, 135, 120);
//       weightPanel.setBounds(270, 250, 135, 120);
//       buttonPanel.setBounds(0, 380, 405, 50);
//
//       this.add(buttonPanel);
//       this.add(categoryPanel);
//       this.add(numberPanel);
//       this.add(weightPanel);
//       this.add(previousYearPanel);
//       this.add(weightSettingRadioButtonPanel);
//       this.add(weightSettingLabelPanel);
//       this.add(semesterPanel);
//       this.add(courseNamePanel);
//       this.add(courseIDPanel);
//       this.add(nullPanel);
//
//       this.setTitle("Add Course");
//       this.setSize(405, 450);
//       this.setLocationRelativeTo(null);
//       this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//
//       this.setVisible(true);
//       this.setResizable(false);
//   }
//
//   public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
//       ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
//       ObjectOutputStream out = new ObjectOutputStream(byteOut);
//       out.writeObject(src);
//
//       ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
//       ObjectInputStream in = new ObjectInputStream(byteIn);
//       @SuppressWarnings("unchecked")
//       List<T> dest = (List<T>) in.readObject();
//       return dest;
//   }
//
//   private int useNameFindIndex(String name){
//       for (int i = 0; i < savedCriterion.size(); i++){
//           if(savedCriterion.get(i).getName() == name)
//               return i;
//       }
//       return -1;
//   }
//
//   // When adding course work is finished (Click OK), a "Course" object is initialized.
//   public void actionPerformed(ActionEvent e) {
//       if (e.getActionCommand() == "OK") {
//           boolean index = judgeInput();
//           if (index)
//               switch (radioButtonIndex){
//                   case 1:
//                       Criterion tempCriterion1 = new Criterion(0.25, 0.25, 0.25, 0.25,
//                               Integer.valueOf(assignmentNumberTextField.getText()), Integer.valueOf(examNumberTextField.getText()),
//                               Integer.valueOf(projectNumberTextField.getText()), courseNameTextField.getText());
//                       Course returnCourse1 = new Course(courseNameTextField.getText(), courseIDTextField.getText(), semesterTextField.getText(), yearComboBox.getSelectedItem().toString(),
//                               tempCriterion1);
//                       //mainFrame.setCurrentCourse(returnCourse1);
//                       mainFrame.refresh(returnCourse1);
//                       this.dispose();
//                       break;
//                   case 2:
//                       Course returnCourse2 = new Course(courseNameTextField.getText(), courseIDTextField.getText(), semesterTextField.getText(), yearComboBox.getSelectedItem().toString(),
//                               savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())));
//                       //mainFrame.setCurrentCourse(returnCourse2);
//                       mainFrame.refresh(returnCourse2);
//                       this.dispose();
//                       break;
//                   case 3:
//                       Criterion tempCriterion3 = new Criterion(Double.valueOf(assignmentWeightTextField.getText()), Double.valueOf(examWeightTextField.getText()),
//                               Double.valueOf(projectWeightTextField.getText()), Double.valueOf(participationWeightTextField.getText()),
//                               Integer.valueOf(assignmentNumberTextField.getText()), Integer.valueOf(examNumberTextField.getText()),
//                               Integer.valueOf(projectNumberTextField.getText()), courseNameTextField.getText());
//                       Course returnCourse3 = new Course(courseNameTextField.getText(), courseIDTextField.getText(), semesterTextField.getText(), yearComboBox.getSelectedItem().toString(),
//                               tempCriterion3);
//                       //mainFrame.setCurrentCourse(returnCourse3);
//                       mainFrame.refresh(returnCourse3);
//                       this.dispose();
//                       break;
//               }
//       }
//       else if (e.getActionCommand() == "Reset") {
//           courseIDTextField.setText("");
//           courseNameTextField.setText("");
//           semesterTextField.setText("");
//           categoryTextArea.setText("");
//           numberTextArea.setText("");
//           weightTextArea.setText("");
//           weightGroup.clearSelection();
//           radioButtonIndex = 0;
//           previousCourseComboBox.setEnabled(true);
//       }
//       else if (e.getSource () == previousCourseComboBox){
//           clearCourseItems();
//           categoryTextArea.setEditable(false);
//           numberTextArea.setEditable(false);
//           weightTextArea.setEditable(false);
//           radioButtonIndex = 2;
//           previousCourseComboBox.setEnabled(true);
//
////            assignmentNumberTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getNumberOfAssignments()));
////            assignmentWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfAssignments()));
////            examNumberTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getNumberOfExams()));
////            examWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfExams()));
////            projectNumberTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getNumberOfProjects()));
////            projectWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfProjects()));
////            participationNumberTextField.setText(String.valueOf(1));
////            participationWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfAttendance()));
//
//       }
//   }
//
//   private void clearCourseItems() {
//       categoryTextArea.setText("");
//       numberTextArea.setText("");
//       weightTextArea.setText("");
//   }
//
//   public void itemStateChanged(ItemEvent e){
//       if(e.getSource() == defaultWeightRadioButton){
//           clearCourseItems();
//           categoryTextArea.setEditable(true);
//           numberTextArea.setEditable(true);
//           weightTextArea.setEditable(false);
//           radioButtonIndex = 1;
//           previousCourseComboBox.setEnabled(false);
//       }
//       else if(e.getSource() == previousWeightRadioButton){
//           clearCourseItems();
//           categoryTextArea.setEditable(false);
//           numberTextArea.setEditable(false);
//           weightTextArea.setEditable(false);
//           radioButtonIndex = 2;
//           previousCourseComboBox.setEnabled(true);
//
////            assignmentNumberTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getNumberOfAssignments()));
////            assignmentWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfAssignments()));
////            examNumberTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getNumberOfExams()));
////            examWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfExams()));
////            projectNumberTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getNumberOfProjects()));
////            projectWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfProjects()));
////            participationNumberTextField.setText(String.valueOf(1));
////            participationWeightTextField.setText(String.valueOf(savedCriterion.get(useNameFindIndex(previousCourseComboBox.getSelectedItem().toString())).getWeightsOfAttendance()));
//
//       }
//       else if(e.getSource() == customizedWeightRadioButton){
//           clearCourseItems();
//           categoryTextArea.setEditable(true);
//           numberTextArea.setEditable(true);
//           weightTextArea.setEditable(true);
//           radioButtonIndex = 3;
//           previousCourseComboBox.setEnabled(false);
//       }
//   }
//
//   private static boolean isNumeric(String str){
//       Pattern pattern = Pattern.compile("[0-9]*");
//       return pattern.matcher(str).matches();
//   }
//
//   private boolean isDouble(String str) {
//       if (null == str || "".equals(str)) {
//           return false;
//       }
//       Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
//       if(pattern.matcher(str).matches()){
//           if(Double.parseDouble(str) >= 0.0 && Double.parseDouble(str) <= 1.0)
//               return true;
//           else
//               return false;
//       }
//       return false;
//   }
//
//  private boolean arrayIsNumeric(String[] arr){
//        boolean returnValue = true;
//        for (int i = 0; i < arr.length; i++){
//            if(!isNumeric(arr[i]))
//                returnValue = false;
//        }
//        return returnValue;
//    }
//
//    private boolean arrayIsDouble(String[] arr){
//        boolean returnValue = true;
//        for (int i = 0; i < arr.length; i++){
//            if(!isDouble(arr[i]))
//                returnValue = false;
//        }
//        return returnValue;
//    }
//
//  private boolean checkWeightSum(String[] arr){
//        double sum = 0.0;
//        for (int i = 0; i < arr.length; i++)
//            sum += Double.parseDouble(arr[i]);
//        if(sum == 1.0)
//            return true;
//        else
//            return false;
//    }
//
//  private boolean judgeInput() {
//        if(radioButtonIndex == 1) {
//            if(courseIDTextField.getText().equals("") ||
//                    courseNameTextField.getText().equals("") ||
//                    semesterTextField.getText().equals("") ||
//                    categoryTextArea.getText().equals("") ||
//                    numberTextArea.getText().equals("")) {
//                JOptionPane.showMessageDialog(null, "Inputted information is incomlplete!", "Info",JOptionPane.WARNING_MESSAGE);
//                return false;
//            }
//            else {
//                //For MacOS: regex is "\n"; for Windows: "\r\n".
//                if(arrayIsNumeric(numberTextArea.getText().split("\n"))){
//                    if(categoryTextArea.getText().split("\n").length != numberTextArea.getText().split("\n").length){
//                        JOptionPane.showMessageDialog(null, "The column of category and number must be the same!", "Info", JOptionPane.WARNING_MESSAGE);
//                        return false;
//                    }
//                    else
//                        return true;
//                }
//                else {
//                    JOptionPane.showMessageDialog(null, "All the number item must be intager!", "Info", JOptionPane.WARNING_MESSAGE);
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
//                    categoryTextArea.getText().equals("") ||
//                    numberTextArea.getText().equals("") ||
//                    weightTextArea.getText().equals("")) {
//                JOptionPane.showMessageDialog(null, "Inputted information is incomlplete!", "Info",JOptionPane.WARNING_MESSAGE);
//                return false;
//            }
//            else{
//                if(arrayIsNumeric(numberTextArea.getText().split("\n"))){
//                    if(arrayIsDouble(weightTextArea.getText().split("\n"))){
//                        if(checkWeightSum(weightTextArea.getText().split("\n"))) {
//                            if(categoryTextArea.getText().split("\n").length == numberTextArea.getText().split("\n").length
//                                    && numberTextArea.getText().split("\n").length == weightTextArea.getText().split("\n").length)
//                                return true;
//                            else{
//                                JOptionPane.showMessageDialog(null, "The column of category, number and weight must be the same!", "Info", JOptionPane.WARNING_MESSAGE);
//                                return false;
//                            }
//                        }
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
//                    JOptionPane.showMessageDialog(null, "All the number item must be intager!", "Info", JOptionPane.WARNING_MESSAGE);
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
