package vn.edu.tdt.finalproject.sounds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.TimeUtils;

import vn.edu.tdt.finalproject.utils.SoundConstants;

public class MusicEffect {
    public enum MusicKind{
        DISABLE,
        ONE_TIME,
        DURING
    }

    private MusicKind musicKind;

    private Music music;

    private long startTime;
    private long currTime;
    private float timeDelay;

    public MusicEffect(String AudioPath){
        music = Gdx.audio.newMusic(Gdx.files.internal(AudioPath));
        musicKind = MusicKind.DISABLE;
        startTime = TimeUtils.millis();
        currTime = startTime;
        timeDelay = 0;
    }

    public void setMusicSound(String AudioPath){
        music = Gdx.audio.newMusic(Gdx.files.internal(AudioPath));
    }

    public MusicKind getMusicKind() {
        return musicKind;
    }

    public void setMusicKind(MusicKind musicKind) {
        this.musicKind = musicKind;
    }

    public void playMusicLoopOnAndroid(){
        if(musicKind.equals(MusicKind.DURING)) {
            music.setLooping(true);
            music.play();
            musicKind = MusicKind.DISABLE;
        }
    }

    public void playMusic() {
        if(musicKind.equals(MusicKind.DISABLE)){
            return;
        }
        if(musicKind.equals(MusicKind.ONE_TIME)){
            stopPlay();
            music.setVolume(SoundConstants.getMusicVolume());
            music.play();
            musicKind = MusicKind.DISABLE;
        }
        if(musicKind.equals(MusicKind.DURING)) {
            currTime = TimeUtils.millis();
            if(subtractTime(currTime, startTime) >= timeDelay) {
                stopPlay();
                music.setVolume(SoundConstants.getMusicVolume());
                music.play();
                startTime = currTime;
            }
        }
    }

    private float subtractTime(long milisec1, long milisec2){
        return Math.abs(milisec1 - milisec2)*1.0f/1000f;
    }

    public void pausePlay(){
        music.pause();
    }

    public void resumePlay(){
        music.play();
    }

    public void stopPlay(){
        music.stop();
    }

    public void dispose(){
        music.dispose();
    }
}
