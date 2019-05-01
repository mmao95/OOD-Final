package UI;

import course.Course;

/**
 * @Auther: Di Zhu
 * @Date: 04-21-2019 17:38
 * @Description:
 */
public class NavigationBarMethods {

    public static Course importCourse(String path) {
        Course c = new Course();
        c = c.readFromFile(path);
        return c;
    }

    public static void addNewStudent() {

    }

}
