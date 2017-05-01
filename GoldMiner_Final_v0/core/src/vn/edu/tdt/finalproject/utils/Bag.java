package vn.edu.tdt.finalproject.utils;

public class Bag {
    private float sodaPower;
    private long timeBonus;
    public Bag(){
        sodaPower = 0;
        timeBonus = 0;
    }

    public void resetBag(){
        sodaPower = 0;
        timeBonus = 0;
    }

    public float getSodaPower() {
        return sodaPower;
    }

    public void setSodaPower(float podMoveSpeedBonus) {
        this.sodaPower = podMoveSpeedBonus;
    }

    public long getTimeBonus() {
        return timeBonus;
    }

    public void setTimeBonus(long timeBonus) {
        this.timeBonus = timeBonus;
    }
}
