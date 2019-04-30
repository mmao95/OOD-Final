/**
 * @Auther:Maoxuan Zhu
 * @Date:04-15-201921:20
 * @Description:
 **/
import personal.*;
import grade.*;
import course.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {

//        Name name1 = new Name("1","2","3");//initialize name
//        Name name2 = new Name("4","5","6");
//        Criterion cc= new Criterion();
//
//        Course c1 = new Course("OOD","591P","Spring","2019",cc);//initialize course with default criterion
//        Student s1 = new Student("U001",name1,"001@bu.edu");//initialize student
//        Student s2 = new Student("U002",name2,"002@bu.edu");
//        c1.enrollStudent(s1);//enroll a student to a course
//        c1.enrollStudent(s2);
//        Grade g = c1.getsGrade(s1);//get the grade of a specific student
//        //set grade of student s1 for each component
//        // Updated upstream
//        g.setAttendence("-20");
//        g.setAssignment("80",0);
//        g.setAssignment("70",1);
//        g.setExam("80",0);
//        g.setExam("70",1);
//        g.setProject("80",0);
//        g.setProject("70",1);
//
//        Grade g2 = c1.getsGrade(s2);
//        //set grade of student s1 for each component
//        g2.setAttendence("90");
//        g2.setAssignment("80",0);
//        g2.setAssignment("70",1);
//        g2.setExam("90",0);
//        g2.setExam("60",1);
//        g2.setProject("80",0);
//        g2.setProject("70",1);
//        c1.calculateAll();//calculate total grade of one course
//        System.out.println(c1.getsGrade(s1).getTtscore());//get total score
//        System.out.println(c1.getsGrade(s1).getAtt());//get assignment total score
//        System.out.println(c1.getsGrade(s2).getEtt());//get exam total score
//        System.out.println(c1.getsGrade(s2).getPtt());//get project total score
//        System.out.println(c1.getAnalysis()[0]);//print analysis [0] is ave [1] is max [2] is min
//
//        // test add an assignment:
//        cc.addAssignment();
//        for (Student student : c1.getList().keySet()){
//            Grade grade = c1.getsGrade(student);
//            grade.addAssignment();
//        }
//        c1.calculateAll();
//        System.out.println("new mean score after add new assignment: " + c1.getAnalysis("a",2)[0]);
//

//        c1.writeToFile("a.txt");

//        Course c = new Course();
//        c1 = c.readFromFile("a.txt");
//        System.out.println(c1.getInfo()[1]);
//        c1.calculateAll();
//        System.out.println(c1.getAnalysis()[0]);




        /**
         * test method readRawdata
         * @Param: String path
         */

//        Course course = ReadRawData.readRawData(args[0]);
//        course.calculateAll();
//        String[] arr = course.getAnalysis("a",0);
//        for (String s : arr){
//            System.out.println(s);
//        }

        /**
         * test NewCriterion toString()
         */
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Assignment", 0.5,3));
        categories.add(new Category("Exam", 0.5,2));
        NewCriterion newCriterion = new NewCriterion(categories, "my criterion");
        System.out.println(newCriterion.toString());
    }
}
