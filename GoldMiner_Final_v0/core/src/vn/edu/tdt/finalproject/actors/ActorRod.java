package vn.edu.tdt.finalproject.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;

import vn.edu.tdt.finalproject.animations.AnimationCustom;
import vn.edu.tdt.finalproject.sounds.SoundEffect;
import vn.edu.tdt.finalproject.utils.PlayerInfo;
import vn.edu.tdt.finalproject.utils.ScreenConstants;
import vn.edu.tdt.finalproject.utils.TextConstants;

public class ActorRod extends Actor {

    public enum RodTag{
        GOLD_500, GOLD_250, GOLD_100, GOLD_50,
        ROCK_20, ROCK_10,
        DINAMOND_650
    }

    public enum RodState{
        FREEZE,
        CATCHED,
        DISABLED,
        TRASH,
        EXPLODED
    }

    private RodTag tag;
    private float weight;
    private int money;
    private Sprite sprite;
    private RodState rodState;
    private float moveSpeed;
    private Texture textureRod;
    private SoundEffect rodSound;
    private AnimationCustom rodExplosionAnimation;
    private float[] explosionPos;
    private SoundEffect explosionSound;
    private SoundEffect eatBonusSound;


    public ActorRod(float x, float y, float width, float height, RodTag tag){

        this.tag = tag;

        this.rodState = RodState.FREEZE;

        createRodPhysical();

        setPosition(x, y);

        this.sprite = new Sprite(textureRod);

        sprite.setPosition(x,y);
        setSize(width, height);
        sprite.setSize(getWidth(), getHeight());

        scaleBound();



        rodExplosionAnimation = new AnimationCustom("animations/explosions/rodexplosion.atlas", 6, x, y, width, height);
        explosionPos = new float[4];
        explosionSound = new SoundEffect("sounds/explode.ogg");

        eatBonusSound = new SoundEffect("sounds/eatbonus.ogg");
    }

    public RodState getRodState() {
        return rodState;
    }

    public RodTag getTag() {
        return tag;
    }

    public float getWeight() {
        return weight * ScreenConstants.TRANSFORM_Y;
    }

    public int getMoney() {
        return money;
    }

    public void setMoveSpeed(float moveSpeed) {
        this.moveSpeed = moveSpeed;
    }

    public Sprite getSprite() {
        return sprite;
    }

    private void createRodPhysical(){
        if(tag.equals(RodTag.GOLD_500)){
            money = 500;
            weight = 2.6f * ScreenConstants.TRANSFORM_Y;
            textureRod = new Texture("images/textureobjects/gold500.png");
            rodSound = new SoundEffect("sounds/bigmoney.ogg");
            return;
        }
        if(tag.equals(RodTag.GOLD_250)){
            money = 250;
            weight = 2.3f * ScreenConstants.TRANSFORM_Y;
            textureRod = new Texture("images/textureobjects/gold250.png");
            rodSound = new SoundEffect("sounds/bigmoney.ogg");
            return;
        }
        if(tag.equals(RodTag.GOLD_100)){
            money = 100;
            weight = 1.5f * ScreenConstants.TRANSFORM_Y;
            textureRod = new Texture("images/textureobjects/gold100.png");
            rodSound = new SoundEffect("sounds/gold.ogg");
            return;
        }
        if(tag.equals(RodTag.GOLD_50)){
            money = 50;
            weight = 1f * ScreenConstants.TRANSFORM_Y;
            textureRod = new Texture("images/textureobjects/gold50.png");
            rodSound = new SoundEffect("sounds/gold.ogg");
            return;
        }
        if(tag.equals(RodTag.DINAMOND_650)){
            money = 650;
            weight = 1f * ScreenConstants.TRANSFORM_Y;
            textureRod = new Texture("images/textureobjects/dinamond.png");
            rodSound = new SoundEffect("sounds/bigmoney.ogg");
            return;
        }
        if(tag.equals(RodTag.ROCK_20)){
            money = 20;
            weight = 2.7f * ScreenConstants.TRANSFORM_Y;
            textureRod = new Texture("images/textureobjects/rock20.png");
            rodSound = new SoundEffect("sounds/rock.ogg");
            return;
        }
        if(tag.equals(RodTag.ROCK_10)){
            money = 10;
            weight = 1.5f * ScreenConstants.TRANSFORM_Y;
            textureRod = new Texture("images/textureobjects/rock10.png");
            rodSound = new SoundEffect("sounds/rock.ogg");
            return;
        }
    }

