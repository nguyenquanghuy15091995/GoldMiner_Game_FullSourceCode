package vn.edu.tdt.finalproject;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import vn.edu.tdt.finalproject.managers.GameScreenManager;
import vn.edu.tdt.finalproject.utils.ShopConstants;
import vn.edu.tdt.finalproject.utils.SplashDoors;

public class Application extends Game {
	public static int ANDROID_NUM = 60;
	public static int DESKTOP_WIDTH = ANDROID_NUM*16;
	public static int DESKTOP_HEIGHT = ANDROID_NUM*9;

	public AssetManager asset;
	public SpriteBatch batch;
	public ShapeRenderer shapeBatch;
	public GameScreenManager gsm;
	
	@Override
	public void create () {
		asset = new AssetManager();
		shapeBatch = new ShapeRenderer();
		batch = new SpriteBatch();
		gsm = new GameScreenManager(this);
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		shapeBatch.dispose();
		SplashDoors.dispose();
		ShopConstants.disposeAllItem();
		asset.dispose();
		gsm.dispose();
	}
}
