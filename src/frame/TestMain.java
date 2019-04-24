package frame;

import javax.swing.*;
import java.io.IOException;
import java.util.*;
import course.*;
import grade.*;
import personal.*;

/**
 * @Auther: wangqitong
 * @Date: 04-12-2019 11:57
 * @Description:
 */
public class TestMain {
    public static void main(String[] args) throws Exception {
        Criterion c1 = new Criterion(0.4,0.2,0.2,0.1,2,2,2, "cs941");
        Criterion c2 = new Criterion();
        List<Criterion> l1 = new ArrayList<>();
        l1.add(c1);
        l1.add(c2);
        new AddCourse(l1);
    }
}
