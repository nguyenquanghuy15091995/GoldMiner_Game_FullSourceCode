package vn.edu.tdt.finalproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.actors.ActorButton;
import vn.edu.tdt.finalproject.sounds.MusicEffect;
import vn.edu.tdt.finalproject.sounds.SoundEffect;
import vn.edu.tdt.finalproject.utils.PlayerInfo;
import vn.edu.tdt.finalproject.utils.ScreenConstants;
import vn.edu.tdt.finalproject.utils.SplashDoors;

public class MainMenuScreen extends AbstractScreen {

    private Texture background;
    private ActorButton btnPlayGame;
    private MusicEffect musicMenu;
    private SoundEffect soundCloseDoor;
    private boolean isCloseDoor;
    private long startTime;

    public MainMenuScreen(Application app) {
        super(app);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void show() {
        SplashDoors.setDoorOpen();
        createCamera();
        createStageGame();
        app.batch.setProjectionMatrix(getCamera().combined);
        app.shapeBatch.setProjectionMatrix(getCamera().combined);
        background = new Texture("images/backgrounds/mainmenu.jpg");

        soundCloseDoor = new SoundEffect("sounds/closedoor.ogg");
        soundCloseDoor.setSoundKind(SoundEffect.SoundKind.ONE_TIME);
        isCloseDoor = false;


        musicMenu = new MusicEffect("sounds/mainmenu_2.ogg");
        musicMenu.setMusicKind(MusicEffect.MusicKind.DURING);
        musicMenu.playMusicLoopOnAndroid();

        btnPlayGame = new ActorButton(128f, 128f, ActorButton.ButtonTag.MAINMENU_PLAY);

        getStageGame().addActor(btnPlayGame);
        Gdx.input.setInputProcessor(getStageGame());

        PlayerInfo.setCurrentBombNum(3);
        PlayerInfo.setCurrentMoney(0);
        PlayerInfo.setCurrentTarget(0);
        PlayerInfo.setCurrentLevel(1);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        app.batch.begin();
        app.batch.draw(background,0,0,Application.DESKTOP_WIDTH,Application.DESKTOP_HEIGHT);
        app.batch.end();

        getStageGame().act();
        btnPlayGame.updateButtonTouched();
        if(btnPlayGame.isTouched()){
            updateMenuSound();
            startTime = TimeUtils.millis()/1000;
            isCloseDoor = true;
        }
        getStageGame().draw();

        app.batch.begin();
        if(isCloseDoor) {
            SplashDoors.closeTheSplashDoor(10f);
            SplashDoors.drawDoor(app.batch);
        }
        app.batch.end();

        if(SplashDoors.checkDoorClose()){
            if(TimeUtils.millis()/1000 - startTime >= 1) {
                app.gsm.setScreen(1);
                dispose();
            }
        }
    }

    private void updateMenuSound(){
        musicMenu.pausePlay();
        musicMenu.stopPlay();
        soundCloseDoor.playSound();
    }

    @Override
    public void pause() {
        musicMenu.pausePlay();
    }

    @Override
    public void resume() {
        musicMenu.resumePlay();
    }

    @Override
    public void dispose() {
        if(btnPlayGame != null) {
            btnPlayGame.remove();
        }
        background.dispose();
        musicMenu.dispose();
        soundCloseDoor.dispose();
        super.dispose();
    }

    @Override
    public void hide() {

    }
}
