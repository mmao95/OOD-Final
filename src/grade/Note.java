package grade;

public class Note {
    private String content;
    public Note() {
        content = "";
    }
    public Note(String s) {
        content = s;
    }
    public void setNote(String s) {
        content = s;
    }
    public String getNote() {
        return content;
    }
}