    private void moveRod() {
        setX(getX() - moveSpeed*(float) Math.cos(Math.toRadians(sprite.getRotation()-90)));
        setY(getY() - moveSpeed*(float) Math.sin(Math.toRadians(sprite.getRotation()-90)));
        sprite.setX(sprite.getX() - moveSpeed*(float) Math.cos(Math.toRadians(sprite.getRotation()-90)));
        sprite.setY(sprite.getY() - moveSpeed*(float) Math.sin(Math.toRadians(sprite.getRotation()-90)));
    }

    public void updateCollisionWithPod(ActorPod pod, ActorText talkingText) {

        eatBonusSound.playSound();
        if(rodState.equals(RodState.DISABLED)){
            return;
        }
        if(rodState.equals(RodState.TRASH)){
            rodState = RodState.DISABLED;
            remove();
            return;
        }
        if(checkCatchRod(pod)){
            if(rodState.equals(RodState.FREEZE) && pod.getPodState().equals(ActorPod.PodState.SHOOT)) {
                fixPosition(pod);
                pod.setMoveSpeed(pod.getMoveSpeed() - weight);
                pod.setPodState(ActorPod.PodState.REWIND);
                setMoveSpeed(pod.getMoveSpeed());
                rodState = RodState.CATCHED;
                rodSound.setSoundKind(SoundEffect.SoundKind.ONE_TIME);
                rodSound.playSound();
                return;
            }


        }
        if(rodState.equals(RodState.CATCHED) && pod.getPodState().equals(ActorPod.PodState.ROTATION)){
            eatBonusSound.setSoundKind(SoundEffect.SoundKind.ONE_TIME);
            PlayerInfo.setCurrentMoney(PlayerInfo.getCurrentMoney() + money);

            talkingText.setText("Oh yeah!$" + money + "...");
            talkingText.setTextState(ActorText.TextState.FREEZE);
            TextConstants.setTakingStartTimeShow(TimeUtils.millis()/1000);
            setPosition(0 - getWidth(), 0 - getHeight());
            setRotation(0);
            rodState = RodState.TRASH;
            return;
        }
    }

    public void updateCollisionWithBomb(ActorPod acPod, ActorBomb acBomb){

        acBomb.updateCollisionWithPod(acPod);
        if(rodState.equals(RodState.CATCHED) && acBomb.getBombState().equals(ActorBomb.BombState.BURST)){
            rodState = RodState.EXPLODED;
            explosionSound.setSoundKind(SoundEffect.SoundKind.ONE_TIME);
            explosionSound.playSound();
            acPod.setMoveSpeed(40);
            synchronizeExplosionWithRod(acPod);
            acBomb.setBombState(ActorBomb.BombState.FREEZE);
        }
    }

    //       |O|
    // pod_A------pod_B
    //  | ********* |
    //  | * Catch * |
    //  | ********* |
    // pod_C------pod_D
    //
    // bound < real.
    // scale number must be < 50% (< 0.5f).
    // if scale number > 50% then top,bottom, left, right will be reversed.

    private float scaleNum = 0.25f;
    private float left;
    private float right;
    private float bottom;
    private float top;

    public void scaleBound(){
        left = getX() + getWidth()*scaleNum;
        right = getX() + getWidth()*(1f - scaleNum);
        bottom = getY() + getHeight()*scaleNum;
        top = getY() + getHeight()*(1f - scaleNum);
    }

