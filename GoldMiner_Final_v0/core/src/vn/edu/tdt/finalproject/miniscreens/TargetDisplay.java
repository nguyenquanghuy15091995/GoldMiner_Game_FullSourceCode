package vn.edu.tdt.finalproject.miniscreens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.actors.ActorButton;
import vn.edu.tdt.finalproject.sounds.MusicEffect;
import vn.edu.tdt.finalproject.utils.PlayerInfo;
import vn.edu.tdt.finalproject.utils.ScreenConstants;
import vn.edu.tdt.finalproject.utils.SplashDoors;

public class TargetDisplay extends Actor{
    private Texture background;
    private Texture board;
    private ActorButton btnPlay;
    private MiniScreenState miniScreenState;
    private BoardState boardState;
    private float boardDirectionY;
    private long startTime;
    private MusicEffect soundStartPlay;
    private BitmapFont font;


    public TargetDisplay(){
        background = new Texture("images/backgrounds/minidisplay.png");
        board = new Texture("images/textureobjects/targetboard.png");
        btnPlay = new ActorButton(128f, 128f, ActorButton.ButtonTag.TARGET_PLAY);
        miniScreenState = MiniScreenState.FREEZE;
        boardState = BoardState.MOVE_DOWN;
        boardDirectionY =  Application.DESKTOP_HEIGHT;
        startTime = TimeUtils.millis()/1000;
        soundStartPlay = new MusicEffect("sounds/startplay.ogg");
        soundStartPlay.setMusicKind(MusicEffect.MusicKind.ONE_TIME);
        soundStartPlay.playMusic();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/luximb.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        font = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!
        font.setColor(Color.BROWN);

    }

    public ActorButton getBtnPlay() {
        return btnPlay;
    }

    public MiniScreenState getMiniScreenState() {
        return miniScreenState;
    }

    public void setMiniScreenState(MiniScreenState miniScreenState) {
        this.miniScreenState = miniScreenState;
    }

    public void moveDownBoard(){
        if(boardState.equals(BoardState.MOVE_DOWN)) {
            if (boardDirectionY > Application.DESKTOP_HEIGHT - 390f) {
                boardDirectionY -= 3* ScreenConstants.TRANSFORM_Y;
            } else {
                btnPlay.setButtonState(ActorButton.ButtonState.ENABLED);
                boardState = BoardState.FREEZE;
            }
        }
    }

    public void moveUpBoard(){
        if(boardState.equals(BoardState.MOVE_UP)) {
            if (boardDirectionY < Application.DESKTOP_HEIGHT) {
                boardDirectionY += 10*ScreenConstants.TRANSFORM_Y;
            } else {
                miniScreenState = MiniScreenState.HIDE;
                btnPlay.setButtonState(ActorButton.ButtonState.DISABLED);
                boardState = BoardState.FREEZE;
            }
        }
    }



    @Override
    public void act(float delta) {
        super.act(delta);
        if(!miniScreenState.equals(MiniScreenState.FINISH)) {
            if (TimeUtils.millis() / 1000 - startTime > 1) {
                SplashDoors.openTheSplashDoor(3f);
            }
            if (miniScreenState.equals(MiniScreenState.FREEZE)) {
                if (TimeUtils.millis() / 1000 - startTime > 2) {
                    miniScreenState = MiniScreenState.SHOW;
                }
            }
            if (miniScreenState.equals(MiniScreenState.SHOW)) {
                btnPlay.updateButtonTouched();
                moveDownBoard();
                moveUpBoard();

                if (btnPlay.isTouched()) {
                    btnPlay.setButtonState(ActorButton.ButtonState.HIDE);
                    boardState = BoardState.MOVE_UP;

                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if(miniScreenState.equals(MiniScreenState.FREEZE)){
            batch.draw(background, 0f, 0f, Application.DESKTOP_WIDTH, Application.DESKTOP_HEIGHT);
            SplashDoors.drawDoor(batch);
        }
        if(miniScreenState.equals(MiniScreenState.SHOW)){
            batch.draw(background, 0f, 0f, Application.DESKTOP_WIDTH, Application.DESKTOP_HEIGHT);
            batch.draw(board, Application.DESKTOP_WIDTH/2 - 200f, boardDirectionY, 420f, 390f);
            font.draw(batch,"" + PlayerInfo.getCurrentTarget(), Application.DESKTOP_WIDTH/2 - 60f, boardDirectionY + 130f);
            btnPlay.draw(batch, parentAlpha);
            SplashDoors.drawDoor(batch);
        }
    }

    @Override
    public boolean remove() {
        background.dispose();
        board.dispose();
        btnPlay.remove();
        font.dispose();
        soundStartPlay.dispose();
        return super.remove();
    }
}
