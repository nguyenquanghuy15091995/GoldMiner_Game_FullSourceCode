package vn.edu.tdt.finalproject.utils;

import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import vn.edu.tdt.finalproject.actors.ActorBomb;
import vn.edu.tdt.finalproject.actors.ActorButton;
import vn.edu.tdt.finalproject.actors.ActorItem;
import vn.edu.tdt.finalproject.actors.ActorPod;
import vn.edu.tdt.finalproject.actors.ActorRod;
import vn.edu.tdt.finalproject.actors.ActorText;
import vn.edu.tdt.finalproject.managers.GameScreenManager;
import vn.edu.tdt.finalproject.screens.AbstractScreen;
import vn.edu.tdt.finalproject.screens.ShopScreen;
import vn.edu.tdt.finalproject.sounds.SoundEffect;

public class GameMethods {

    public static void createPlayScreenActorText(Stage stage, ArrayList<ActorRod> lstAcRod, HashMap<ActorText.TextTag, ActorText> lstAcText){
        lstAcText.put(ActorText.TextTag.TIMER, new ActorText(ActorText.TextTag.TIMER));
        lstAcText.put(ActorText.TextTag.TARGET, new ActorText(ActorText.TextTag.TARGET));
        lstAcText.put(ActorText.TextTag.MONEY, new ActorText(ActorText.TextTag.MONEY));
        lstAcText.put(ActorText.TextTag.TALKING, new ActorText(ActorText.TextTag.TALKING));
        lstAcText.put(ActorText.TextTag.LEVEL, new ActorText(ActorText.TextTag.LEVEL));
        lstAcText.put(ActorText.TextTag.BOMB_NUMBER, new ActorText(ActorText.TextTag.BOMB_NUMBER));
        for (ActorText acText: lstAcText.values()){
            stage.addActor(acText);
        }

        long targetTemp = 0;
        for(ActorRod rod: lstAcRod){
            if(rod != null){
                targetTemp += rod.getMoney();
            }
        }

        PlayerInfo.setCurrentTarget(PlayerInfo.getCurrentTarget() + (targetTemp*9/20));
        lstAcText.get(ActorText.TextTag.TARGET).setText("" + PlayerInfo.getCurrentTarget());
    }

    public static void updateSoundTargetSuccess(HashMap<ActorText.TextTag, ActorText> lstAcText){
        if(PlayerInfo.getCurrentMoney() >= PlayerInfo.getCurrentTarget()){
            lstAcText.get(ActorText.TextTag.TARGET).getSoundText().playSound();
        }
    }

    public static void updateBombNumber(HashMap<ActorText.TextTag, ActorText> lstAcText){
        if(PlayerInfo.getCurrentBombNum() < 10) {
            lstAcText.get(ActorText.TextTag.BOMB_NUMBER).setText("0" + PlayerInfo.getCurrentBombNum());
        }
        else {
            lstAcText.get(ActorText.TextTag.BOMB_NUMBER).setText("" + PlayerInfo.getCurrentBombNum());
        }
    }

    public static void disposeAllText(HashMap<ActorText.TextTag,ActorText> lstAcText){
        for (ActorText acText: lstAcText.values()){
            if(acText != null){
                acText.remove();
            }
        }
    }

    private static long countTimePlay(long startTime){
        if ((PlayerInfo.getCurrentTimePlay() - (TimeUtils.millis()/1000 - startTime)) > PlayerInfo.getCurrentTimePlay()){
            return PlayerInfo.getCurrentTimePlay();
        }
        else {
            return (PlayerInfo.getCurrentTimePlay() - (TimeUtils.millis() / 1000 - startTime));
        }
    }

    public static void pauseTimerSound(HashMap<ActorText.TextTag,ActorText> lstAcText){
        lstAcText.get(ActorText.TextTag.TIMER).getSoundText().pausePlay();
    }
    public static void resumeTimerSound(HashMap<ActorText.TextTag,ActorText> lstAcText){
        lstAcText.get(ActorText.TextTag.TIMER).getSoundText().resumePlay();
    }

    public static boolean checkTimePlay(HashMap<ActorText.TextTag,ActorText> lstAcText, long startTime){
        long timeTemp = countTimePlay(startTime);
        updateTalkingShow(lstAcText);
        if (timeTemp < 0){
            lstAcText.get(ActorText.TextTag.TIMER).getSoundText().stopPlay();
            return false;
        }
        if(timeTemp == 10){
            lstAcText.get(ActorText.TextTag.TIMER).getSoundText().playSoundLoopOnAndroid();
        }
        if(timeTemp < 10){

            lstAcText.get(ActorText.TextTag.TIMER).setText("0" + timeTemp);
        }
        else {
            lstAcText.get(ActorText.TextTag.TIMER).setText("" + timeTemp);
        }

        return true;
    }

