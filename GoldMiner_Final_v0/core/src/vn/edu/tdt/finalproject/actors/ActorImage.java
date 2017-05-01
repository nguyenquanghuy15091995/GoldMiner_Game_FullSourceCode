package vn.edu.tdt.finalproject.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import vn.edu.tdt.finalproject.utils.ScreenConstants;

public class ActorImage extends Actor{
    private Texture texture;
    private Sprite spriteWin;
    private float rotationSpeed;
    private boolean isRotationNormal;
    private boolean isRotationWave;
    private float maxRotationAngle;
    private boolean isChangeSizeEffect;
    private float changeSizeSpeed;
    private float maxChangeSizeRadius;
    private float[] firstShape;
    private boolean isMoveLeft;
    private boolean isMoveRight;
    private float moveSpeed;
    private float moveRange;


    public ActorImage(String path, float x, float y, float width, float height){
        rotationSpeed = 3;
        isRotationNormal = false;
        isRotationWave = false;
        isChangeSizeEffect = false;
        maxRotationAngle = 45;
        changeSizeSpeed = 0;
        maxChangeSizeRadius = 0;
        isMoveLeft = false;
        isMoveRight = false;
        moveSpeed = 0;
        moveRange = 0;

        setPosition(x, y);
        setSize(width, height);
        setOrigin(getOriginX() + getWidth()/2, getOriginY() + getHeight()/2);

        firstShape = new float[4];
        firstShape[0] = x;
        firstShape[1] = y;
        firstShape[2] = width;
        firstShape[3] = height;

        texture = new Texture(path);
        spriteWin = new Sprite(texture);

        spriteWin.setPosition(getX(), getY());
        spriteWin.setSize(getWidth(), getHeight());
        spriteWin.setOrigin(getOriginX(), getOriginY());
    }

    public void setNewFirstShape(float x, float y, float width, float height){
        firstShape[0] = x;
        firstShape[1] = y;
        firstShape[2] = width;
        firstShape[3] = height;
    }

    public void setRotationSpeed(float rotationSpeed){
        this.rotationSpeed = rotationSpeed;
    }

    public void setMaxRotationAngle(float maxRotationAngle) {
        this.maxRotationAngle = maxRotationAngle;
    }

    public void setChangeSizeEffectOn(float changeSizeSpeed, float maxChangeSizeRadius) {
        this.maxChangeSizeRadius = maxChangeSizeRadius;
        this.changeSizeSpeed = changeSizeSpeed;
        isChangeSizeEffect = true;
    }

    public void setChangeSizeEffectOff() {
        maxChangeSizeRadius = 0;
        this.changeSizeSpeed = 0;
        isChangeSizeEffect = false;
        resetToFirstShape();
    }

    public void setRotationNormal(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
        isRotationNormal = true;
        isRotationWave = false;
    }

    public void setRotationWave(float rotationSpeed, float maxRotationAngle) {
        this.rotationSpeed = rotationSpeed;
        this.maxRotationAngle = maxRotationAngle;
        setRotation(0);
        spriteWin.setRotation(0);
        isRotationWave = true;
        isRotationNormal = false;
    }

    public void setNonRotation(){
        isRotationWave = false;
        isRotationNormal = false;
        setRotation(0);
        spriteWin.setRotation(0);
    }

    public void setMoveRight(float moveSpeed, float moveRange){
        this.moveSpeed = moveSpeed * ScreenConstants.TRANSFORM_X;
        this.moveRange = moveRange;// * ScreenConstants.TRANSFORM_X;
        this.isMoveRight = true;
        this.isMoveLeft = false;
    }

    public void setMoveLeft(float moveSpeed, float moveRange){
        this.moveSpeed = moveSpeed * ScreenConstants.TRANSFORM_X;
        this.moveRange = moveRange;// * ScreenConstants.TRANSFORM_X;
        this.isMoveRight = false;
        this.isMoveLeft = true;
    }

    public void setNonMoveLefRight(){
        this.isMoveRight = false;
        this.isMoveLeft = false;
    }

    private void resetToFirstShape(){
        setPosition(firstShape[0], firstShape[1]);
        setSize(firstShape[2], firstShape[3]);
        spriteWin.setPosition(firstShape[0], firstShape[1]);
        spriteWin.setSize(firstShape[2], firstShape[3]);
    }

    private void updateChangeSizeEffect(){
        if(isChangeSizeEffect){
            spriteWin.setX(spriteWin.getX() - changeSizeSpeed * ScreenConstants.TRANSFORM_X);
            spriteWin.setY(spriteWin.getY() - changeSizeSpeed * ScreenConstants.TRANSFORM_Y);
            spriteWin.setSize(spriteWin.getWidth() + 2*changeSizeSpeed*ScreenConstants.TRANSFORM_X, spriteWin.getHeight());
            spriteWin.setSize(spriteWin.getWidth(), spriteWin.getHeight() + 2*changeSizeSpeed*ScreenConstants.TRANSFORM_Y);


            if(Math.abs(firstShape[0] - spriteWin.getX()) >= maxChangeSizeRadius
                    || Math.abs(firstShape[1] - spriteWin.getY()) >= maxChangeSizeRadius
                    || Math.abs(firstShape[2] - spriteWin.getWidth()) >= maxChangeSizeRadius
                    || Math.abs(firstShape[3] - spriteWin.getHeight()) >= maxChangeSizeRadius){
                changeSizeSpeed *= -1;
            }

        }
    }

    private void updateRotationNormal(){
        if(isRotationNormal){
            spriteWin.setRotation(spriteWin.getRotation() + rotationSpeed);
            setRotation(getRotation() + rotationSpeed);
        }
    }

    private void updateRotationWave(){
        if(isRotationWave){
            if(spriteWin.getRotation() >= maxRotationAngle || spriteWin.getRotation() <= (-1)*maxRotationAngle){
                rotationSpeed *= -1;
            }
            spriteWin.setRotation(spriteWin.getRotation() + rotationSpeed);
            setRotation(getRotation() + rotationSpeed);
        }
    }

    private void updateMoveLeftRight(){
        if(moveSpeed <= 0 || moveRange <= 0){
            return;
        }
        if(isMoveLeft){
            if(Math.abs(getX() - firstShape[0]) < moveRange) {
                spriteWin.setX(spriteWin.getX() - moveSpeed);
                setX(getX() - moveSpeed);
            }
            if(Math.abs(getX() - firstShape[0]) > moveRange){
                spriteWin.setX(firstShape[0] - moveRange);
                setX(firstShape[0] - moveRange);
            }
        }
        if(isMoveRight){
            if(Math.abs(getX() - firstShape[0]) < moveRange) {
                spriteWin.setX(spriteWin.getX() + moveSpeed);
                setX(getX() + moveSpeed);
            }
            if(Math.abs(getX() - firstShape[0]) > moveRange){
                spriteWin.setX(firstShape[0] + moveRange);
                setX(firstShape[0] + moveRange);
            }
        }
    }

    @Override
    public boolean remove() {
        texture.dispose();
        return super.remove();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateRotationNormal();
        updateRotationWave();
        updateChangeSizeEffect();
        updateMoveLeftRight();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        spriteWin.draw(batch);
    }
}
