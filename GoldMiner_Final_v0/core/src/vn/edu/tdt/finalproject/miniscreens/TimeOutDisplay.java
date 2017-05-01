package vn.edu.tdt.finalproject.miniscreens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.actors.ActorButton;
import vn.edu.tdt.finalproject.sounds.SoundEffect;
import vn.edu.tdt.finalproject.utils.SplashDoors;
import vn.edu.tdt.finalproject.utils.TextNoBackground;

public class TimeOutDisplay extends Actor {
    private MiniScreenState miniScreenState;
    private Texture background;
    private TextNoBackground timeOutText;
    private Texture timeOutSign;
    private SoundEffect soundCloseDoor;
    private SoundEffect soundTimeOut;
    private long startTime;
    private ActorButton btnChangeScreen;

    public TimeOutDisplay(){
        background = new Texture("images/backgrounds/minidisplay.png");
        timeOutSign = new Texture("images/textureobjects/timeout.png");
        timeOutText = new TextNoBackground(Color.GOLD, 50);
        miniScreenState = MiniScreenState.HIDE;
        soundCloseDoor = new SoundEffect("sounds/closedoor.ogg");
        soundCloseDoor.setSoundKind(SoundEffect.SoundKind.ONE_TIME);
        soundTimeOut = new SoundEffect("sounds/timeout.ogg");
        soundTimeOut.setSoundKind(SoundEffect.SoundKind.ONE_TIME);
        startTime = 0;
        btnChangeScreen = new ActorButton(120f, 120f, ActorButton.ButtonTag.TIMEOUT_CHANGE_SCREEN);
    }

    public void showDisplay(){
        btnChangeScreen.setButtonState(ActorButton.ButtonState.ENABLED);
        miniScreenState = MiniScreenState.SHOW;
    }

    public MiniScreenState getMiniScreenState() {
        return miniScreenState;
    }

    @Override
    public boolean remove() {
        background.dispose();
        timeOutText.dispose();
        timeOutSign.dispose();
        soundTimeOut.dispose();
        return super.remove();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(miniScreenState.equals(MiniScreenState.SHOW)){
            soundTimeOut.playSound();
            btnChangeScreen.updateButtonTouched();
            if(btnChangeScreen.isTouched()){
                miniScreenState = MiniScreenState.FREEZE;
                startTime = TimeUtils.millis()/1000;
            }
        }
        if(miniScreenState.equals(MiniScreenState.FREEZE)){
            soundCloseDoor.playSound();
            SplashDoors.closeTheSplashDoor(10f);
            if(SplashDoors.checkDoorClose() && TimeUtils.millis()/1000 - startTime >= 2){
                miniScreenState = MiniScreenState.FINISH;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(miniScreenState.equals(MiniScreenState.SHOW)){
            batch.draw(background, 0, 0, Application.DESKTOP_WIDTH, Application.DESKTOP_HEIGHT);
            batch.draw(timeOutSign, Application.DESKTOP_WIDTH / 2 - 125f, Application.DESKTOP_HEIGHT/2 - 50f, 250f, 250f);
            timeOutText.drawText(batch, "Finish Level!", Application.DESKTOP_WIDTH / 2 - 190f, Application.DESKTOP_HEIGHT/2 - 50f);
            btnChangeScreen.draw(batch, parentAlpha);
        }
    }
}
