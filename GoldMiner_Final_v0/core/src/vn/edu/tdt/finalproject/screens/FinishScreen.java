package vn.edu.tdt.finalproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.actors.ActorButton;
import vn.edu.tdt.finalproject.actors.ActorImage;
import vn.edu.tdt.finalproject.actors.ActorRollingImage;
import vn.edu.tdt.finalproject.sounds.MusicEffect;
import vn.edu.tdt.finalproject.utils.GameMethods;
import vn.edu.tdt.finalproject.utils.SplashDoors;
import vn.edu.tdt.finalproject.utils.TextNoBackground;

public class FinishScreen extends AbstractScreen {

    private Texture background;
    private ActorImage acTheEndText;
    private ArrayList<ActorImage> lstCD;
    private ActorRollingImage soundTrack;
    private MusicEffect finishMusic;
    private TextNoBackground thankText;
    private boolean isFinishScreen;
    private long startTime;
    private ActorButton btnBackMainMenu;

    public FinishScreen(Application app) {
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
        isFinishScreen = false;


        background= new Texture("images/backgrounds/desertbackground.jpg");

        acTheEndText = new ActorImage("images/textureobjects/theendtext.png", Application.DESKTOP_WIDTH/2 - 250f, Application.DESKTOP_HEIGHT - 300f, 500f, 250f);

        thankText = new TextNoBackground(Color.BLACK, 40);

        soundTrack = new ActorRollingImage("images/textureobjects/soundtrack.png", 0.7f, 120f, 50f);
        soundTrack.startRolling();

        finishMusic = new MusicEffect("sounds/dustandlight.ogg");
        finishMusic.setMusicKind(MusicEffect.MusicKind.DURING);
        finishMusic.playMusicLoopOnAndroid();

        lstCD = new ArrayList<ActorImage>();
        lstCD.add(new ActorImage("images/textureobjects/cd1.png", 30f, 200f, 100f, 100f));
        lstCD.add(new ActorImage("images/textureobjects/cd2.png", 30f, 310f, 100f, 100f));
        lstCD.add(new ActorImage("images/textureobjects/cd3.png", 30f, 410f, 100f, 100f));
        lstCD.add(new ActorImage("images/textureobjects/cd3.png", Application.DESKTOP_WIDTH - 130f, 200f, 100f, 100f));
        lstCD.add(new ActorImage("images/textureobjects/cd2.png", Application.DESKTOP_WIDTH - 130f, 300f, 100f, 100f));
        lstCD.add(new ActorImage("images/textureobjects/cd1.png", Application.DESKTOP_WIDTH - 130f, 410f, 100f, 100f));

        for(int i = 0; i < lstCD.size(); i++){
            if(i % 2 == 0){
                lstCD.get(i).setRotationNormal(0.5f);
            }
            else {
                lstCD.get(i).setRotationNormal(-0.5f);
            }
        }

        btnBackMainMenu = new ActorButton(120f, 120f, ActorButton.ButtonTag.FINISH_BACK_MENU);

        getStageGame().addActor(acTheEndText);
        getStageGame().addActor(soundTrack);
        for(ActorImage acImg : lstCD){
            if(acImg != null){
                getStageGame().addActor(acImg);
            }
        }
        getStageGame().addActor(btnBackMainMenu);

        Gdx.input.setInputProcessor(getStageGame());

        startTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        app.batch.begin();
        app.batch.draw(background, 0, 0, Application.DESKTOP_WIDTH, Application.DESKTOP_HEIGHT);
        thankText.drawText(app.batch, "THANKS FOR PLAYING!", Application.DESKTOP_WIDTH/2 - 220f, 80f);
        app.batch.end();

        if(!isFinishScreen){
            if(TimeUtils.millis() - startTime >= 500) {
                SplashDoors.openTheSplashDoor(2f);
            }
            updateButtonBackMainMenuClick();
        }
        else {
            SplashDoors.closeTheSplashDoor(5f);
            if(SplashDoors.checkDoorClose() && TimeUtils.millis() - startTime >= 2000){
                GameMethods.goBackMainMenu(this, app.gsm);
            }
        }

        getStageGame().act();
        getStageGame().draw();

        app.batch.begin();
        SplashDoors.drawDoor(app.batch);
        app.batch.end();
    }

    private void updateButtonBackMainMenuClick(){
        btnBackMainMenu.updateButtonTouched();
        if(btnBackMainMenu.isTouched()){
            isFinishScreen = true;
            startTime = TimeUtils.millis();
        }
    }

    @Override
    public void pause() {
        finishMusic.pausePlay();
    }

    @Override
    public void resume() {
        finishMusic.resumePlay();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();
        acTheEndText.remove();
        soundTrack.remove();
        finishMusic.dispose();
        for(ActorImage acImg : lstCD){
            if(acImg != null){
                acImg.remove();
            }
        }
        super.dispose();
    }
}
