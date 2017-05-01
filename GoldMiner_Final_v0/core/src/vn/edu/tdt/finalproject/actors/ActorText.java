package vn.edu.tdt.finalproject.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

import org.w3c.dom.Text;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.sounds.SoundEffect;
import vn.edu.tdt.finalproject.utils.PlayerInfo;
import vn.edu.tdt.finalproject.utils.TextConstants;

public class ActorText extends Actor {

    public enum TextState{
        FREEZE,
        DISABLE
    }

    public enum TextTag{
        TIMER,
        MONEY,
        TARGET,
        TALKING,
        LEVEL,
        BOMB_NUMBER
    }

    private TextState textState;

    private TextTag textTag;

    private float[] textPosition;

    private Texture textBackground;

    private float[] textBackgroundInfo;

    private Color colorText;

    private int sizeText;

    private BitmapFont font;

    private String text;

    private SoundEffect soundText;

    public ActorText(String text, Color color, int size){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/luximb.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = size;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        font.setColor(color);
        textBackground = new Texture("images/textbackgrounds/moneybackground.png");
        textBackgroundInfo = new float[4];
        textPosition = new float[2];
        this.text = text;
    }

    public ActorText(TextTag tag){
        textTag = tag;
        textBackgroundInfo = new float[4];
        textPosition = new float[2];
        createDisplayText();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/luximb.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = sizeText;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        font.setColor(colorText);
    }

