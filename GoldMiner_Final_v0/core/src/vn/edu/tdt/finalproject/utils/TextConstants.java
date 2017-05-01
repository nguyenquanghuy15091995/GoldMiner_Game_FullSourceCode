package vn.edu.tdt.finalproject.utils;

import vn.edu.tdt.finalproject.Application;

/**
 * TextConstants have all info of ActorText.
 * FONT_POSITION: [0]-X, [1]-Y.
 * BACKGROUND_INFO: [0]-X, [1]-Y, [2]-Width, [3]-Height.
 */
public class TextConstants {

    /**
     *  Actor text: timer.
     */
    public static final int TIMER_FONT_SIZE = 28;
    public static final float[] TIMER_FONT_POSITION= {Application.DESKTOP_WIDTH - 62f, Application.DESKTOP_HEIGHT - 40f};
    public static final float[] TIMER_BACKGROUND_INFO = {Application.DESKTOP_WIDTH - 85f, Application.DESKTOP_HEIGHT - 90f, 80f, 80f};

    /**
     *  Actor text: target.
     */
    public static final int TARGET_FONT_SIZE = 17;
    public static final float[] TARGET_FONT_POSITION = {Application.DESKTOP_WIDTH - 200f, Application.DESKTOP_HEIGHT - 25f};
    public static final float[] TARGET_BACKGROUND_INFO = {Application.DESKTOP_WIDTH - 250f, Application.DESKTOP_HEIGHT - 65f, 155f, 65f};

    /**
     * Actor text: Money.
     */
    public static final int MONEY_FONT_SIZE = 17;
    public static final float[] MONEY_FONT_POSITION = {Application.DESKTOP_WIDTH - 200f, Application.DESKTOP_HEIGHT - 70f};
    public static final float[] MONEY_BACKGROUND_INFO = {Application.DESKTOP_WIDTH - 250f, Application.DESKTOP_HEIGHT - 110f, 155f, 65f};

    /**
     * Actor text: Talking.
     */
    public static final int TALKING_FONT_SIZE = 17;
    public static final float[] TALKING_FONT_POSITION = {Application.DESKTOP_WIDTH/2 - 170f, Application.DESKTOP_HEIGHT - 30f};
    public static final float[] TALKING_BACKGROUND_INFO = {Application.DESKTOP_WIDTH/2 - 180f, Application.DESKTOP_HEIGHT - 75f, 180f, 70f};


    public static final long TALKING_MAX_TIME_SHOW = 2;
    private static long TAKING_START_TIME_SHOW = 0;

    public static long getTakingStartTimeShow() {
        return TAKING_START_TIME_SHOW;
    }

    public static void setTakingStartTimeShow(long takingStartTimeShow) {
        TAKING_START_TIME_SHOW = takingStartTimeShow;
    }

    /**
     * Actor text: Level
     */
    public static final int LEVEL_FONT_SIZE = 40;
    public static final float[] LEVEL_FONT_POSITION = {30f, Application.DESKTOP_HEIGHT - 55f};
    public static final float[] LEVEL_BACKGROUND_INFO = {10f, Application.DESKTOP_HEIGHT - 100f, 90f, 90f};

    /**
     * Actor text: Bomb number
     */
    public static final int BOMB_NUM_FONT_SIZE = 22;
    public static final float[] BOMB_NUM_FONT_POSITION = {Application.DESKTOP_WIDTH/2 - 97f, Application.DESKTOP_HEIGHT - 77f};
    public static final float[] BOMB_NUM_BACKGROUND_INFO = {Application.DESKTOP_WIDTH/2 - 100f, Application.DESKTOP_HEIGHT - 100f, 60f, 30f};
}
