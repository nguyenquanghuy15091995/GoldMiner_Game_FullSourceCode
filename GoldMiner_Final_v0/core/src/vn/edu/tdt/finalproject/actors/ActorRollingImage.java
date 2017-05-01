package vn.edu.tdt.finalproject.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.utils.ScreenConstants;

public class ActorRollingImage extends Actor {

    private Texture backA;
    private Texture backB;
    private float positionXA;
    private float positionXB;
    private float moveSpeed;
    private boolean isRolling;
    private float y;
    private float height;

    public ActorRollingImage(String path, float rollingSpeed, float y, float height){
        backA = new Texture(path);
        backB = new Texture(path);
        moveSpeed = rollingSpeed;
        this.y = y;
        this.height = height;
        positionXA = 0;
        positionXB = Application.DESKTOP_WIDTH;
        isRolling = false;

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(isRolling) {
            positionXA -= moveSpeed * ScreenConstants.TRANSFORM_X;
            positionXB -= moveSpeed * ScreenConstants.TRANSFORM_X;
            if (positionXA <= (-1) * Application.DESKTOP_WIDTH) {
                positionXA = 0;
            }
            if (positionXB <= 0) {
                positionXB = Application.DESKTOP_WIDTH;
            }
        }
    }

    public void stopRolling(){
        isRolling = false;
    }

    public void startRolling(){
        isRolling = true;
    }

    @Override
    public boolean remove() {
        backA.dispose();
        backB.dispose();
        return super.remove();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(backA, positionXA, y, Application.DESKTOP_WIDTH, height);
        batch.draw(backB, positionXB, y, Application.DESKTOP_WIDTH, height);
    }
}
