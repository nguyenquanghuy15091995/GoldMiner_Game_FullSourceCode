package vn.edu.tdt.finalproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.actors.ActorButton;
import vn.edu.tdt.finalproject.sounds.MusicEffect;
import vn.edu.tdt.finalproject.sounds.SoundEffect;
import vn.edu.tdt.finalproject.utils.ScreenConstants;
import vn.edu.tdt.finalproject.utils.SplashDoors;

public class LoseScreen extends AbstractScreen{

    private Texture background;
    private MusicEffect loseSound;
    private ActorButton btnBackMenu;
    private boolean isScreenFinish;
    private long startTime;
    private SoundEffect finishSound;


    public LoseScreen(Application app) {
        super(app);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void show() {
        SplashDoors.setDoorClose();
        createCamera();
        createStageGame();
        app.batch.setProjectionMatrix(getCamera().combined);
        app.shapeBatch.setProjectionMatrix(getCamera().combined);
        background = new Texture("images/backgrounds/losebackground.png");
        isScreenFinish = false;

        loseSound = new MusicEffect("sounds/youlose.ogg");
        loseSound.setMusicKind(MusicEffect.MusicKind.ONE_TIME);
        loseSound.playMusic();

        finishSound = new SoundEffect("sounds/closedoor.ogg");
        finishSound.setSoundKind(SoundEffect.SoundKind.ONE_TIME);

        btnBackMenu = new ActorButton(120f, 120f, ActorButton.ButtonTag.LOSE_BACK_MAINMENU);

        getStageGame().addActor(btnBackMenu);

        Gdx.input.setInputProcessor(getStageGame());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        app.batch.begin();
        app.batch.draw(background,0,0,Application.DESKTOP_WIDTH,Application.DESKTOP_HEIGHT);
        SplashDoors.drawDoor(app.batch);
        app.batch.end();

        if(!isScreenFinish) {

            if (SplashDoors.checkDoorOpen()) {
                getStageGame().act();
                updateButtonBackMenuClick();
                getStageGame().draw();
            } else {
                SplashDoors.openTheSplashDoor(5f);
            }
        }
        if(isScreenFinish){
            SplashDoors.closeTheSplashDoor(10f);
            finishSound.playSound();
            if(TimeUtils.millis()/1000 - startTime >= 2) {
                app.gsm.setScreen(ScreenConstants.MAINMENU_SCREEN);
                dispose();
            }
        }

    }

    private void updateButtonBackMenuClick(){
        btnBackMenu.updateButtonTouched();
        if(btnBackMenu.isTouched()){
            isScreenFinish = true;
            startTime = TimeUtils.millis()/1000;
        }
    }

    @Override
    public void pause() {
        loseSound.pausePlay();
    }

    @Override
    public void resume() {
        loseSound.resumePlay();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        btnBackMenu.remove();
        loseSound.dispose();
        finishSound.dispose();
        super.dispose();
    }
}
