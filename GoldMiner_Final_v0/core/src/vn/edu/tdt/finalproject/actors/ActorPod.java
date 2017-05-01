package vn.edu.tdt.finalproject.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import vn.edu.tdt.finalproject.Application;
import vn.edu.tdt.finalproject.sounds.SoundEffect;
import vn.edu.tdt.finalproject.utils.PlayerInfo;
import vn.edu.tdt.finalproject.utils.ScreenConstants;


/**
 * Actor of pod: claw, rope,...
 */
public class ActorPod extends Actor {

    /**
     * state of pod.
     */
    public enum PodState {
        FREEZE,
        ROTATION,
        SHOOT,
        REWIND
    }

    private float ROPE_WIDTH = 2;
    private float ROPE_HEIGHT = 3;

    /**
     * Texture of rope and claw.
     */
    private Texture claw;
    private Texture rope;

    /**
     * Use to move and rotation.
     */
    private float moveSpeed;
    private float moveSpeedReset;
    private int rotationSpeed;

    /**
     * Use to do all action of Pod.
     */
    private Sprite spriteClaw;
    private Sprite spriteRope;

    /**
     * Use to change State.
     */
    private PodState podState;

    /**
     * Use to Remember first position.
     * [0] = x.
     * [1] = y.
     */
    private float[] currPoint;

    /**
     * Sound of pod action.
     */
    private SoundEffect shootSound;
    private SoundEffect rewindSound;

    /**
     * Constructor.
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public ActorPod(float x, float y, float width, float height){

        //create texture.
        claw = new Texture("images/textureobjects/claw.png");
        rope = new Texture("images/textureobjects/rope.png");

        //create speed.
        rotationSpeed = 1;// * (int) ScreenConstants.TRANSFORM_Y;
        moveSpeed = 3 * ScreenConstants.TRANSFORM_Y  + PlayerInfo.getBag().getSodaPower();
        moveSpeedReset = moveSpeed;

        //Actor setting.
        setPosition(x, y);
        setSize(width, height);
        setOrigin(getOriginX() + getWidth()/2, getOriginY() + getHeight());

        //Sprite create.
        spriteRope = new Sprite(rope);
        spriteClaw = new Sprite(claw);

        //sprite claw setting.
        spriteClaw.setPosition(x, y);
        spriteClaw.setSize(width, height);
        spriteClaw.setOrigin(getOriginX(), getOriginY());

        //sprite rope setting.
        spriteRope.setSize(ROPE_WIDTH, ROPE_HEIGHT);
        spriteRope.setPosition(getX() + getWidth()/2 - spriteRope.getWidth()/2, getY() + getHeight() - spriteRope.getHeight());
        spriteRope.setOrigin(spriteRope.getWidth()/2,spriteRope.getHeight());
        spriteRope.setRotation(180);

        //save first Position.
        currPoint = new float[2];
        currPoint[0] = getX();
        currPoint[1] = getY();

        //sound setting.
        shootSound = new SoundEffect("sounds/crank.ogg", 1f);
        shootSound.setSoundKind(SoundEffect.SoundKind.DURING);
//        shootSound.playSoundLoopOnAndroid();
//        shootSound.pausePlay();
        rewindSound = new SoundEffect("sounds/rewind.ogg", 1f);
        rewindSound.setSoundKind(SoundEffect.SoundKind.DURING);
//        rewindSound.playSoundLoopOnAndroid();
//        rewindSound.pausePlay();

        System.out.println(x + " - " + y);
        System.out.println(currPoint[0] + " - " + currPoint[1]);
        //first State.
        podState = PodState.ROTATION;
    }

    private void resetAllSprite(){
        //sprite claw setting.
        spriteClaw.setPosition(getX(), getY());
        spriteClaw.setSize(getWidth(), getHeight());
        spriteClaw.setOrigin(getOriginX(), getOriginY());
        spriteClaw.setRotation(getRotation());

        //sprite rope setting.
        spriteRope.setSize(ROPE_WIDTH, getHeight()+ moveSpeed);
        spriteRope.setPosition(getX() + getWidth()/2 - ROPE_WIDTH/2, getY() + getHeight() - ROPE_HEIGHT + moveSpeed);
        spriteRope.setOrigin(spriteRope.getWidth()/2,ROPE_HEIGHT);
        spriteRope.setRotation(getRotation() + 180);
    }

    private void resetPosition(){
        setRotation(0);
        setPosition(currPoint[0], currPoint[1] - moveSpeed);
    }

    /**
     * use after catch sth.
     */
    public void resetMoveSpeed(){
        moveSpeed = moveSpeedReset;
    }

    /**
     * Get current move speed.
     * @return current move speed.
     */
    public float getMoveSpeed() {
        return moveSpeed;
    }

    /**
     * Change speed.
     * @param moveSpeed new speed.
     */
    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    /**
     * set State.
     * @param podState next State.
     */
    public void setPodState(PodState podState) {
        this.podState = podState;
    }

    /**
     * Call current State.
     * @return current State.
     */
    public PodState getPodState() {
        return podState;
    }

    public SoundEffect getRewindSound() {
        return rewindSound;
    }

