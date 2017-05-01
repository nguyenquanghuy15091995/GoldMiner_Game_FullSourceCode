package vn.edu.tdt.finalproject.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;

import vn.edu.tdt.finalproject.utils.SoundConstants;

public class SoundEffect {
    public enum SoundKind{
        DISABLE,
        ONE_TIME,
        DURING,
        PAUSE,
        PLAYING
    }

    private SoundKind soundKind;

    private Sound sound;

    private long startTime;
    private long currTime;
    private float timeDelay;

    public SoundEffect(String AudioPath) {
        sound = Gdx.audio.newSound(Gdx.files.internal(AudioPath));
        soundKind = SoundKind.DISABLE;
        startTime = TimeUtils.millis();
        currTime = startTime;
        timeDelay = 0;
    }

    public SoundEffect(String AudioPath, float timeDelayTemp) {
        sound = Gdx.audio.newSound(Gdx.files.internal(AudioPath));
        soundKind = SoundKind.DISABLE;
        startTime = TimeUtils.millis();
        currTime = startTime;
        timeDelay = timeDelayTemp;
    }

    public void setAudioSound(String AudioPath){
        sound = Gdx.audio.newSound(Gdx.files.internal(AudioPath));
    }

    public void setTimeDelay(float timeDelay) {
        this.timeDelay = timeDelay;
    }

    public SoundKind getSoundKind() {
        return soundKind;
    }

    public void setSoundKind(SoundKind soundKindTemp){
        soundKind = soundKindTemp;
    }

    public void startCountTime() {
        this.startTime = TimeUtils.millis();
    }

    public void playAndroidOneTime(){
        sound.play(SoundConstants.getSoundVolume());
    }

    public void playSound() {
        if(soundKind.equals(SoundKind.DISABLE)){
            return;
        }
        if(soundKind.equals(SoundKind.ONE_TIME)){
            //stopPlay();
            sound.play(SoundConstants.getSoundVolume());
            soundKind = SoundKind.DISABLE;
        }
        if(soundKind.equals(SoundKind.DURING)) {
            currTime = TimeUtils.millis();
            if(subtractTime(currTime, startTime) >= timeDelay) {
                stopPlay();
                sound.play(SoundConstants.getSoundVolume());
                startTime = currTime;
            }
        }
    }

    public void playSoundLoopOnAndroid(){
        if(soundKind.equals(SoundKind.DURING)) {
            sound.loop(SoundConstants.getSoundVolume());
            soundKind = SoundKind.PLAYING;
        }
    }

    public void playMusic() {
        if(soundKind.equals(SoundKind.DISABLE)){
            return;
        }
        if(soundKind.equals(SoundKind.ONE_TIME)){
            stopPlay();
            sound.play(SoundConstants.getMusicVolume());
            soundKind = SoundKind.DISABLE;
        }
        if(soundKind.equals(SoundKind.DURING)) {
            currTime = TimeUtils.millis();
            if(subtractTime(currTime, startTime) >= timeDelay) {
                stopPlay();
                sound.play(SoundConstants.getMusicVolume());
                startTime = currTime;
            }
        }
    }

    public void stopPlay(){
        sound.stop();
    }

    public void pausePlay(){
        if(soundKind.equals(SoundKind.PLAYING)) {
            sound.pause();
            soundKind = SoundKind.PAUSE;
        }
    }

    public void resumePlay(){
        if(soundKind.equals(SoundKind.PAUSE)) {
            sound.resume();
            soundKind = SoundKind.PLAYING;
        }
    }

    private float subtractTime(long milisec1, long milisec2){
        return Math.abs(milisec1 - milisec2)*1.0f/1000f;
    }

    public void dispose(){
        sound.dispose();
    }
}
