package vn.edu.tdt.finalproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.actors.ActorButton;
import vn.edu.tdt.finalproject.actors.ActorImage;
import vn.edu.tdt.finalproject.actors.ActorItem;
import vn.edu.tdt.finalproject.sounds.MusicEffect;
import vn.edu.tdt.finalproject.sounds.SoundEffect;
import vn.edu.tdt.finalproject.utils.GameMethods;
import vn.edu.tdt.finalproject.utils.PlayerInfo;
import vn.edu.tdt.finalproject.utils.ScreenConstants;
import vn.edu.tdt.finalproject.utils.ShopConstants;
import vn.edu.tdt.finalproject.utils.SplashDoors;
import vn.edu.tdt.finalproject.utils.TextNoBackground;

public class ShopScreen extends AbstractScreen{

    private Texture background;
    private MusicEffect shopMusic;
    private SoundEffect closeDoorSound;
    private ActorButton btnGoNextLevel;
    private ActorButton btnBackMainMenu;
    private boolean isFinishScreen;
    private int nextScreen;
    private long startTime;
    private Texture currentMoneyBack;
    private TextNoBackground currMoneyText;

    public ShopScreen(Application app) {
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
        background = new Texture("images/backgrounds/shopbackground.png");
        currentMoneyBack = new Texture("images/backgrounds/currmoneyback.png");
        currMoneyText = new TextNoBackground(Color.BROWN, 22);
        isFinishScreen = false;
        nextScreen = 0;
        startTime = TimeUtils.millis()/1000;

        shopMusic = new MusicEffect("sounds/shopmusic.ogg");
        shopMusic.setMusicKind(MusicEffect.MusicKind.DURING);
        shopMusic.playMusicLoopOnAndroid();

        closeDoorSound = new SoundEffect("sounds/closedoor.ogg");
        closeDoorSound.setSoundKind(SoundEffect.SoundKind.ONE_TIME);

        btnGoNextLevel = new ActorButton(120f, 120f, ActorButton.ButtonTag.SHOP_GOTO_NEXT_LEVEL);
        btnBackMainMenu = new ActorButton(120f, 120f, ActorButton.ButtonTag.SHOP_BACK_MAINMENU);

        GameMethods.randomShopItem();

        getStageGame().addActor(btnGoNextLevel);
        getStageGame().addActor(btnBackMainMenu);

        for(int i = 0; i < ShopConstants.getListItems().length; i++){
            if(ShopConstants.getListItems()[i] != null){
                getStageGame().addActor(ShopConstants.getListItems()[i]);
            }
        }

        Gdx.input.setInputProcessor(getStageGame());
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        app.batch.begin();
        app.batch.draw(background, 0, 0, Application.DESKTOP_WIDTH, Application.DESKTOP_HEIGHT);
        app.batch.draw(currentMoneyBack, Application.DESKTOP_WIDTH - 350f, Application.DESKTOP_HEIGHT - 135f, 140f, 130f);
        currMoneyText.drawText(app.batch, "$" + PlayerInfo.getCurrentMoney(), Application.DESKTOP_WIDTH - 315f, Application.DESKTOP_HEIGHT - 100f);
        app.batch.end();

        updateButtonNextLevelClickEvent();
        updateButtonBackMenuClickEvent();

        if(!isFinishScreen){
            SplashDoors.openTheSplashDoor(5f);
        }
        else {
            SplashDoors.closeTheSplashDoor(10f);
            shopMusic.stopPlay();
            closeDoorSound.playSound();
            if(SplashDoors.checkDoorClose() && TimeUtils.millis()/1000 - startTime >= 2){
                app.gsm.setScreen(nextScreen);
                dispose();
            }
        }

        getStageGame().act();
        getStageGame().draw();

        app.batch.begin();
        SplashDoors.drawDoor(app.batch);
        app.batch.end();
    }

    private void updateButtonBackMenuClickEvent(){
        btnBackMainMenu.updateButtonTouched();
        if(btnBackMainMenu.isTouched() && !isFinishScreen){
            isFinishScreen = true;
            nextScreen = ScreenConstants.MAINMENU_SCREEN;
            startTime = TimeUtils.millis()/1000;
        }
    }

    private void updateButtonNextLevelClickEvent(){
        btnGoNextLevel.updateButtonTouched();
        if(btnGoNextLevel.isTouched() && !isFinishScreen){
            isFinishScreen = true;
            nextScreen = PlayerInfo.getCurrentLevel();
            startTime = TimeUtils.millis()/1000;
        }
    }

    @Override
    public void pause() {
        shopMusic.pausePlay();
    }

    @Override
    public void resume() {
        shopMusic.resumePlay();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        ShopConstants.resetListItem();
        background.dispose();
        btnGoNextLevel.remove();
        btnBackMainMenu.remove();
        shopMusic.dispose();
        closeDoorSound.dispose();
        currentMoneyBack.dispose();
        currMoneyText.dispose();
        super.dispose();
    }
}
