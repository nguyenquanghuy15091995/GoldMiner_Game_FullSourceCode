package vn.edu.tdt.finalproject.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * setting animation for game.
 */
public class AnimationCustom {

    /**
     * Animation component.
     */
    private TextureAtlas atlas;
    private Animation<TextureRegion> animation;
    private float timePassed;

    /**
     * Location.
     */
    private float x;
    private float y;
    private float width;
    private float height;

    /**
     * Constructor.
     * @param atlasPath path of atlas.
     * @param fps frame per second.
     * @param x position x.
     * @param y position y.
     * @param width texture width.
     * @param height texture height.
     */
    public AnimationCustom(String atlasPath, float fps, float x, float y, float width, float height){

        //Animation component setting.
        atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        animation = new Animation(1/(fps*1.0f), atlas.getRegions());
        timePassed = 0;

        //Location setting.
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Get current X.
     * @return current X.
     */
    public float getX() {
        return x;
    }

    /**
     * Change X.
     * @param x new X position.
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Get current Y.
     * @return current Y;
     */
    public float getY() {
        return y;
    }

    /**
     * Change Y;
     * @param y new Y position.
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Get current Width.
     * @return current Width.
     */
    public float getWidth() {
        return width;
    }

    /**
     * Change Width.
     * @param width new Width.
     */
    public void setWidth(float width) {
        this.width = width;
    }

    /**
     * Get current height.
     * @return current height.
     */
    public float getHeight() {
        return height;
    }

    /**
     * Change height.
     * @param height new height.
     */
    public void setHeight(float height) {
        this.height = height;
    }

    /**
     * Change Fps.
     * @param fps new fps.
     */
    public void setFPS(float fps) {
        animation.setFrameDuration(fps);
    }

    /**
     * Get current Fps.
     * @return current Fps.
     */
    public float getFPS() {
        return animation.getFrameDuration();
    }

    public float getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(float timePassed) {
        this.timePassed = timePassed;
    }

    /**
     * Display animation.
     * @param batch to draw frame.
     * @param isLooping looping frame or not.
     */
    public void draw(Batch batch, boolean isDoAnimation, boolean isLooping) {
        if(isDoAnimation) {
            timePassed += Gdx.graphics.getDeltaTime();
        }
        if(!isDoAnimation && !isLooping) {
            timePassed = 0;
        }
        batch.draw(animation.getKeyFrame(timePassed, isLooping), x, y, width, height);
    }

    public void drawButton(Batch batch, boolean isTouched){
        timePassed = 0;
        if(isTouched){
            timePassed = 1;
        }
        batch.draw(animation.getKeyFrame(timePassed), x, y, width, height);
    }

    public void drawExplosion(Batch batch, float x, float y, float width, float height){
        timePassed += Gdx.graphics.getDeltaTime();
        batch.draw(animation.getKeyFrame(timePassed), x, y, width, height);
    }

    /**
     * Dispose atlas to release memory.
     */
    public void dispose() {
        atlas.dispose();
    }
}
