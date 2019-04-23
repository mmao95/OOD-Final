package course;

import java.io.*;
import grade.Grade;
import personal.Student;
import java.util.HashMap;

public class Course implements Analysis, IO<Course>, Serializable{
    private String cname;
    private String cid;
    private String semester;
    private String cyear;
    private Criterion ccriterion;
    private HashMap<Student, Grade> cgrade;

    private static final long serialVersionUID = -4689572748727448978L;

    public Course(){
        cname = "Course01";
        cid = "000";
        semester = "Fall";
        cyear = "2019";
        ccriterion = new Criterion();
        cgrade = new HashMap<>();
    }
    public Course(String name, String id, String s, String y, Criterion c) {
        cname = name;
        cid = id;
        semester = s;
        cyear = y;
        ccriterion = c;
        cgrade = new HashMap<>();
    }
    public void enrollStudent(Student s){
        Grade temp = new Grade(ccriterion);
        cgrade.put(s,temp);
    }
    public Grade getsGrade(Student s){
        return cgrade.get(s);
    }
    public HashMap<Student, Grade> getList(){
        return cgrade;
    }
    public String[] getInfo(){
        String[] res =new String[]{cname,cid,semester,cyear};
        return res;
    }
    public Criterion getCcriterion(){
        return ccriterion;
    }
    public String[] getAnalysis(){
        String[] res = new String[4];
        double tt = 0;
        int count = 0;
        double maxd = 0;
        double mind = 100;
        for (Student key : cgrade.keySet()) {
            tt += cgrade.get(key).getTtscore();
            maxd = Math.max(maxd,cgrade.get(key).getTtscore());
            mind = Math.min(mind,cgrade.get(key).getTtscore());
            count += 1;
        }
        res[0] = Double.toString(tt/(double)count);
        res[1] = Double.toString(maxd);
        res[2] = Double.toString(mind);
        return res;
    }
    public String[] getAnalysis(String type,int index){
        String[] res = new String[4];
        double tt = 0;
        int count = 0;
        double maxd = 0;
        double mind = 100;
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
                maxd = Math.max(maxd,sc * fs);
                mind = Math.min(mind,sc * fs);
                tt += sc * fs;
                count += 1;
            }
        }
        res[0] = Double.toString(tt / (double) count);
        res[1] = Double.toString(maxd);
        res[2] = Double.toString(mind);
        return res;
    }
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

    public void calculateAll(){
        for (Student key : cgrade.keySet()) {
            calculateTotal(cgrade.get(key));
        }
    }

    public Student getStudent(String id) {
        for (Student key : cgrade.keySet()) {
            if (key.getId() == id) {
                return key;
            }
        }
        return null;
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
}
