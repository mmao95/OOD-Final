package course;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: This is the class to maintain the weight of current category
 * @author: Zhizhou Qiu
 * @create: 04-30-2019
 **/
public class Category implements Serializable {
    private String name;
    private double weight;
    private List<CriComp> criComps;
    private  static final int DEFAULT_NUMBER = 2;

    /**
    * @Description: this is the constructor to create a Category according to name, weight and number of tasks
    * @Param: String name, double weight, int number
    * @Return: a Category object
    * @Author: Zhizhou Qiu
    * @Date: 2019/4/30
    **/
    public Category(String name, double weight, int number){
        this.name = name;
        this.weight = weight;
        criComps = new ArrayList<CriComp>();
        for (int i = 0; i < number; i++){
            criComps.add(new CriComp(1.0/number, 100.0));
        }
    }

    // default constructor, with 0 weight of current category, 2 tasks in this category with 0.5 weight each
    public Category(){
        name = "";
        weight = 0;
        criComps = new ArrayList<CriComp>();
        for (int i = 0; i < DEFAULT_NUMBER; i++){
            criComps.add(new CriComp());
        }
    }

    @Override
    public String toString() {
        return "name: " + name +
                ", weight: " + weight +
                ", tasks: " + criComps + "\n";
    }

    public int getNumberOfTasks(){
        if (criComps == null) return  -1;
        return criComps.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<CriComp> getCriComps() {
        return criComps;
    }

    public void setCriComps(List<CriComp> criComps) {
        this.criComps = criComps;
    }
}
