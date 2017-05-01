package vn.edu.tdt.finalproject.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.animations.AnimationCustom;
import vn.edu.tdt.finalproject.utils.ScreenConstants;

public class ActorButton extends Actor {

    public enum ButtonTag{
        SHOOT,
        BOOM,
        SETTING,
        TARGET_PLAY,
        MAINMENU_PLAY,
        SETTING_MAINMENU,
        SETTING_RESUME,
        WIN_GOTO_SHOP,
        TIMEOUT_CHANGE_SCREEN,
        LOSE_BACK_MAINMENU,
        SHOP_GOTO_NEXT_LEVEL,
        SHOP_BACK_MAINMENU,
        FINISH_BACK_MENU,
        ITEM
    }

    public enum ButtonState{
        FREEZE,
        ENABLED,
        HIDE,
        DISABLED
    }

    /**
     * Animation.
     */
    private AnimationCustom buttonAnimation;

    private ButtonState buttonState;

    private ButtonTag buttonTag;

    private boolean isTouched;

    public ActorButton(String path, float x, float y, float width, float height){
        isTouched = false;

        setPosition(x, y);
        setSize(width, height);
        buttonState = ButtonState.ENABLED;
        buttonTag = ButtonTag.ITEM;

        buttonAnimation = new AnimationCustom(path, 20f,  getX(), getY(), getWidth(), getHeight());

    }

    public ActorButton(float x, float y, float width, float height, ButtonTag tag){
        isTouched = false;

        setPosition(x, y);
        setSize(width, height);
        buttonState = ButtonState.ENABLED;
        buttonTag = tag;

        createButtonDisplay();

    }

    public ActorButton(float width, float height, ButtonTag tag){
        isTouched = false;

        buttonState = ButtonState.ENABLED;
        buttonTag = tag;

        setButtonPosition(width, height);
        setSize(width, height);

        createButtonDisplay();
    }

    public void setButtonState(ButtonState buttonState) {
        this.buttonState = buttonState;
    }

    public ButtonState getButtonState() {
        return buttonState;
    }

    public boolean isTouched() {
        return isTouched;
    }


    private void createButtonDisplay(){
        if(buttonTag.equals(ButtonTag.SHOOT)){
            buttonAnimation = new AnimationCustom("animations/buttons/shoot/shootbutton.atlas", 20f, getX(), getY(), getWidth(), getHeight());

        }
        if(buttonTag.equals(ButtonTag.BOOM)){
            buttonAnimation = new AnimationCustom("animations/buttons/boom/cancelbutton.atlas", 20f, getX(), getY(), getWidth(), getHeight());

            buttonState = ButtonState.DISABLED;
        }
        if(buttonTag.equals(ButtonTag.TARGET_PLAY)){
            buttonAnimation = new AnimationCustom("animations/buttons/targetplay/targetplaybutton.atlas", 20f,  getX(), getY(), getWidth(), getHeight());
            buttonState = ButtonState.DISABLED;
        }
        if(buttonTag.equals(ButtonTag.MAINMENU_PLAY)){
            buttonAnimation = new AnimationCustom("animations/buttons/menuplay/menuplaybutton.atlas", 20f,  getX(), getY(), getWidth(), getHeight());
        }
        if(buttonTag.equals(ButtonTag.SETTING)){
            buttonAnimation = new AnimationCustom("animations/buttons/setting/settingbutton.atlas", 20f,  getX(), getY(), getWidth(), getHeight());
        }
        if(buttonTag.equals(ButtonTag.SETTING_MAINMENU)){
            buttonAnimation = new AnimationCustom("animations/buttons/backmainmenu/backmainmenu.atlas", 20f,  getX(), getY(), getWidth(), getHeight());
            buttonState = ButtonState.DISABLED;
        }
        if(buttonTag.equals(ButtonTag.SETTING_RESUME)){
            buttonAnimation = new AnimationCustom("animations/buttons/targetplay/targetplaybutton.atlas", 20f,  getX(), getY(), getWidth(), getHeight());
            buttonState = ButtonState.DISABLED;
        }
        if(buttonTag.equals(ButtonTag.WIN_GOTO_SHOP)){
            buttonAnimation = new AnimationCustom("animations/buttons/targetplay/targetplaybutton.atlas", 20f,  getX(), getY(), getWidth(), getHeight());
        }
        if(buttonTag.equals(ButtonTag.TIMEOUT_CHANGE_SCREEN)){
            buttonAnimation = new AnimationCustom("animations/buttons/targetplay/targetplaybutton.atlas", 20f,  getX(), getY(), getWidth(), getHeight());
            buttonState = ButtonState.DISABLED;
        }
        if(buttonTag.equals(ButtonTag.LOSE_BACK_MAINMENU)){
            buttonAnimation = new AnimationCustom("animations/buttons/backmainmenu/backmainmenu.atlas", 20f,  getX(), getY(), getWidth(), getHeight());
        }
        if(buttonTag.equals(ButtonTag.SHOP_BACK_MAINMENU)){
            buttonAnimation = new AnimationCustom("animations/buttons/backmainmenu/backmainmenu.atlas", 20f,  getX(), getY(), getWidth(), getHeight());
        }
        if(buttonTag.equals(ButtonTag.SHOP_GOTO_NEXT_LEVEL)){
            buttonAnimation = new AnimationCustom("animations/buttons/targetplay/targetplaybutton.atlas", 20f,  getX(), getY(), getWidth(), getHeight());
        }
        if(buttonTag.equals(ButtonTag.FINISH_BACK_MENU)){
            buttonAnimation = new AnimationCustom("animations/buttons/backmainmenu/backmainmenu.atlas", 20f,  getX(), getY(), getWidth(), getHeight());
        }
    }

