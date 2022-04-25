package Patient;

public class Patient {
    private Integer target;
    private double attribute_1;
    private double attribute_2;
    private double attribute_3;
    private double attribute_4;
    private double attribute_5;


    public Patient(int target, double attribute_1, double attribute_2, double attribute_3, double attribute_4,
                   double attribute_5) {
        this.target = target;
        this.attribute_1 = attribute_1;
        this.attribute_2 = attribute_2;
        this.attribute_3 = attribute_3;
        this.attribute_4 = attribute_4;
        this.attribute_5 = attribute_5;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public double getAttribute_1() {
        return attribute_1;
    }

    public void setAttribute_1(double attribute_1) {
        this.attribute_1 = attribute_1;
    }

    public double getAttribute_2() {
        return attribute_2;
    }

    public void setAttribute_2(double attribute_2) {
        this.attribute_2 = attribute_2;
    }

    public double getAttribute_3() {
        return attribute_3;
    }

    public void setAttribute_3(double attribute_3) {
        this.attribute_3 = attribute_3;
    }

    public double getAttribute_4() {
        return attribute_4;
    }

    public void setAttribute_4(double attribute_4) {
        this.attribute_4 = attribute_4;
    }

    public double getAttribute_5() {
        return attribute_5;
    }

    public void setAttribute_5(double attribute_5) {
        this.attribute_5 = attribute_5;
    }







}