    /**
     * check action per delta time.
     * @param delta delta time.
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        //FREEZE: do nothing.
        if (podState.equals(PodState.FREEZE)) {
            return;
        }

        //ROTATION: rotate claw and rope.
        if (podState.equals(PodState.ROTATION)) {
            if(rewindSound.getSoundKind().equals(SoundEffect.SoundKind.DISABLE)) {
                rewindSound.setSoundKind(SoundEffect.SoundKind.DURING);
            }
            if(shootSound.getSoundKind().equals(SoundEffect.SoundKind.DISABLE)){
                shootSound.setSoundKind(SoundEffect.SoundKind.DURING);
            }
//            shootSound.pausePlay();
//            rewindSound.pausePlay();
            updateRotation();
        }

        //SHOOT: move claw and scale rope.
        if (podState.equals(PodState.SHOOT)) {
            if(checkPodEdge()) {
                podState = PodState.REWIND;
                rewindSound.setSoundKind(SoundEffect.SoundKind.DURING);
                shootSound.stopPlay();
                shootSound.pausePlay();
            }
            shootSound.playSoundLoopOnAndroid();
            shootSound.resumePlay();
            shootPod();
        }

        //REWIND: move claw and scale rope back to the first state.
        if (podState.equals(PodState.REWIND)) {

            if(getY() >= currPoint[1]) {
                podState = PodState.ROTATION;
                resetMoveSpeed();
                resetPosition();
                resetAllSprite();
                rewindSound.stopPlay();
                rewindSound.setSoundKind(SoundEffect.SoundKind.DISABLE);
                shootSound.setSoundKind(SoundEffect.SoundKind.DISABLE);
                //rewindSound.pausePlay();
            }

            shootSound.stopPlay();
            rewindSound.playSoundLoopOnAndroid();
//            shootSound.pausePlay();
//            rewindSound.resumePlay();
            rewindPod();
        }
    }

    public void muteAllSound(){
        shootSound.stopPlay();
        rewindSound.stopPlay();
    }

    public void unmuteAllSound(){
        if(podState.equals(PodState.SHOOT)){
            shootSound.setSoundKind(SoundEffect.SoundKind.DURING);
            shootSound.playSoundLoopOnAndroid();
        }
        if(podState.equals(PodState.REWIND)){
            rewindSound.setSoundKind(SoundEffect.SoundKind.DURING);
            rewindSound.playSoundLoopOnAndroid();
        }
    }

    /**
     * check Pod Edge to rewind.
     *  podA-----podB
     *   |        |
     *   |        |
     *  podC-----podD
     */
    private boolean checkPodEdge(){
        if(getX() < 0 || getY()+getHeight() < 0
                || getX() + getWidth() > Application.DESKTOP_WIDTH || getY() + getHeight() > Application.DESKTOP_HEIGHT){
            return true;
        }
        return false;
    }

    /**
     * update angle.
     */
    private void updateRotation(){
        if (spriteClaw.getRotation() >= 85 || spriteClaw.getRotation() <= -85) {
            rotationSpeed *= -1;
        }
        setRotation(getRotation() + rotationSpeed);
        spriteClaw.setRotation(spriteClaw.getRotation() + rotationSpeed);
        spriteRope.setRotation(spriteRope.getRotation() + rotationSpeed);
    }

    /**
     * update position of pod.
     */
    private void shootPod(){
        setX(getX() + moveSpeed*(float) Math.cos(Math.toRadians(spriteClaw.getRotation()-90)));
        setY(getY() + moveSpeed*(float) Math.sin(Math.toRadians(spriteClaw.getRotation()-90)));
        spriteClaw.setX(spriteClaw.getX() + moveSpeed*(float) Math.cos(Math.toRadians(spriteClaw.getRotation()-90)));
        spriteClaw.setY(spriteClaw.getY() + moveSpeed*(float) Math.sin(Math.toRadians(spriteClaw.getRotation()-90)));
        spriteRope.setSize(spriteRope.getWidth(), calculateRopeLength() );

    }

    /**
     * update position of pod (go back first position).
     */
    private void rewindPod() {
        setX(getX() - moveSpeed*(float) Math.cos(Math.toRadians(spriteClaw.getRotation()-90)));
        setY(getY() - moveSpeed*(float) Math.sin(Math.toRadians(spriteClaw.getRotation()-90)));
        spriteClaw.setX(spriteClaw.getX() - moveSpeed*(float) Math.cos(Math.toRadians(spriteClaw.getRotation()-90)));
        spriteClaw.setY(spriteClaw.getY() - moveSpeed*(float) Math.sin(Math.toRadians(spriteClaw.getRotation()-90)));
        spriteRope.setSize(spriteRope.getWidth(), calculateRopeLength());
    }

    /**
     * update the rope length.
     * @return the length.
     */
    private float calculateRopeLength(){
        float x = getX() + getWidth()/2;
        float y = getY() + getHeight()/2;
        float deltaX = x - (spriteRope.getX() + spriteRope.getWidth()/2);
        float deltaY = y - (spriteRope.getY());
        return (float) Math.sqrt(deltaX*deltaX + deltaY*deltaY);
    }

    /**
     * dispose all texture.
     * @return remove or not.
     */
    @Override
    public boolean remove() {
        claw.dispose();
        rope.dispose();
        shootSound.dispose();
        rewindSound.dispose();
        return super.remove();
    }

    /**
     * draw per frame.
     * @param batch use to draw.
     * @param parentAlpha .
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        spriteClaw.draw(batch);
        spriteRope.draw(batch);
    }
}