    private void setButtonPosition(float width, float height){
        if(buttonTag.equals(ButtonTag.SHOOT)){
            setPosition(Application.DESKTOP_WIDTH - width - 2, 0);
        }
        if(buttonTag.equals(ButtonTag.BOOM)){
            setPosition(Application.DESKTOP_WIDTH - width - 2, 0);
        }
        if(buttonTag.equals(ButtonTag.TARGET_PLAY)){
            setPosition(Application.DESKTOP_WIDTH/2 - width/2, 0);
        }
        if(buttonTag.equals(ButtonTag.MAINMENU_PLAY)){
            setPosition(Application.DESKTOP_WIDTH - 200f, Application.DESKTOP_HEIGHT/8);
        }
        if(buttonTag.equals(ButtonTag.SETTING_MAINMENU)){
            setPosition(Application.DESKTOP_WIDTH/2 - 2*width - 10f, Application.DESKTOP_HEIGHT/2 - height/2);
        }
        if(buttonTag.equals(ButtonTag.SETTING_RESUME)){
            setPosition(Application.DESKTOP_WIDTH/2 + width + 10f, Application.DESKTOP_HEIGHT/2 - height/2);
        }
        if(buttonTag.equals(ButtonTag.SETTING)){
            setPosition(110f, Application.DESKTOP_HEIGHT - height - 10f);
        }
        if(buttonTag.equals(ButtonTag.WIN_GOTO_SHOP)){
            setPosition(Application.DESKTOP_WIDTH - width - width/3, 0);
        }
        if(buttonTag.equals(ButtonTag.TIMEOUT_CHANGE_SCREEN)){
            setPosition(Application.DESKTOP_WIDTH/2 - width/2, 40f);
        }
        if(buttonTag.equals(ButtonTag.LOSE_BACK_MAINMENU)){
            setPosition(Application.DESKTOP_WIDTH/2 - width/2, 40f);
        }
        if(buttonTag.equals(ButtonTag.SHOP_BACK_MAINMENU)){
            setPosition(15f, Application.DESKTOP_HEIGHT - height - 15f);
        }
        if(buttonTag.equals(ButtonTag.SHOP_GOTO_NEXT_LEVEL)){
            setPosition(Application.DESKTOP_WIDTH - width - 15f, Application.DESKTOP_HEIGHT - height - 15f);
        }
        if(buttonTag.equals(ButtonTag.FINISH_BACK_MENU)){
            setPosition(Application.DESKTOP_WIDTH - width - 2, 0);
        }
    }

    public void updateButtonTouched(){

        if (!Gdx.input.justTouched()) {
            isTouched = false;
            return;
        }
        if(buttonState.equals(ButtonState.ENABLED)) {
            if (checkXTouch(Gdx.input.getX()) && checkYTouch(Gdx.input.getY())) {
                isTouched = true;
            } else {
                isTouched = false;
            }
            return;
        }
        isTouched = false;
    }

    private boolean checkXTouch(float touchX) {
        touchX = touchX / ScreenConstants.TRANSFORM_X;
        float temp10p = getX() + getWidth()*10/100;
        float temp90p = getX() + getWidth()*90/100;
        if(temp10p < touchX && touchX < temp90p) {
            return true;
        }
        return false;
    }

    private boolean checkYTouch(float touchY) {
        touchY =  touchY/ ScreenConstants.TRANSFORM_Y;
        touchY = synchronizeTouchY(touchY);
        float temp10p = getY() + getHeight()*10/100;
        float temp90p = getY() + getHeight()*90/100;
        if(temp10p < touchY && touchY < temp90p) {
            return true;
        }
        return false;
    }



    private float synchronizeTouchY(float touchY) {
        return Application.DESKTOP_HEIGHT - touchY;
    }

    @Override
    public boolean remove() {
        buttonAnimation.dispose();
        return super.remove();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(buttonState.equals(ButtonState.ENABLED)){
            buttonAnimation.drawButton(batch, isTouched);
        }
    }
}
