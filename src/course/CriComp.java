package course;

/**
 * @description: this a class to manage the weight of every assignment/exam/project
 * @author: Zhizhou Qiu
 * @date: 04-12-2019
 */
public class CriComp extends Criterion{
    private final static double DEFAULT_TOTALSCORE = 100.0;
    private final static int DEFAULT_NUMBER = 2;
    private double weights;
    private double toatalScore;

    public CriComp(double weights, double totalScore){
        this.weights = weights;
        this.toatalScore = totalScore;
    }

    /**
    * @Description: default constructor,
    * @Author: Zhizhou Qiu
    * @Date: 2019/4/12
    **/
    public CriComp() {
        this.weights = 1.0 / DEFAULT_NUMBER;
        this.toatalScore = DEFAULT_TOTALSCORE;
    }

    /**
    * @Description: getters and setters for current weight and totalscore
    * @Author: Zhizhou Qiu
    * @Date: 2019/4/12
    **/
    public double getToatalScore() {
        return toatalScore;
    }

    public void setToatalScore(double toatalScore) {
        this.toatalScore = toatalScore;
    }

    public double getWeights() {
        return weights;
    }

    public void setWeights(double weights) {
        this.weights = weights;
    }
}
