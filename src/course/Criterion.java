package course;

import java.util.ArrayList;
import java.util.List;

/**
 * @descriptions: this is a class that when a course is initialized,
 * the number of assignments, exams etc are assigned and each weight is clarified
 */
public class Criterion {

    private final static int DEFAULT_NUMBER = 2;
    private List<CriComp> assignments;
    private List<CriComp> exams;
    private List<CriComp> projects;
    private double weightsOfAssignments;
    private double weightsOfExams;
    private double weightsOfProjects;
    private double weightsOfAttendance;
    private int numberOfAssignments;
    private int numberOfExams;
    private int numberOfProjects;


    /**
     * @Description: the constructors of Criterion, by default the params are valid
     * @Param: double: weightsOfAssignments,      int: numberOfAssignments
     *                  weightsOfExams,                 numberOfExams
     *                  weightsOfProjects,              numberOfProjects
     *                  weightsOfAttendance,
     * @Author: Zhizhou Qiu
     * @Date: 2019/4/12
     **/
    public Criterion(
            double weightsOfAssignments,
            double weightsOfExams,
            double weightsOfProjects,
            double weightsOfAttendance,
            int numberOfAssignments,
            int numberOfExams,
            int numberOfProjects
    ) {
        this.weightsOfAssignments = weightsOfAssignments;
        this.weightsOfAttendance = weightsOfAttendance;
        this.weightsOfExams = weightsOfExams;
        this.weightsOfProjects = weightsOfProjects;
        this.numberOfAssignments = numberOfAssignments;
        this.numberOfExams = numberOfExams;
        this.numberOfProjects = numberOfProjects;
        assignments = new ArrayList<>();
        exams = new ArrayList<>();
        projects = new ArrayList<>();
    }

    /**
    * @Description: default constructor
    * @Param:
    * @Return:
    * @Author: Zhizhou Qiu
    * @Date: 2019/4/12
    **/
    public Criterion() {
        createDefaultCriterion();
    }

    public int getNumberOfAssignments() {
        return numberOfAssignments;
    }

    public int getNumberOfExams() {
        return numberOfExams;
    }

    public int getNumberOfProjects() {
        return numberOfProjects;
    }

    public List<CriComp> getAssignments() {
        return assignments;
    }

    public List<CriComp> getExams() {
        return exams;
    }

    public List<CriComp> getProjects() {
        return projects;
    }

    public double getWeightsOfAssignments() {
        return weightsOfAssignments;
    }

    public double getWeightsOfExams() {
        return weightsOfExams;
    }

    public double getWeightsOfProjects() {
        return weightsOfProjects;
    }

    public double getWeightsOfAttendance() {
        return weightsOfAttendance;
    }

    /**
     * @Description: create default criterion, for each category the default number is 2, and the weight is 0.25
     * @Param:
     * @Return:
     * @Author: Zhizhou Qiu
     * @Date: 2019/4/12
     **/
    private void createDefaultCriterion() {
        this.assignments = new ArrayList<CriComp>();
        this.projects = new ArrayList<CriComp>();
        this.exams = new ArrayList<CriComp>();
        this.weightsOfProjects = 0.25;
        this.weightsOfExams = 0.25;
        this.weightsOfAttendance = 0.25;
        this.weightsOfAssignments = 0.25;
        for (int i = 0; i < DEFAULT_NUMBER; i++) {
            this.assignments.add(new CriComp());
            this.projects.add(new CriComp());
            this.exams.add(new CriComp());
        }
    }
}
