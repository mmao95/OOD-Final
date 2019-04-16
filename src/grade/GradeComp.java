package grade;

public class GradeComp {
    private String score;
    private Note gNote;
    public GradeComp() {
        score = "0";
        gNote = new Note();
    }
    public GradeComp(String s) {
        score = s;
        gNote = new Note();
    }

    public String getScore(){
        return score;
    }
    public Note getNote(){
        return gNote;
    }
}
