//package UI.Listener;
//
//import UI.ColumnGroup;
//import UI.GroupableTableHeader;
//import course.Course;
//import personal.Student;
//
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.List;
//
//import javax.swing.*;
//import javax.swing.table.TableColumn;
//
///**
// * @Author Di Zhu
// * @Date 9:53 53/17/2019
// * @Description //TODO
// **/
//public class TableMouseListener extends MouseAdapter {
//
//    private JTable table;
//    private JTextField weightingField;
//    private JTextArea commentArea;
//    private Course currentCourse;
//
//    public TableMouseListener(JTable table, JTextField weightingField,
//                              JTextArea commentArea, Course currentCourse) {
//        this.table = table;
//        this.weightingField = weightingField;
//        this.commentArea = commentArea;
//        this.currentCourse = currentCourse;
//    }
//
//    @Override
//    public void mousePressed(MouseEvent event) {
//        // selects the row at which point the mouse is clicked
//        if (event.getClickCount() == 2) {
//            weightingField.setText("Double Clicked!");
//        } else if (event.getClickCount() == 1) {
//
////            commentArea.setText("");
////            //Get note
////            int selectedColumn = table.getSelectedColumn();
////            String stuId = table.getValueAt(table.getSelectedRow(), 1).toString();
////
////            GroupableTableHeader header = (GroupableTableHeader) table.getTableHeader();
////
////            TableColumn tc = table.getColumnModel().getColumn(selectedColumn);
////            List<ColumnGroup> columnGroups = header.getColumnGroups(tc);
////            String comment = "";
////
////            if (columnGroups.size() != 0) {
////                String category = columnGroups.get(0).getHeaderValue();
////                int subCategory = Integer.parseInt(table.getColumnName(selectedColumn));
////                Student student = currentCourse.getStudent(stuId);
////                if (category.equals("Assignments")) {
////                    comment = currentCourse.getsGrade(student).
////                            getAssignment(subCategory - 1).getNote().getNote();
////                    weightingField.setText(currentCourse.getCcriterion().toString("a"));
////                } else if (category.equals("Exams")) {
////                    comment = currentCourse.getsGrade(student).
////                            getExam(subCategory -1).getNote().getNote();
////                    weightingField.setText(currentCourse.getCcriterion().toString("e"));
////                } else {
////                    comment = currentCourse.getsGrade(student).
////                            getProject(subCategory - 1).getNote().getNote();
////                    weightingField.setText(currentCourse.getCcriterion().toString("p"));
////                }
////                commentArea.setText(comment);
////            } else {
////                if (selectedColumn == table.getColumnCount() - 1)
////                    weightingField.setText(currentCourse.getCcriterion().toString());
////                else
////                    weightingField.setText("");
////                commentArea.setText("No comment");
////            }
//        }
//    }
//}