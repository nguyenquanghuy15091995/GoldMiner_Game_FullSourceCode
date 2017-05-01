package vn.edu.tdt.finalproject.miniscreens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.actors.ActorButton;
import vn.edu.tdt.finalproject.utils.SplashDoors;
import vn.edu.tdt.finalproject.utils.TextNoBackground;

public class SettingDisplay extends Actor {
    private MiniScreenState miniScreenState;
    private Texture background;
    private ActorButton btnBackMainMenu;
    private ActorButton btnResume;
    private TextNoBackground textMenuShow;
    private TextNoBackground textResumeShow;
    private boolean isBackMainMenu;
    private boolean isResume;

    public SettingDisplay(){
        background = new Texture("images/backgrounds/minidisplay.png");
        btnBackMainMenu = new ActorButton(128f, 128f, ActorButton.ButtonTag.SETTING_MAINMENU);
        btnResume = new ActorButton(128f, 128f, ActorButton.ButtonTag.SETTING_RESUME);
        textMenuShow = new TextNoBackground(Color.WHITE, 30);
        textResumeShow = new TextNoBackground(Color.WHITE, 30);
        miniScreenState = MiniScreenState.HIDE;
        isResume = false;
        isBackMainMenu = false;
    }

    public MiniScreenState getMiniScreenState() {
        return miniScreenState;
    }

    public void showDisplay(){
        isResume = false;
        miniScreenState = MiniScreenState.SHOW;
    }

    public void hideDisplay(){
        miniScreenState = MiniScreenState.HIDE;
    }

    public void freezeDisplay(){
        miniScreenState = MiniScreenState.FREEZE;
    }

    public boolean isBackMainMenu() {
        return isBackMainMenu;
    }

    public boolean isResume() {
        return isResume;
    }

    public void setResume(boolean resume) {
        isResume = resume;
    }

    public ActorButton getBtnResume() {
        return btnResume;
    }

    public ActorButton getBtnBackMainMenu() {
        return btnBackMainMenu;
    }

    @Override
    public boolean remove() {
        background.dispose();
        btnResume.remove();
        btnBackMainMenu.remove();
        textMenuShow.dispose();
        textResumeShow.dispose();
        return super.remove();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!miniScreenState.equals(MiniScreenState.FINISH)) {

            if (miniScreenState.equals(MiniScreenState.HIDE)) {
                return;
            }
            if (miniScreenState.equals(MiniScreenState.SHOW)) {
                btnResume.setButtonState(ActorButton.ButtonState.ENABLED);
                btnBackMainMenu.setButtonState(ActorButton.ButtonState.ENABLED);
                btnBackMainMenu.updateButtonTouched();
                btnResume.updateButtonTouched();
                if (btnBackMainMenu.isTouched()) {
                    miniScreenState = MiniScreenState.FINISH;
                    isBackMainMenu = true;
                    btnResume.setButtonState(ActorButton.ButtonState.DISABLED);
                    btnBackMainMenu.setButtonState(ActorButton.ButtonState.DISABLED);
                    return;
                }
                if (btnResume.isTouched()) {
                    miniScreenState = MiniScreenState.HIDE;
                    isResume = true;
                    btnResume.setButtonState(ActorButton.ButtonState.DISABLED);
                    btnBackMainMenu.setButtonState(ActorButton.ButtonState.DISABLED);
                    return;
                }
            }
        }
        else {
            SplashDoors.closeTheSplashDoor(25f);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(miniScreenState.equals(MiniScreenState.SHOW)){
            batch.draw(background, 0, 0, Application.DESKTOP_WIDTH, Application.DESKTOP_HEIGHT);
            btnBackMainMenu.draw(batch, parentAlpha);
            btnResume.draw(batch, parentAlpha);
            textMenuShow.drawText(batch, "Main Menu", Application.DESKTOP_WIDTH/2 - 280f, Application.DESKTOP_HEIGHT/2 - 80f);
            textResumeShow.drawText(batch, "Resume", Application.DESKTOP_WIDTH/2 + 150f, Application.DESKTOP_HEIGHT/2 - 80f);
        }
    }
}