    private boolean checkCatchRod(ActorPod pod) {
        if(checkCatchPodA(pod) || checkCatchPodB(pod) || checkCatchPodC(pod) || checkCatchPodD(pod)){
            return true;
        }
        return false;
    }

    private boolean checkCatchPodA(ActorPod pod) {
        if (left < pod.getX() && pod.getX() < right
                && bottom < pod.getY() + pod.getHeight() && pod.getY() + pod.getHeight() < top){
            return true;
        }
        return false;
    }

    private boolean checkCatchPodB(ActorPod pod) {
        if (left < pod.getX() + pod.getWidth() && pod.getX() + pod.getWidth() < right
                && bottom < pod.getY() + pod.getHeight() && pod.getY() + pod.getHeight() < top){
            return true;
        }
        return false;
    }

    private boolean checkCatchPodC(ActorPod pod) {
        if (left < pod.getX() && pod.getX() < right
                && bottom < pod.getY() && pod.getY() < top){
            return true;
        }
        return false;
    }

    private boolean checkCatchPodD(ActorPod pod) {
        if (left < pod.getX() + pod.getWidth() && pod.getX() + pod.getWidth() < right
                && bottom < pod.getY() && pod.getY() < top){
            return true;
        }
        return false;
    }


    private void fixPosition(ActorPod pod) {
        float rotTemp = pod.getRotation();
        pod.setRotation(0);
        setRotation(0);
        sprite.setRotation(0);

        setX(pod.getX() + pod.getWidth()/2 - getWidth()/2);
        sprite.setX(pod.getX() + pod.getWidth()/2 - getWidth()/2);
        setY(pod.getY() - getHeight()*(1f-scaleNum));
        sprite.setY(pod.getY() - getHeight()*(1f-scaleNum));

        pod.setRotation(rotTemp);

        setOrigin(getOriginX() + getWidth()/2, getOriginY() + getHeight()*(1f-scaleNum) + pod.getHeight());
        sprite.setOrigin(getOriginX(), getOriginY());

        setRotation(rotTemp);
        sprite.setRotation(rotTemp);
    }

    private void synchronizeExplosionWithRod(ActorPod acPod){

        explosionPos[2] = getWidth();
        explosionPos[3] = getHeight();

        explosionPos[0] = acPod.getX() + acPod.getWidth()/2 - getWidth()/2;
        explosionPos[1] = acPod.getY() + acPod.getHeight() - getHeight()/2;

        float avgTemp = scaleNum*(getWidth() + getHeight());

        explosionPos[0] = explosionPos[0] + avgTemp*(float) Math.cos(Math.toRadians(getRotation()-90));
        explosionPos[1] = explosionPos[1] + avgTemp*(float) Math.sin(Math.toRadians(getRotation()-90));
    }

    @Override
    public boolean remove() {
        rodSound.dispose();
        rodExplosionAnimation.dispose();
        textureRod.dispose();
        explosionSound.dispose();
        return super.remove();
    }

    @Override
    public void act(float delta) {
        if (rodState.equals(RodState.CATCHED)) {
            moveRod();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {

        if(rodState.equals(RodState.FREEZE) || rodState.equals(RodState.CATCHED)) {
            sprite.draw(batch);
        }
        if(rodState.equals(RodState.EXPLODED)){
            explosionPos[0] -= 0.5;
            explosionPos[1] -= 0.5;
            explosionPos[2] += 1;
            explosionPos[3] += 1;
            rodExplosionAnimation.drawExplosion(batch, explosionPos[0], explosionPos[1], explosionPos[2], explosionPos[3]);
            if(rodExplosionAnimation.getTimePassed() > 1){
                rodState = RodState.TRASH;
                setPosition(0 - getWidth(), 0 - getHeight());
                setRotation(0);
            }
        }

    }
}
