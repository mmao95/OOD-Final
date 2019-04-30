package course;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: This a the new criterion that allows adding new category
 * @author: Zhizhou Qiu
 * @create: 04-30-2019
 **/
public class NewCriterion implements IO<NewCriterion>, Serializable {
    private List<Category> categories;
    private String name;


    public NewCriterion(List<Category> list, String name){
        if (list == null) categories = new ArrayList<Category>();
        else categories = list;
        this.name = name;
    }

    public NewCriterion(){
        categories = new ArrayList<Category>();
        name = "";
    }

    @Override
    public NewCriterion readFromFile(String path) {
        NewCriterion newCriterion = null;
        try{
            FileInputStream file = new FileInputStream
                    (path);
            ObjectInputStream in = new ObjectInputStream
                    (file);

            newCriterion = (NewCriterion) in.readObject();
            in.close();
            file.close();
        } catch (IOException io){
            io.printStackTrace();
        } catch (ClassNotFoundException cl){
            cl.printStackTrace();
        }
        return newCriterion;
    }

    @Override
    public void writeToFile(String path) {
        try{
            FileOutputStream file = new FileOutputStream
                    (path);
            ObjectOutputStream out = new ObjectOutputStream
                    (file);
            out.writeObject(this);
            out.close();
            file.close();
//            System.out.println("object has been serialized\n + Data before serialization.");

        } catch (IOException io){
            io.printStackTrace();
        }
    }
}
