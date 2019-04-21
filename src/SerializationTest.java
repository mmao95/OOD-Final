import course.Criterion;
import personal.Name;

/**
 * @description: Test Serialization
 * @author: Zhizhou Qiu
 * @create: 04-21-2019
 **/
public class SerializationTest {
    public static void main(String[] args){
        Name name = new Name("1","2","3");
        System.out.println(name);
        Criterion criterion = new Criterion();
        criterion.writeToFile("test.txt");
        Criterion testCri = criterion.readFromFile("test.txt");
        System.out.println(testCri.getNumberOfAssignments());
        System.out.println(testCri.getWeightsOfAttendance());
//        System.out.println(testCri.getAssignments().get(0).getWeights());
    }
}
