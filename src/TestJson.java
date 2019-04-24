import course.Course;
import course.Criterion;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

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

        } catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        } catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }


    }
}
