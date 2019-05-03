/**
 * @Auther:Maoxuan Zhu
 * @Date:04-15-201921:20
 * @Description:
 **/
import personal.*;
import grade.*;
import course.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        Name name1 = new Name("1","2","3");//initialize name
        Name name2 = new Name("4","5","6");
        NewCriterion cc= new NewCriterion();
        List<Category> catlist = new ArrayList<>();
        Category cat1 = new Category("Assignment",0.3,3);
        Category cat2 = new Category("Exam",0.4,2);
        Category cat3 = new Category("Project",0.3,3);
        catlist.add(cat1);
        catlist.add(cat2);
        catlist.add(cat3);
        cc.setCategories(catlist);
        Course c1 = new Course("OOD","591P","Spring","2019",cc);//initialize course with default criterion
        Student s1 = new Student("U001",name1,"001@bu.edu");//initialize student
        Student s2 = new Student("U002",name2,"002@bu.edu");
        c1.enrollStudent(s1);//enroll a student to a course
        c1.enrollStudent(s2);
        Grade g = c1.getsGrade(s1);//get the grade of a specific student
        System.out.println(c1.getCcriterion().getCategories().get(1).getName());
        g.getOne(0,0).setScore("80");
        g.getOne(0,1).setScore("80");
        g.getOne(0,2).setScore("80");
        g.getOne(1,0).setScore("80");
        g.getOne(1,1).setScore("80");
        g.getOne(2,0).setScore("80");
        g.getOne(2,1).setScore("80");
        g.getOne(2,2).setScore("80");
        c1.calculateTotal(g);
        System.out.println(g.getTtscore());

        // test reading raw data
        Course testCourse = ReadRawData.readRawData("newRawData.json");
        testCourse.calculateAll();
        String[] res = testCourse.getAnalysis();
        for (String s : res){
            System.out.println(s);
        }
        testCourse.writeToFile("testCourse.txt");
    }
}
