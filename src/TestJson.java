import course.Course;
import course.Criterion;
import grade.Grade;
import grade.GradeComp;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import personal.Name;
import personal.Student;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * @description:
 * @author: Zhizhou Qiu
 * @create: 04-23-2019
 **/
public class TestJson {
    public static void main(String[] args){

        JSONParser jsonParser = new JSONParser();

        try{
            FileReader fileReader = new FileReader("raw.json");

            Object obj = jsonParser.parse(fileReader);

            JSONArray jsonArray = (JSONArray) obj;

            JSONObject jsoncourse = (JSONObject) jsonArray.get(0);

            JSONObject jsonCriterion = (JSONObject) jsonArray.get(1);

//            System.out.println(jsonCriterion.toString());

            Criterion criterion = new Criterion(
                    Double.valueOf(jsonCriterion.get("weightsOfAssignments").toString()),
                    Double.valueOf(jsonCriterion.get("weightsOfExams").toString()),
                    Double.valueOf(jsonCriterion.get("weightsOfProjects").toString()),
                    Double.valueOf(jsonCriterion.get("weightsOfAttendance").toString()),
                    Integer.valueOf(jsonCriterion.get("numberOfAssignments").toString()),
                    Integer.valueOf(jsonCriterion.get("numberOfExams").toString()),
                    Integer.valueOf(jsonCriterion.get("numberOfProjects").toString()),
                    jsonCriterion.get("name").toString()
            );

            Course course = new Course(
                    jsoncourse.get("name").toString(),
                    jsoncourse.get("id").toString(),
                    jsoncourse.get("semester").toString(),
                    jsoncourse.get("year").toString(),
                    criterion
            );

            HashMap<Student, Grade> gradeMap = course.getCgrade();

            JSONObject jsonStudent = (JSONObject) jsonArray.get(2);

            JSONArray studentArray = (JSONArray) jsonStudent.get("student");

//            System.out.println(studentArray.toString());

            List<Student> students = new ArrayList<>();

            for (Object o : studentArray){
                JSONObject jsonObject = (JSONObject) o;
                Student student = new Student(
                        jsonObject.get("id").toString(),
                        createName(jsonObject),
                        jsonObject.get("email").toString()
                );
                Grade grade = new Grade(criterion, jsonObject.get("id").toString());
                List<GradeComp> assignments = grade.getaGrade();
                List<GradeComp> projects = grade.getpGrade();
                List<GradeComp> exams = grade.geteGrade();
                for (int i = 0; i < assignments.size(); i++){
                    GradeComp gradeComp = assignments.get(i);
                    gradeComp.setScore(jsonObject.get(gradeOfAssignment+String.valueOf(i)).toString());
                    System.out.println("assignment" + String.valueOf(i)+ ": " + assignments.get(i).getScore());
                }
                for (int i = 0; i < projects.size(); i++){
                    GradeComp gradeComp = projects.get(i);
                    gradeComp.setScore(jsonObject.get(gradeOfProject+String.valueOf(i)).toString());
                    System.out.println("project" + String.valueOf(i)+": " + projects.get(i).getScore());
                }
                for (int i = 0; i < exams.size(); i++){
                    GradeComp gradeComp = exams.get(i);
                    gradeComp.setScore(jsonObject.get(gradeOfExam+String.valueOf(i)).toString());
                    System.out.println("exam" + String.valueOf(i) + ": " + exams.get(i).getScore());
                }
                GradeComp attendance = grade.getAttendence();
                attendance.setScore(jsonObject.get(gradeOfAttendance).toString());

                System.out.println(grade);
                gradeMap.put(student, grade);
            }

//            String[] res = course.getAnalysis();
//            for (String s : res){
//                System.out.println(s);
//            }

            for (Map.Entry<Student, Grade> entry : gradeMap.entrySet()){
                Student student = entry.getKey();
                Grade grade = entry.getValue();
                System.out.println(student.toString());
                System.out.println(grade.toString());
            }


        } catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }


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

    private static String gradeOfAssignment = "gradeOfAssignment";
    private static String gradeOfExam = "gradeOfExam";
    private static String gradeOfProject = "gradeOfProject";
    private static String gradeOfAttendance = "gradeOfAttendance";
}