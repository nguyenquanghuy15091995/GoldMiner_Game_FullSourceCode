package vn.edu.tdt.finalproject.utils;

/**
 * Constant volume state.
 */
public class SoundConstants {
    /**
     * handle volume variable.
     */
    private static float soundVolume = 100f;
    private static float musicVolume = 100f;

    /**
     * Get current sound volume state.
     * @return sound volume.
     */
    public static float getSoundVolume(){
        return soundVolume;
    }

    /**
     * Get current music volume state.
     * @return music volume.
     */
    public static float getMusicVolume(){
        return musicVolume;
    }

    /**
     * mute the sound volume.
     */
    public static void muteSoundVolume(){
        soundVolume = 0f;
    }

    /**
     * mute the music volume.
     */
    public static void muteMusicVolume(){
        musicVolume = 0f;
    }

    /**
     * unmute the sound volume.
     */
    public static void unmuteSoundVolume(){
        soundVolume = 100f;
    }

    /**
     * unmute the music volume.
     */
    public static void unmuteMusicVolume(){
        musicVolume = 100f;
    }
}