    private void createDisplayText(){
        if(textTag.equals(TextTag.TIMER)){
            textBackground = new Texture("images/textbackgrounds/timebackground.png");
            sizeText = TextConstants.TIMER_FONT_SIZE;
            textPosition[0] = TextConstants.TIMER_FONT_POSITION[0];
            textPosition[1] = TextConstants.TIMER_FONT_POSITION[1];
            textBackgroundInfo[0] = TextConstants.TIMER_BACKGROUND_INFO[0];
            textBackgroundInfo[1] = TextConstants.TIMER_BACKGROUND_INFO[1];
            textBackgroundInfo[2] = TextConstants.TIMER_BACKGROUND_INFO[2];
            textBackgroundInfo[3] = TextConstants.TIMER_BACKGROUND_INFO[3];
            colorText = Color.BROWN;
            text = "" + PlayerInfo.getCurrentTimePlay();
            textState = TextState.FREEZE;
            soundText = new SoundEffect("sounds/timecount.ogg");
            soundText.setSoundKind(SoundEffect.SoundKind.DURING);
            return;
        }
        if(textTag.equals(TextTag.TARGET)){
            textBackground = new Texture("images/textbackgrounds/targetbackground.png");
            sizeText = TextConstants.TARGET_FONT_SIZE;
            textPosition[0] = TextConstants.TARGET_FONT_POSITION[0];
            textPosition[1] = TextConstants.TARGET_FONT_POSITION[1];
            textBackgroundInfo[0] = TextConstants.TARGET_BACKGROUND_INFO[0];
            textBackgroundInfo[1] = TextConstants.TARGET_BACKGROUND_INFO[1];
            textBackgroundInfo[2] = TextConstants.TARGET_BACKGROUND_INFO[2];
            textBackgroundInfo[3] = TextConstants.TARGET_BACKGROUND_INFO[3];
            colorText = Color.BROWN;
            text = "" + PlayerInfo.getCurrentTarget();
            textState = TextState.FREEZE;
            soundText = new SoundEffect("sounds/target.wav");
            soundText.setSoundKind(SoundEffect.SoundKind.ONE_TIME);
            return;
        }
        if(textTag.equals(TextTag.MONEY)){
            textBackground = new Texture("images/textbackgrounds/moneybackground.png");
            sizeText = TextConstants.MONEY_FONT_SIZE;
            textPosition[0] = TextConstants.MONEY_FONT_POSITION[0];
            textPosition[1] = TextConstants.MONEY_FONT_POSITION[1];
            textBackgroundInfo[0] = TextConstants.MONEY_BACKGROUND_INFO[0];
            textBackgroundInfo[1] = TextConstants.MONEY_BACKGROUND_INFO[1];
            textBackgroundInfo[2] = TextConstants.MONEY_BACKGROUND_INFO[2];
            textBackgroundInfo[3] = TextConstants.MONEY_BACKGROUND_INFO[3];
            colorText = Color.BROWN;
            text = "" + PlayerInfo.getCurrentMoney();
            textState = TextState.FREEZE;
            return;
        }
        if(textTag.equals(TextTag.TALKING)){
            textBackground = new Texture("images/textbackgrounds/talkbackground.png");
            sizeText = TextConstants.TALKING_FONT_SIZE;
            textPosition[0] = TextConstants.TALKING_FONT_POSITION[0];
            textPosition[1] = TextConstants.TALKING_FONT_POSITION[1];
            textBackgroundInfo[0] = TextConstants.TALKING_BACKGROUND_INFO[0];
            textBackgroundInfo[1] = TextConstants.TALKING_BACKGROUND_INFO[1];
            textBackgroundInfo[2] = TextConstants.TALKING_BACKGROUND_INFO[2];
            textBackgroundInfo[3] = TextConstants.TALKING_BACKGROUND_INFO[3];
            colorText = Color.BROWN;
            text = "";
            textState = TextState.DISABLE;
            return;
        }
        if(textTag.equals(TextTag.LEVEL)){
            textBackground = new Texture("images/textbackgrounds/levelbackground.png");
            sizeText = TextConstants.LEVEL_FONT_SIZE;
            textPosition[0] = TextConstants.LEVEL_FONT_POSITION[0];
            textPosition[1] = TextConstants.LEVEL_FONT_POSITION[1];
            textBackgroundInfo[0] = TextConstants.LEVEL_BACKGROUND_INFO[0];
            textBackgroundInfo[1] = TextConstants.LEVEL_BACKGROUND_INFO[1];
            textBackgroundInfo[2] = TextConstants.LEVEL_BACKGROUND_INFO[2];
            textBackgroundInfo[3] = TextConstants.LEVEL_BACKGROUND_INFO[3];
            colorText = Color.BROWN;
            if(PlayerInfo.getCurrentLevel() < 10) {
                text = "0" + PlayerInfo.getCurrentLevel();
            }
            else {
                text = "" + PlayerInfo.getCurrentLevel();
            }
            textState = TextState.FREEZE;
            return;
        }
        if(textTag.equals(TextTag.BOMB_NUMBER)){
            textBackground = new Texture("images/textbackgrounds/bombbackground.png");
            sizeText = TextConstants.BOMB_NUM_FONT_SIZE;
            textPosition[0] = TextConstants.BOMB_NUM_FONT_POSITION[0];
            textPosition[1] = TextConstants.BOMB_NUM_FONT_POSITION[1];
            textBackgroundInfo[0] = TextConstants.BOMB_NUM_BACKGROUND_INFO[0];
            textBackgroundInfo[1] = TextConstants.BOMB_NUM_BACKGROUND_INFO[1];
            textBackgroundInfo[2] = TextConstants.BOMB_NUM_BACKGROUND_INFO[2];
            textBackgroundInfo[3] = TextConstants.BOMB_NUM_BACKGROUND_INFO[3];
            colorText = Color.BROWN;
            if(PlayerInfo.getCurrentBombNum() < 10) {
                text = "0" + PlayerInfo.getCurrentLevel();
            }
            else {
                text = "" + PlayerInfo.getCurrentLevel();
            }
            textState = TextState.FREEZE;
            return;
        }
    }

    public void setTextState(TextState textState) {
        this.textState = textState;
    }

    public SoundEffect getSoundText() {
        return soundText;
    }

    @Override
    public boolean remove() {
        textBackground.dispose();
        font.dispose();
        return super.remove();
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(textState.equals(TextState.FREEZE)) {
            batch.draw(textBackground, textBackgroundInfo[0], textBackgroundInfo[1], textBackgroundInfo[2], textBackgroundInfo[3]);
            font.draw(batch, text, textPosition[0], textPosition[1]);
        }
    }
}
