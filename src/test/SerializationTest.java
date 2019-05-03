package test;

import course.Category;
import course.Course;
import course.Criterion;
import course.NewCriterion;
import personal.Name;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: Test Serialization
 * @author: Zhizhou Qiu
 * @create: 04-21-2019
 **/
public class SerializationTest {
    public static void main(String[] args){

        // write to file
        Category assignment = new Category("Assignment", 0.4,5);
        Category exam = new Category("Exam", 0.3, 2);
        Category project = new Category("Project", 0.3, 1);
//        Category attendance = new Category("Attendance", 0.1, 1);
        List<Category> categories = new ArrayList<>();
        categories.add(assignment);
        categories.add(exam);
        categories.add(project);
//        categories.add(attendance);
        NewCriterion criterion = new NewCriterion(categories, "cs_criterion");
//        Course course = new Course("mycourse", "123","fall","2019", criterion);
//        course.writeToFile("mycourse.txt");
        criterion.writeToFile("criterion4");

        // read from file
//        NewCriterion newCriterion = criterion.readFromFile("criterion");
//        System.out.println(newCriterion.toString());
//        Course newCourse = course.readFromFile("mycourse.txt");
//        System.out.println(newCourse.toString());
    }
}
