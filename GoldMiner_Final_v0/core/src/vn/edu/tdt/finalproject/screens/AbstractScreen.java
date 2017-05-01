package vn.edu.tdt.finalproject.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.HashMap;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.actors.ActorBomb;
import vn.edu.tdt.finalproject.actors.ActorButton;
import vn.edu.tdt.finalproject.actors.ActorPod;
import vn.edu.tdt.finalproject.actors.ActorRod;
import vn.edu.tdt.finalproject.actors.ActorText;
import vn.edu.tdt.finalproject.sounds.SoundEffect;
import vn.edu.tdt.finalproject.utils.PlayerInfo;
import vn.edu.tdt.finalproject.utils.TextConstants;

public abstract class AbstractScreen implements Screen {

    public enum ScreenState{
        PAUSE,
        PLAY,
        FINISH
    }

    protected final Application app;

    private ScreenState screenState;

    private OrthographicCamera camera;
    private Stage stageGame;

    public AbstractScreen(final Application app) {
        this.app = app;
        screenState = ScreenState.PLAY;
    }

    public void createCamera(){
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, Application.DESKTOP_WIDTH, Application.DESKTOP_HEIGHT);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Stage getStageGame() {
        return stageGame;
    }

    public void createStageGame(){
        stageGame = new Stage();
        stageGame.setViewport(new ScreenViewport(camera));
    }

    public ScreenState getScreenState() {
        return screenState;
    }

    public void setScreenState(ScreenState screenState) {
        this.screenState = screenState;
    }

    public abstract void update(float delta);

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
        stageGame.dispose();

    }

//    public void createPlayScreenActorText(ArrayList<ActorRod> lstAcRod){
//        lstAcText = new HashMap<ActorText.TextTag, ActorText>();
//        lstAcText.put(ActorText.TextTag.TIMER, new ActorText(ActorText.TextTag.TIMER));
//        lstAcText.put(ActorText.TextTag.TARGET, new ActorText(ActorText.TextTag.TARGET));
//        lstAcText.put(ActorText.TextTag.MONEY, new ActorText(ActorText.TextTag.MONEY));
//        lstAcText.put(ActorText.TextTag.TALKING, new ActorText(ActorText.TextTag.TALKING));
//        for (ActorText acText: lstAcText.values()){
//            stageGame.addActor(acText);
//        }
//
//        long targetTemp = 0;
//        for(ActorRod rod: lstAcRod){
//            if(rod != null){
//                targetTemp += rod.getMoney();
//            }
//        }
//
//        PlayerInfo.setCurrentTarget(PlayerInfo.getCurrentTarget() + (targetTemp*2/5));
//        lstAcText.get(ActorText.TextTag.TARGET).setText("" + PlayerInfo.getCurrentTarget());
//    }
//
//    public void disposeAllText(){
//        for (ActorText acText: lstAcText.values()){
//           if(acText != null){
//               acText.remove();
//           }
//        }
//    }
//
//    private long countTimePlay(long startTime){
//        return (PlayerInfo.getCurrentTimePlay() - (TimeUtils.millis()/1000 - startTime));
//    }
//
//    public boolean checkTimePlay(long startTime){
//        long timeTemp = countTimePlay(startTime);
//        updateTalkingShow();
//        if (timeTemp < 0){
//            lstAcText.get(ActorText.TextTag.TIMER).getSoundText().stopPlay();
//            return false;
//        }
//        if(timeTemp == 11){
//            lstAcText.get(ActorText.TextTag.TIMER).getSoundText().setSoundKind(SoundEffect.SoundKind.DURING);
//        }
//        if(timeTemp == 10){
//            lstAcText.get(ActorText.TextTag.TIMER).getSoundText().playSoundLoopOnAndroid();
//        }
//        if(timeTemp < 10){
//
//            lstAcText.get(ActorText.TextTag.TIMER).setText("0" + timeTemp);
//        }
//        else {
//            lstAcText.get(ActorText.TextTag.TIMER).setText("" + timeTemp);
//        }
//
//        return true;
//    }
//
//    private void updateTalkingShow(){
//        long deltaTime = TimeUtils.millis()/1000 - TextConstants.getTakingStartTimeShow();
//        if(deltaTime >= TextConstants.TALKING_MAX_TIME_SHOW){
//            lstAcText.get(ActorText.TextTag.TALKING).setTextState(ActorText.TextState.DISABLE);
//        }
//    }
//
//    private void updatePlayerMoney(){
//        lstAcText.get(ActorText.TextTag.MONEY).setText("" + PlayerInfo.getCurrentMoney());
//    }
//
//    public void updateRodCollisionEvent(ActorPod acPod, ActorBomb acBomb, ArrayList<ActorRod> lstRod, ActorButton btnCancel){
//        for(ActorRod rod : lstRod){
//            if(rod != null){
//                rod.updateCollisionWithPod(acPod, lstAcText.get(ActorText.TextTag.TALKING));
//                rod.updateCollisionWithBomb(acPod, acBomb);
//                updatePlayerMoney();
//                updateBombToShoot(acPod, rod, acBomb, btnCancel);
//            }
//        }
//    }
//
//    public void disposeAllRod(ArrayList<ActorRod> lstRod){
//        for(ActorRod rod : lstRod){
//            if(rod != null){
//                rod.remove();
//            }
//        }
//    }
//
//    public void updatePodToShoot(ActorPod acPod, ActorButton btnShoot, ActorButton btnCancel){
//        btnShoot.updateButtonTouched();
//        if(btnShoot.isTouched() && acPod.getPodState().equals(ActorPod.PodState.ROTATION)){
//            acPod.setPodState(ActorPod.PodState.SHOOT);
//            btnCancel.setButtonState(ActorButton.ButtonState.ENABLED);
//            btnShoot.setButtonState(ActorButton.ButtonState.DISABLED);
//
//        }
//        if(acPod.getPodState().equals(ActorPod.PodState.ROTATION)){
//            btnCancel.setButtonState(ActorButton.ButtonState.DISABLED);
//            btnShoot.setButtonState(ActorButton.ButtonState.ENABLED);
//        }
//
//    }
//
//    public void updateBombToShoot(ActorPod acPod, ActorRod acRod, ActorBomb bomb, ActorButton cancelButton){
//        cancelButton.updateButtonTouched();
//        if(cancelButton.isTouched() && bomb.getBombState().equals(ActorBomb.BombState.FREEZE)){
//            if(acPod.getPodState().equals(ActorPod.PodState.REWIND) && acRod.getRodState().equals(ActorRod.RodState.CATCHED)){
//                //do bomb
//                bomb.setRotation(acPod.getRotation());
//                bomb.setBombState(ActorBomb.BombState.MOVE);
//            }
//        }
//    }
//

}
