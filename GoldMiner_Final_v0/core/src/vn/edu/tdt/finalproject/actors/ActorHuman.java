package vn.edu.tdt.finalproject.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import vn.edu.tdt.finalproject.animations.AnimationCustom;
import vn.edu.tdt.finalproject.utils.ScreenConstants;

/**
 * display human and change human's animation.
 */
public class ActorHuman extends Actor {

    /**
     * Animation.
     */
    private AnimationCustom humanAnimation;

    /**
     * Run animation's frame or not.
     */
    private boolean isDoAnimation;

    /**
     * Run animation during or one time.
     */
    private boolean isLooping;

    /**
     * The claw.
     */
    private ActorPod acPod;

    private ActorBomb acBomb;

    /**
     * Constructor.
     */
    public ActorHuman(){
        humanAnimation = new AnimationCustom("animations/human/humanrolling.atlas", 5,
                ScreenConstants.HUMAN_X, ScreenConstants.HUNAM_Y,
                ScreenConstants.HUMAN_WIDTH, ScreenConstants.HUNAM_HEIGHT);
        isDoAnimation = false;
        isLooping = false;

        //actor human setting.
        setPosition(ScreenConstants.HUMAN_X, ScreenConstants.HUNAM_Y);
        setSize(ScreenConstants.HUMAN_WIDTH, ScreenConstants.HUNAM_HEIGHT);

        //actor pod setting.
        //human width = 5 * pod width.
        //human height = 4 * pod height.
        //pod's location is the bottom-center of human.
        //
        //  **********
        //  *  ----  *
        //  **|pod |**
        //     ----
        acPod = new ActorPod(ScreenConstants.HUMAN_X + ScreenConstants.HUMAN_WIDTH/2 - ScreenConstants.HUMAN_WIDTH/8,
                ScreenConstants.HUNAM_Y - ScreenConstants.HUNAM_HEIGHT/5,
                ScreenConstants.HUMAN_WIDTH/4, ScreenConstants.HUNAM_HEIGHT/4);

        acBomb = new ActorBomb(acPod.getX(), acPod.getY(), acPod.getWidth(), acPod.getHeight());
    }

    /**
     * Get current animation.
     * @return current animation.
     */
    public AnimationCustom getHumanAnimation() {
        return humanAnimation;
    }

    public ActorBomb getAcBomb() {
        return acBomb;
    }

    /**
     * Get actor pod.
     * @return current actor pod.
     */
    public ActorPod getAcPod() {
        return acPod;
    }

    /**
     * update animation run one time or during or freeze.
     */
    public void updateAnimationState(){
        //rolling during time.
        if(acPod.getPodState().equals(ActorPod.PodState.REWIND)){
            isDoAnimation = true;
            isLooping = true;
            humanAnimation.setFPS(1/8f);
            return;
        }
        //rolling one time (look like shoot claw).
        if(acPod.getPodState().equals(ActorPod.PodState.SHOOT)){
            isDoAnimation = true;
            isLooping = false;
            humanAnimation.setFPS(1/100f);
            return;
        }
        //do nothing.
        else {
            isDoAnimation = false;
            isLooping = false;
            return;
        }
    }

    /**
     * Dispose atlas to release memory.
     * @return remove or not.
     */
    @Override
    public boolean remove() {
        humanAnimation.dispose();
        acPod.remove();
        return super.remove();
    }

    /**
     * Check and do action of actor.
     * @param delta real time.
     */
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    /**
     * Draw actor' display.
     * @param batch to draw animation.
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        humanAnimation.draw(batch, isDoAnimation, isLooping);
    }
}
