package vn.edu.tdt.finalproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.actors.ActorButton;
import vn.edu.tdt.finalproject.actors.ActorImage;
import vn.edu.tdt.finalproject.sounds.MusicEffect;
import vn.edu.tdt.finalproject.sounds.SoundEffect;
import vn.edu.tdt.finalproject.utils.PlayerInfo;
import vn.edu.tdt.finalproject.utils.ScreenConstants;
import vn.edu.tdt.finalproject.utils.SplashDoors;

public class WinScreen extends AbstractScreen {

    private ScreenState screenState;
    private Texture background;
    private SoundEffect soundCloseDoor;
    private MusicEffect musicThugsLife;
    private long startTime;
    private long[] pauseTempTime;
    private boolean isEffect;
    private boolean isMove;
    private ActorImage acYou;
    private ActorImage acWin;
    private ActorImage acHuman;
    private ActorImage acGlasses;
    private boolean isHumanThugslife;
    private ActorButton btnShop;


    public WinScreen(Application app) {
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
        background = new Texture("images/backgrounds/winbackground.png");
        screenState = ScreenState.PLAY;
        isEffect = false;
        isMove = true;
        isHumanThugslife = false;

        soundCloseDoor = new SoundEffect("sounds/closedoor.ogg");
        soundCloseDoor.setSoundKind(SoundEffect.SoundKind.ONE_TIME);

        musicThugsLife = new MusicEffect("sounds/thugslife.ogg");
        musicThugsLife.setMusicKind(MusicEffect.MusicKind.DURING);
        musicThugsLife.playMusicLoopOnAndroid();

        startTime = TimeUtils.millis();
        pauseTempTime = new long[2];

        btnShop = new ActorButton(125f, 125f, ActorButton.ButtonTag.WIN_GOTO_SHOP);

        acHuman = new ActorImage("images/textureobjects/humanbarrow.png", -170f, -25f, 170f, 200f);

        acGlasses = new ActorImage("images/textureobjects/thugslifeglasses.png", -105f, 58f, 60f, 60f);

        acYou = new ActorImage("images/textureobjects/you.png", (-1) * 300f, Application.DESKTOP_HEIGHT - 200f, 300f, 120f);

        acWin= new ActorImage("images/textureobjects/win.png", Application.DESKTOP_WIDTH, Application.DESKTOP_HEIGHT - 200f, 300f, 120f);

        getStageGame().addActor(acHuman);
        getStageGame().addActor(acGlasses);
        getStageGame().addActor(acYou);
        getStageGame().addActor(acWin);
        getStageGame().addActor(btnShop);

        Gdx.input.setInputProcessor(getStageGame());
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if(screenState.equals(ScreenState.PAUSE)){
            pauseTempTime[1] = TimeUtils.millis();
            if(pauseTempTime[1] - pauseTempTime[0] >=1000){
                pauseTempTime[0] = pauseTempTime[1];
                startTime += 1000;
            }
        }

        app.batch.begin();
        app.batch.draw(background,0,0,Application.DESKTOP_WIDTH,Application.DESKTOP_HEIGHT);
        SplashDoors.drawDoor(app.batch);
        app.batch.end();

        if(screenState.equals(ScreenState.PLAY)) {
            getStageGame().act();
            updateThugslife();
            getStageGame().draw();
            SplashDoors.openTheSplashDoor(2f);
            if(SplashDoors.checkDoorOpen()){
                updateButtonGotoShopClickEvent();
            }
        }

        if(screenState.equals(ScreenState.FINISH)){
            SplashDoors.closeTheSplashDoor(10f);
            musicThugsLife.stopPlay();
            soundCloseDoor.playSound();
            if(SplashDoors.checkDoorClose() && TimeUtils.millis() - startTime >= 2000){
                if(PlayerInfo.getCurrentLevel() > ScreenConstants.PLAY_LEVEL_SCREEN.length){
                    app.gsm.setScreen(ScreenConstants.FINISH_SCREEN);
                }
                else {
                    app.gsm.setScreen(ScreenConstants.SHOP_SCREEN);
                }
                dispose();
            }
        }

        app.batch.begin();
        SplashDoors.drawDoor(app.batch);
        app.batch.end();
    }

    private void updateButtonGotoShopClickEvent(){
        btnShop.updateButtonTouched();
        if(btnShop.isTouched()){
            screenState = ScreenState.FINISH;
            startTime = TimeUtils.millis();
        }
    }

    private void updateThugslife(){
        if(TimeUtils.millis() - startTime < 9000){
            if(isMove) {

                isMove = true;
                acYou.setMoveRight(1f, 450f);
                acWin.setMoveLeft(1f, 450f);
            }
            else if(!isMove){
                acWin.setRotationNormal(1f);
                acYou.setRotationNormal(-1f);
            }
        }
        if(TimeUtils.millis() - startTime >= 9300 && TimeUtils.millis() - startTime <= 9900){
            if(isMove) {
                acWin.setNonMoveLefRight();
                acYou.setNonMoveLefRight();
                acWin.setNewFirstShape(acWin.getX(), acWin.getY(), acWin.getWidth(), acWin.getHeight());
                acYou.setNewFirstShape(acYou.getX(), acYou.getY(), acYou.getWidth(), acYou.getHeight());
                isMove = false;
            }

            if(!isMove && !isEffect) {
                //stop move
                acWin.setNonMoveLefRight();
                acYou.setNonMoveLefRight();

                //effect
                acYou.setRotationWave(1.8f, 45f);
                acYou.setChangeSizeEffectOn(1.5f, 50f);
                acWin.setRotationWave(1.8f, 45f);
                acWin.setChangeSizeEffectOn(1.5f, 50f);
                acHuman.setRotationWave(0.2f, 4f);
                acGlasses.setRotationWave(0.2f, 4f);
                isEffect = true;
            }

            if(!isHumanThugslife){
                acGlasses.setMoveRight(1f, Application.DESKTOP_WIDTH/3);
                acHuman.setMoveRight(1f, Application.DESKTOP_WIDTH/3);
                isHumanThugslife = true;
            }
        }
        if(TimeUtils.millis() - startTime >= 30000 && TimeUtils.millis() - startTime <= 30500){
            if(isEffect) {
                acYou.setNonMoveLefRight();
                acYou.setNonRotation();
                acYou.setChangeSizeEffectOff();

                acWin.setNonMoveLefRight();
                acWin.setNonRotation();
                acWin.setChangeSizeEffectOff();

                acHuman.setNonRotation();
                acGlasses.setNonRotation();

                isEffect = false;
            }
        }
        if(TimeUtils.millis() - startTime > 30200){
            startTime = TimeUtils.millis();
        }
    }

    @Override
    public void pause() {
        screenState = ScreenState.PAUSE;
        musicThugsLife.pausePlay();
        pauseTempTime[0] = TimeUtils.millis();
    }

    @Override
    public void resume() {
        screenState = ScreenState.PLAY;
        musicThugsLife.resumePlay();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        acYou.remove();
        acWin.remove();
        acGlasses.remove();
        acHuman.remove();
        background.dispose();
        musicThugsLife.dispose();
        soundCloseDoor.dispose();
        super.dispose();
    }
}
