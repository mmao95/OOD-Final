package personal;

public class Student {
    private String id;
    private Name name;
    private String email;

    /**
    * @Description: Constructor of Student
    * @Author: Zhizhou Qiu
    * @Date: 2019/4/12
    **/
    public Student(String id, Name name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }
    /**
    * @Description: default constructor
    * @Author: Zhizhou Qiu
    * @Date: 2019/4/12
    **/
    public Student(){}

    /**
    * @Description: getters and setters of String id, String full_name and String email
    * @Author: Zhizhou Qiu
    * @Date: 2019/4/12
    **/
    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String first, String middle, String last) {
        this.name = new Name(first, middle, last);
    }

    public String getName() {
        return name.toString();
    }
}
