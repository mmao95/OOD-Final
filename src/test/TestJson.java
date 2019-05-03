package test;

import course.*;
import grade.Grade;
import grade.GradeComp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import personal.Name;
import personal.Student;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @description: this is used to test reading raw json data
 * @author: Zhizhou Qiu
 * @create: 04-23-2019
 **/
public class TestJson {
    public static void main(String[] args){

//        JSONParser jsonParser = new JSONParser();
//
//        try{
//            FileReader fileReader = new FileReader(new File(args[0]));
//
//            Object obj = jsonParser.parse(fileReader);
//
//            JSONArray jsonArray = (JSONArray) obj;
//
//            JSONObject jsoncourse = (JSONObject) jsonArray.get(0);
//
//            JSONObject jsonCriterion = (JSONObject) jsonArray.get(1);
//
////            System.out.println(jsonCriterion.toString());
//
//            List<Category> categories = new ArrayList<>();
//
//            JSONArray catergoryArr = (JSONArray) jsonCriterion.get("category");
//
//            for (Object o : catergoryArr){
//                JSONObject category = (JSONObject) o;
//                categories.add(new Category(category.get("name").toString(),
//                        Double.valueOf(category.get("weight").toString()),
//                        Integer.valueOf(category.get("number").toString())));
//            }
//
//            NewCriterion criterion = new NewCriterion(
//                    categories, jsonCriterion.get("name").toString()
//            );
//
////            System.out.println(criterion);
//
//            Course course = new Course(
//                    jsoncourse.get("name").toString(),
//                    jsoncourse.get("id").toString(),
//                    jsoncourse.get("semester").toString(),
//                    jsoncourse.get("year").toString(),
//                    criterion
//            );
//
//            HashMap<Student, Grade> gradeMap = course.getList();
//
//            JSONObject jsonStudent = (JSONObject) jsonArray.get(2);
//
//            JSONArray studentArray = (JSONArray) jsonStudent.get("student");
//
////            System.out.println(studentArray.toString());
//
//            // set grade of every student
//            for (Object o : studentArray){
//                JSONObject jsonObject = (JSONObject) o;
//
//                // create a Student Object
//                Student student = new Student(
//                        jsonObject.get("id").toString(),
//                        createName(jsonObject),
//                        jsonObject.get("email").toString()
//                );
//                course.enrollStudent(student);
//                // get the mapping Grade object
//                Grade g = course.getsGrade(student);
//
//                // for each student, set score of each category
//                for (int i = 0; i < categories.size(); i++){
//                    Category category = categories.get(i);
//                    String name = category.getName();
//
//                    // set score for each grade component in each category
//                    List<GradeComp> gradeComps = g.getCategory(i);
//                    for (int j = 0; j < gradeComps.size(); j++){
//                        GradeComp gradeComp = gradeComps.get(j);
//                        String key = prefix+name+(j+1);
//                        System.out.println(key);
//                        String score = jsonObject.get(key).toString();
//                        System.out.println("score: " + score);
//                        gradeComp.setScore(score);
//                    }
//                }
//                System.out.println("===================");
//
//                gradeMap.put(student,g);
//
//            }
//
//            course.calculateAll();
//            String[] res = course.getAnalysis();
//            for (String s : res){
//                System.out.println(s);
//            }
//
//
//        } catch (IOException e){
//            e.printStackTrace();
//        } catch (ParseException e){
//            e.printStackTrace();
//        } catch (IndexOutOfBoundsException e){
//            e.printStackTrace();
//        }



        Course course = ReadRawData.readRawData("newRawData.json");
        course.calculateAll();
        String[] arr = course.getAnalysis();
        for (String s : arr){
            System.out.println(s);
        }
        Student s = course.getStudent("128");
        System.out.println(s.getType());
    }


    private static Name createName(JSONObject jsonObject){
        String name = jsonObject.get("name").toString();
        String[] arr = name.split(" ");
        if (arr.length == 0) return new Name(null, null, null);
        else if (arr.length == 1) return new Name(arr[0],null,null);
        else if (arr.length == 2) return new Name(arr[0],null, arr[1]);
        else if (arr.length == 3) return new Name(arr[0], arr[1], arr[2]);
        else return new Name(name, null,null);
    }


    private static final String prefix = "gradeOf";
}
