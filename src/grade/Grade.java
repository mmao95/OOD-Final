package grade;


import course.Criterion;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @Auther:Maoxuan Zhu
 * @Date:04-15-201921:20
 * @Description: this class represent the grade consist of 4 component(assignment, exam, project and attendance)
 **/
public class Grade implements Serializable{
    private List<GradeComp> aGrade;
    private List<GradeComp> eGrade;
    private List<GradeComp> pGrade;
    private GradeComp attendence;
    private GradeComp extraCredit;
    private double ttscore = 0;
    private double att = 0;
    private double ett = 0;
    private double ptt = 0;
    //default constructor
    public Grade(){
        aGrade = new ArrayList<GradeComp>();
        eGrade = new ArrayList<GradeComp>();
        pGrade = new ArrayList<GradeComp>();
    }
    public Grade(Criterion c){
        aGrade = new ArrayList<GradeComp>();
        eGrade = new ArrayList<GradeComp>();
        pGrade = new ArrayList<GradeComp>();
        GradeComp temp = new GradeComp();
        for(int i=0;i<c.getNumberOfAssignments();i++){
            aGrade.add(temp);
        }
        for(int i=0;i<c.getNumberOfExams();i++){
            eGrade.add(temp);
        }
        for(int i=0;i<c.getNumberOfProjects();i++){
            pGrade.add(temp);
        }
    }
    //get list of grade of assignment/exam/project
    public List<GradeComp> getaGrade(){
        return aGrade;
    }
    public List<GradeComp> geteGrade(){
        return eGrade;
    }
    public List<GradeComp> getpGrade() {
        return pGrade;
    }
    //get particular assignment/exam/project
    public GradeComp getAssignment(int ind){
        return aGrade.get(ind);
    }
    public GradeComp getExam(int ind){
        return eGrade.get(ind);
    }
    public GradeComp getProject(int ind){
        return pGrade.get(ind);
    }
    public GradeComp getAttendence(){
        return attendence;
    }
    public GradeComp getExtra(){
        return extraCredit;
    }
    //add new assignment/exam/project
    public void addAssignment(){
        GradeComp temp = new GradeComp("0");
        aGrade.add(temp);
    }
    public void addExam(){
        GradeComp temp = new GradeComp("0");
        eGrade.add(temp);
    }
    public void addProject(){
        GradeComp temp = new GradeComp("0");
        pGrade.add(temp);
    }
    //setter
    public void setAssignment(String s, int ind){
        GradeComp temp = new GradeComp(s);
        aGrade.set(ind,temp);
    }
    public void setExam(String s, int ind){
        GradeComp temp = new GradeComp(s);
        eGrade.set(ind,temp);
    }
    public void setProject(String s, int ind){
        GradeComp temp = new GradeComp(s);
        pGrade.set(ind,temp);
    }
    public void setTtscore(double a){
        ttscore = a;
    }
    public void setAtt(double a){
        att = a;
    }
    public void setEtt(double a){
        ett = a;
    }
    public void setptt(double a){
        ptt = a;
    }
    public void setAttendence(String s){
        attendence = new GradeComp(s);
    }
    public void setExtra(String s){
        extraCredit = new GradeComp(s);
    }
    //getter
    public double getTtscore(){
        return ttscore;
    }
    public double getAtt(){
        return att;
    }
    public double getEtt(){
        return ett;
    }
    public double getPtt(){
        return ptt;
    }
}