    private static void updateTalkingShow(HashMap<ActorText.TextTag,ActorText> lstAcText){
        long deltaTime = TimeUtils.millis()/1000 - TextConstants.getTakingStartTimeShow();
        if(deltaTime >= TextConstants.TALKING_MAX_TIME_SHOW){
            lstAcText.get(ActorText.TextTag.TALKING).setTextState(ActorText.TextState.DISABLE);
        }
    }

    private static void updatePlayerMoney(HashMap<ActorText.TextTag,ActorText> lstAcText){
        lstAcText.get(ActorText.TextTag.MONEY).setText("" + PlayerInfo.getCurrentMoney());
    }

    public static void updateRodCollisionEvent(ActorPod acPod, ActorBomb acBomb, ArrayList<ActorRod> lstRod, HashMap<ActorText.TextTag,ActorText> lstAcText, ActorButton btnCancel){
        for(ActorRod rod : lstRod){
            if(rod != null){
                rod.updateCollisionWithPod(acPod, lstAcText.get(ActorText.TextTag.TALKING));
                rod.updateCollisionWithBomb(acPod, acBomb);
                updatePlayerMoney(lstAcText);
                updateBombToShoot(acPod, rod, acBomb, btnCancel);
            }
        }
    }

    public static void disposeAllRod(ArrayList<ActorRod> lstRod){
        for(ActorRod rod : lstRod){
            if(rod != null){
                rod.remove();
            }
        }
    }

    public static void updatePodToShoot(ActorPod acPod, ActorButton btnShoot, ActorButton btnCancel){
        btnShoot.updateButtonTouched();
        if(btnShoot.isTouched() && acPod.getPodState().equals(ActorPod.PodState.ROTATION)){
            acPod.setPodState(ActorPod.PodState.SHOOT);
            btnCancel.setButtonState(ActorButton.ButtonState.ENABLED);
            btnShoot.setButtonState(ActorButton.ButtonState.DISABLED);

        }
        if(acPod.getPodState().equals(ActorPod.PodState.ROTATION)){
            btnCancel.setButtonState(ActorButton.ButtonState.DISABLED);
            btnShoot.setButtonState(ActorButton.ButtonState.ENABLED);
        }

    }

    private static void updateBombToShoot(ActorPod acPod, ActorRod acRod, ActorBomb bomb, ActorButton cancelButton){
        cancelButton.updateButtonTouched();
        if(PlayerInfo.getCurrentBombNum() > 0) {
            if (cancelButton.isTouched() && bomb.getBombState().equals(ActorBomb.BombState.FREEZE)) {
                if (acPod.getPodState().equals(ActorPod.PodState.REWIND) && acRod.getRodState().equals(ActorRod.RodState.CATCHED)) {
                    //do bomb
                    bomb.setRotation(acPod.getRotation());
                    bomb.setBombState(ActorBomb.BombState.MOVE);
                    PlayerInfo.setCurrentBombNum(PlayerInfo.getCurrentBombNum() - 1);
                }
            }
        }
    }

    public static void goWinOrLoseScreen(AbstractScreen currentScreen, GameScreenManager gsm){
        if(PlayerInfo.getCurrentMoney() >= PlayerInfo.getCurrentTarget()){
            PlayerInfo.setCurrentLevel(PlayerInfo.getCurrentLevel() + 1);
            gsm.setScreen(ScreenConstants.WIN_SCREEN);
        }
        else {
            gsm.setScreen(ScreenConstants.LOSE_SCREEN);
        }
        currentScreen.dispose();
    }

    public static void goBackMainMenu(AbstractScreen currentScreen, GameScreenManager gsm){
        gsm.setScreen(ScreenConstants.MAINMENU_SCREEN);
        currentScreen.dispose();
    }

    public static void randomShopItem(){
        long deltaRange = (PlayerInfo.getCurrentMoney() - PlayerInfo.getCurrentTarget())/2;
        Random rand = new Random();
        for(int i = 0; i < ShopConstants.getListItems().length; i++){
            int temp = rand.nextInt(ShopConstants.getListItems().length);
            int newMoney = rand.nextInt((int) deltaRange) + 1;
            if(temp % 2 == 0){
                ShopConstants.getListItems()[i].setEnable(true);
                ShopConstants.getListItems()[i].setMoney(newMoney);
            }
            else {
                ShopConstants.getListItems()[i].setEnable(false);
            }
        }
    }

    public static boolean isCatchedAllRod(ArrayList<ActorRod> lstAcRod){
        int countRod = 0;
        for (ActorRod rod : lstAcRod){
            if(rod != null){
                if(rod.getRodState().equals(ActorRod.RodState.DISABLED)){
                    countRod++;
                }
            }
        }

        if(countRod == lstAcRod.size()){
            return true;
        }
        else {
            return false;
        }
    }
}
