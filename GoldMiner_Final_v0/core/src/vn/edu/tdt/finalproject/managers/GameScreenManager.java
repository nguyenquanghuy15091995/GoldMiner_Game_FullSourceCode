package vn.edu.tdt.finalproject.managers;

import java.util.HashMap;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.screens.AbstractScreen;
import vn.edu.tdt.finalproject.screens.FinishScreen;
import vn.edu.tdt.finalproject.screens.LoseScreen;
import vn.edu.tdt.finalproject.screens.MainMenuScreen;
import vn.edu.tdt.finalproject.screens.ScreenPlayLevel1;
import vn.edu.tdt.finalproject.screens.ScreenPlayLevel2;
import vn.edu.tdt.finalproject.screens.ScreenTest;
import vn.edu.tdt.finalproject.screens.ShopScreen;
import vn.edu.tdt.finalproject.screens.WinScreen;
import vn.edu.tdt.finalproject.utils.ScreenConstants;

public class GameScreenManager {
    public final Application app;

    private AbstractScreen gameScreen;

    public GameScreenManager(final Application app){
        this.app = app;

        setScreen(ScreenConstants.MAINMENU_SCREEN);

    }

    public AbstractScreen getNextScreen(int level){
        gameScreen = null;
        if(level == ScreenConstants.WIN_SCREEN){
            gameScreen = new WinScreen(this.app);
        }
        if(level == ScreenConstants.LOSE_SCREEN){
            gameScreen = new LoseScreen(this.app);
        }
        if(level == ScreenConstants.FINISH_SCREEN){
            gameScreen = new FinishScreen(this.app);
        }
        if(level == ScreenConstants.SHOP_SCREEN){
            gameScreen = new ShopScreen(this.app);
        }
        if(level == ScreenConstants.MAINMENU_SCREEN){
            gameScreen = new MainMenuScreen(this.app);
        }
        if(level == ScreenConstants.PLAY_LEVEL_SCREEN[0]){
            gameScreen = new ScreenPlayLevel1(this.app);
        }
        if(level == ScreenConstants.PLAY_LEVEL_SCREEN[1]){
            gameScreen = new ScreenPlayLevel2(this.app);
        }
        return gameScreen;
    }

    public void setScreen(int level){
        app.setScreen(getNextScreen(level));
    }

    public void dispose(){

    }
}
