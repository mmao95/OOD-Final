package course;

import java.io.*;
import grade.Grade;
import javafx.util.Pair;
import personal.Student;
import java.util.*;

/**
 * @Auther:Maoxuan Zhu
 * @Date:04-15-201921:20
 * @Description: this class represent a course and store grade of each students enroll in this course in a hashmap
 **/
public class Course implements Analysis,IO<Course>,Serializable{
    private String cname;
    private String cid;
    private String semester;
    private String cyear;
    private Criterion ccriterion;
    private HashMap<Student, Grade> cgrade;
    private static final long serialVersionUID = -4689572748727448978L;
    // Default constructor
    public Course(){
        cname = "Course01";
        cid = "000";
        semester = "Fall";
        cyear = "2019";
        ccriterion = new Criterion();
        cgrade = new HashMap<>();
    }
    public Course(String name, String id, String s, String y, Criterion c){
        cname = name;
        cid = id;
        semester = s;
        cyear = y;
        ccriterion = c;
        cgrade = new HashMap<>();
    }
    //enroll a student to this course
    public void enrollStudent(Student s){
        Grade temp = new Grade(ccriterion,s.getId());
        cgrade.put(s,temp);
    }
    //get student object
    public Student getStudent(String id){
        for(Student key: cgrade.keySet()){
            if(key.getId()==id){
                return key;
            }
        }
        return null;
    }
    //get the grade object of student s
    public Grade getsGrade(Student s){
        return cgrade.get(s);
    }
    //get grade list
    public HashMap<Student, Grade> getList(){
        return cgrade;
    }
    //get course information
    public String[] getInfo(){
        String[] res =new String[]{cname,cid,semester,cyear};
        return res;
    }
    //get grading criterion
    public Criterion getCcriterion(){
        return ccriterion;
    }
    //get analysis data of total score
    public String[] getAnalysis(){
        String[] res = new String[4];
        double tt = 0;
        int count = 0;
        double maxd = 0;
        double mind = 100;
        double dd;
        List<Grade> sortList = new ArrayList<>();
        for (Student key : cgrade.keySet()) {
            tt += cgrade.get(key).getTtscore();
            maxd = Math.max(maxd,cgrade.get(key).getTtscore());
            mind = Math.min(mind,cgrade.get(key).getTtscore());
            count += 1;
            sortList.add(cgrade.get(key));
        }
        Collections.sort(sortList);
        res[0] = Double.toString(tt/(double)count);
        res[1] = Double.toString(maxd);
        res[2] = Double.toString(mind);
        if(sortList.size()%2==0){
            dd = sortList.get(sortList.size()/2).getTtscore()+sortList.get(sortList.size()/2+1).getTtscore();
            dd /= 2;
        }else{
            dd = sortList.get(sortList.size()/2).getTtscore();
        }
        res[3] = Double.toString(dd);
        return res;
    }
    //get analysis data of one chosen field("a" for assignment. "e" for exam, "p" for project)
    public String[] getAnalysis(String type,int index){
        String[] res = new String[4];
        double tt = 0;
        int count = 0;
        double maxd = 0;
        double mind = 100;
        double dd;
        List<Pair<String,Double>> sg = new ArrayList<>();
        if(type=="a") {
            double fs = ccriterion.getAssignments().get(index).getToatalScore();
            double sc;
            for (Student key : cgrade.keySet()) {
                String rawsc = cgrade.get(key).getAssignment(index).getScore();
                if (rawsc.charAt(rawsc.length() - 1) == '%') {
                    sc = Double.valueOf(rawsc.substring(0, rawsc.length() - 1)) / 100;
                } else if (rawsc.charAt(0) == '-') {
                    sc = (fs - Double.valueOf(rawsc.substring(1))) / fs;
                } else {
                    sc = Double.valueOf(rawsc) / fs;
                }
                sg.add(new Pair<>(key.getId(),sc * fs));
                maxd = Math.max(maxd,sc * fs);
                mind = Math.min(mind,sc * fs);
                tt += sc * fs;
                count += 1;
            }
        }else if(type=="e") {
            double fs = ccriterion.getExams().get(index).getToatalScore();
            double sc;
            for (Student key : cgrade.keySet()) {
                String rawsc = cgrade.get(key).getExam(index).getScore();
                if (rawsc.charAt(rawsc.length() - 1) == '%') {
                    sc = Double.valueOf(rawsc.substring(0, rawsc.length() - 1)) / 100;
                } else if (rawsc.charAt(0) == '-') {
                    sc = (fs - Double.valueOf(rawsc.substring(1))) / fs;
                } else {
                    sc = Double.valueOf(rawsc) / fs;
                }
                sg.add(new Pair<>(key.getId(),sc * fs));
                maxd = Math.max(maxd,sc * fs);
                mind = Math.min(mind,sc * fs);
                tt += sc * fs;
                count += 1;
            }
        }else if(type=="p"){
            double fs = ccriterion.getProjects().get(index).getToatalScore();
            double sc;
            for (Student key : cgrade.keySet()) {
                String rawsc = cgrade.get(key).getProject(index).getScore();
                if (rawsc.charAt(rawsc.length() - 1) == '%') {
                    sc = Double.valueOf(rawsc.substring(0, rawsc.length() - 1)) / 100;
                } else if (rawsc.charAt(0) == '-') {
                    sc = (fs - Double.valueOf(rawsc.substring(1))) / fs;
                } else {
                    sc = Double.valueOf(rawsc) / fs;
                }
                sg.add(new Pair<>(key.getId(),sc * fs));
                maxd = Math.max(maxd,sc * fs);
                mind = Math.min(mind,sc * fs);
                tt += sc * fs;
                count += 1;
            }
        }
        Collections.sort(sg, Comparator.comparing(p -> p.getValue()));
        res[0] = Double.toString(tt / (double) count);
        res[1] = Double.toString(maxd);
        res[2] = Double.toString(mind);
        if(sg.size()%2==0){
            dd = sg.get(sg.size()/2).getValue()+sg.get(sg.size()/2+1).getValue();
            dd /= 2;
        }else{
            dd = sg.get(sg.size()/2).getValue();
        }
        res[3] = Double.toString(dd);
        return res;
    }
    //calculate the total score of a given grade object
    public void calculateTotal(Grade g){
        double total = 0,at = 0,et = 0,pt = 0;
        for(int i=0;i<ccriterion.getNumberOfAssignments();i++){
            String oc = g.getAssignment(i).getScore();
            CriComp temp = ccriterion.getAssignments().get(i);
            double fs = temp.getToatalScore();
            double sc;
            if(oc.charAt(oc.length()-1)=='%'){
                sc = Double.valueOf(oc.substring(0,oc.length()-1))/100;
            } else if(oc.charAt(0)=='-'){
                sc = (fs-Double.valueOf(oc.substring(1)))/fs;
            } else {
                sc = Double.valueOf(oc)/fs;
            }
            total+=sc*temp.getWeights()*100*ccriterion.getWeightsOfAssignments();
            at+=sc*temp.getWeights()*100;
        }
        for(int i=0;i<ccriterion.getNumberOfExams();i++){
            String oc = g.getExam(i).getScore();
            CriComp temp = ccriterion.getExams().get(i);
            double fs = temp.getToatalScore();
            double sc;
            if(oc.charAt(oc.length()-1)=='%'){
                sc = Double.valueOf(oc.substring(0,oc.length()-1))/100;
            } else if(oc.charAt(0)=='-'){
                sc = (fs-Double.valueOf(oc.substring(1)))/fs;
            } else {
                sc = Double.valueOf(oc)/fs;
            }
            total+=sc*temp.getWeights()*100*ccriterion.getWeightsOfExams();
            et+=sc*temp.getWeights()*100;
        }
        for(int i=0;i<ccriterion.getNumberOfProjects();i++){
            String oc = g.getProject(i).getScore();
            CriComp temp = ccriterion.getProjects().get(i);
            double fs = temp.getToatalScore();
            double sc;
            if(oc.charAt(oc.length()-1)=='%'){
                sc = Double.valueOf(oc.substring(0,oc.length()-1))/100;
            } else if(oc.charAt(0)=='-'){
                sc = (fs-Double.valueOf(oc.substring(1)))/fs;
            } else {
                sc = Double.valueOf(oc)/fs;
            }
            total+=sc*temp.getWeights()*100*ccriterion.getWeightsOfProjects();
            pt+=sc*temp.getWeights()*100;
        }
        String att = g.getAttendence().getScore();
        double attsc;
        if(att.charAt(att.length()-1)=='%'){
            attsc = Double.valueOf(att.substring(0,att.length()-1))/100;
        }else if(att.charAt(0)=='-'){
            attsc = (100-Double.valueOf(att.substring(1)))/100;
        }else{
            attsc = Double.valueOf(att)/100;
        }
        total+=attsc*ccriterion.getWeightsOfAttendance()*100;
        g.setTtscore(total);
        g.setAtt(at);
        g.setEtt(et);
        g.setptt(pt);
    }
    //calculate total score of all students
    public void calculateAll(){
        for (Student key : cgrade.keySet()) {
            calculateTotal(cgrade.get(key));
        }
    }

    @Override
    public Course readFromFile(String path) {
        Course c = null;
        try{
            FileInputStream file = new FileInputStream
                    (path);
            ObjectInputStream in = new ObjectInputStream
                    (file);

            c = (Course) in.readObject();
            in.close();
            file.close();
        } catch (IOException io){
            io.printStackTrace();
        } catch (ClassNotFoundException cl){
            cl.printStackTrace();
        }
        return c;
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

        } catch (IOException io){
            io.printStackTrace();
        }
    }

    public HashMap<Student, Grade> getCgrade() {
        return cgrade;
    }
}
