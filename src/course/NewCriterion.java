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


    /**
    * @Description: this is to create a NewCriterion object consisting a list of Categories
    * @Param: list of Category, String name
    * @Return: a NewCriterion object
    * @Author: Zhizhou Qiu
    * @Date: 2019/4/30
    **/
    public NewCriterion(List<Category> list, String name){
        if (list == null) categories = new ArrayList<Category>();
        else categories = list;
        this.name = name;
    }

    // default constructor
    public NewCriterion(){
        categories = new ArrayList<Category>();
        name = "";
    }

    /**
    * @Description: serialize a NewCriterion
    * @Param:
    * @Return:
    * @Author: Zhizhou Qiu
    * @Date: 2019/4/30
    **/
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
