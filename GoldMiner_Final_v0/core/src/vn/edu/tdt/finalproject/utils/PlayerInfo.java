package vn.edu.tdt.finalproject.utils;

public class PlayerInfo {

    private static long currentMoney = 0;
    private static long currentTarget = 0;
    private static long currentTimePlay = 60;
    private static int currentBombNum = 3;
    private static int currentLevel = 1;
    private static Bag bag = new Bag();

    public static Bag getBag() {
        return bag;
    }

    public static void setCurrentMoney(long currentMoney) {
        PlayerInfo.currentMoney = currentMoney;
    }

    public static void setCurrentTarget(long currentTarget) {
        PlayerInfo.currentTarget = currentTarget;
    }

    public static long getCurrentTimePlay() {
        return currentTimePlay + bag.getTimeBonus();
    }

    public static void setCurrentTimePlay(long currentTimePlay) {
        PlayerInfo.currentTimePlay = currentTimePlay;
    }

    public static long getCurrentMoney(){
        return currentMoney;
    }

    public static long getCurrentTarget(){
        return currentTarget;
    }

    public static int getCurrentBombNum() {
        return currentBombNum;
    }

    public static void setCurrentBombNum(int currentBombNum) {
        PlayerInfo.currentBombNum = currentBombNum;
    }

    public static int getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevel(int currentLevel) {
        PlayerInfo.currentLevel = currentLevel;
    }
}
