package course;

import grade.Grade;
import personal.Student;
import java.util.HashMap;

public class Course implements Analysis{
    private String cname;
    private String cid;
    private String semester;
    private String cyear;
    private Criterion ccriterion;
    private HashMap<Student, Grade> cgrade;
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
    public void calculateTotal(Grade g){
        double total = 0;
        for(int i=0;i<ccriterion.getNumberOfAssignments();i++){
            String oc = g.getAssignment(i).getScore();
            CriComp temp = ccriterion.getAssignments().get(i);
            double fs = temp.getToatalScore();
            double sc;
            if(oc.charAt(oc.length()-1)=='%'){
                sc = Double.valueOf(oc.substring(0,oc.length()-2))/100;
            } else if(oc.charAt(0)=='-'){
                sc = (fs-Double.valueOf(oc.substring(1,oc.length()-1)))/fs;
            } else {
                sc = Double.valueOf(oc)/fs;
            }
            total+=sc*temp.getWeights()*100*ccriterion.getWeightsOfAssignments();
        }
        for(int i=0;i<ccriterion.getNumberOfExams();i++){
            String oc = g.getExam(i).getScore();
            CriComp temp = ccriterion.getExams().get(i);
            double fs = temp.getToatalScore();
            double sc;
            if(oc.charAt(oc.length()-1)=='%'){
                sc = Double.valueOf(oc.substring(0,oc.length()-2))/100;
            } else if(oc.charAt(0)=='-'){
                sc = (fs-Double.valueOf(oc.substring(1,oc.length()-1)))/fs;
            } else {
                sc = Double.valueOf(oc)/fs;
            }
            total+=sc*temp.getWeights()*100*ccriterion.getWeightsOfExams();
        }
        for(int i=0;i<ccriterion.getNumberOfProjects();i++){
            String oc = g.getProject(i).getScore();
            CriComp temp = ccriterion.getProjects().get(i);
            double fs = temp.getToatalScore();
            double sc;
            if(oc.charAt(oc.length()-1)=='%'){
                sc = Double.valueOf(oc.substring(0,oc.length()-2))/100;
            } else if(oc.charAt(0)=='-'){
                sc = (fs-Double.valueOf(oc.substring(1,oc.length()-1)))/fs;
            } else {
                sc = Double.valueOf(oc)/fs;
            }
            total+=sc*temp.getWeights()*100*ccriterion.getWeightsOfProjects();
        }
        String att = g.getAttendence().getScore();
        double attsc;
        if(att.charAt(att.length()-1)=='%'){
            attsc = Double.valueOf(att.substring(0,att.length()-2))/100;
        }else if(att.charAt(0)=='-'){
            attsc = (100-Double.valueOf(att.substring(1,att.length()-1)))/100;
        }else{
            attsc = Double.valueOf(att)/100;
        }
        total+=attsc*ccriterion.getWeightsOfAttendance()*100;
        g.setTtscore(total);
    }
    public void calculateAll(){
        for (Student key : cgrade.keySet()) {
            calculateTotal(cgrade.get(key));
        }
    }
}

