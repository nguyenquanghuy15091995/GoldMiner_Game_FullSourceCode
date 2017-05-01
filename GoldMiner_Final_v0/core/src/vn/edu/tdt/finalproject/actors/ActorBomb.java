package vn.edu.tdt.finalproject.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import vn.edu.tdt.finalproject.animations.AnimationCustom;

public class ActorBomb extends Actor{
    public enum BombState{
        FREEZE,
        MOVE,
        BURST
    }

    private BombState bombState;

    private float moveSpeed;

    private float rotationSpeed;

    private Sprite spriteBoom;
    private Texture texture;

    private float[] firstPoint;

    public ActorBomb(float x, float y, float width, float height){

        rotationSpeed = 15;
        moveSpeed = 15;
        bombState = BombState.FREEZE;

        setPosition(x, y);
        setSize(width, height);
        setOrigin(getOriginX() + getWidth()/2, getOriginY() + getHeight()/2);

        texture = new Texture("images/textureobjects/bomb.png");
        spriteBoom = new Sprite(texture);
        spriteBoom.setPosition(x, y);
        spriteBoom.setSize(width, height);
        spriteBoom.setOrigin(getOriginX(), getOriginY());

        firstPoint = new float[4];
        firstPoint[0] = getX();
        firstPoint[1] = getY();
        firstPoint[2] = getWidth();
        firstPoint[3] = getHeight();
    }

    public void setBombState(BombState bombState) {
        this.bombState = bombState;
    }

    public BombState getBombState() {
        return bombState;
    }

    private void updateSpriteRotation(){
        spriteBoom.setRotation(spriteBoom.getRotation() + rotationSpeed);
    }

    private void resetBombPosition(){
        setPosition(firstPoint[0], firstPoint[1]);
        setSize(firstPoint[2], firstPoint[3]);
        spriteBoom.setPosition(firstPoint[0], firstPoint[1]);
    }

    public void updateCollisionWithPod(ActorPod acPod){
        if(getY() < acPod.getY() && bombState.equals(BombState.MOVE)){

            bombState = BombState.BURST;
        }
    }

    private void moveBomb(){
        setX(getX() + moveSpeed*(float) Math.cos(Math.toRadians(getRotation()-90)));
        setY(getY() + moveSpeed*(float) Math.sin(Math.toRadians(getRotation()-90)));
        spriteBoom.setX(getX() + moveSpeed*(float) Math.cos(Math.toRadians(getRotation()-90)));
        spriteBoom.setY(getY() + moveSpeed*(float) Math.sin(Math.toRadians(getRotation()-90)));
    }

    @Override
    public boolean remove() {
        texture.dispose();
        return super.remove();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(bombState.equals(BombState.MOVE)){
            updateSpriteRotation();
            moveBomb();
        }
        if(bombState.equals(BombState.FREEZE)){
            resetBombPosition();
            setRotation(0);
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        //spriteBoom.draw(batch);
        if(bombState.equals(BombState.MOVE)){
            System.out.println("bomb go move");
            spriteBoom.draw(batch);
        }
//        if(bombState.equals(BombState.BURST)){
//           // setPosition(getX() - 1, getY() - 1);
//           // setSize(getWidth() + 1, getHeight() + 1);
//            explosion.drawExplosion(batch, getX(), getY(), getWidth(), getHeight());
//            System.out.println("bomb go burst");
//            if(explosion.getTimePassed() >= 1){
//                System.out.println("bomb go freeze");
//                bombState = BombState.FREEZE;
//            }
//        }
    }
}
