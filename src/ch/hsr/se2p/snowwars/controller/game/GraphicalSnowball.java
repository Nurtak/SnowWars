package ch.hsr.se2p.snowwars.controller.game;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.model.ShotObject.ShotObjectState;
import ch.hsr.se2p.snowwars.view.BufferedImageLoader;

public class GraphicalSnowball extends GraphicalObject {
	private final static Logger logger = Logger.getLogger(GraphicalSnowball.class.getPackage().getName());

	public AnimationController splashingAnimation;
	public AnimationController normalSnowballAnimation;
	public AnimationController activeAnimation;

	private BufferedImage spriteSheetSnowball;
	public BufferedImageLoader loader;

	private int width = 35;
	private int height = 35;

	private final Shot shot;

	public GraphicalSnowball(Shot shot) {
		this.shot = shot;

		loader = BufferedImageLoader.getInstance();
		try {
			spriteSheetSnowball = loader.getSnowballSpriteSheet();
		} catch (IOException e1) {
			logger.error(e1.getMessage());
		}

		loadSplashAnimation();
		loadNormalSnowballAnimation();
		activeAnimation = normalSnowballAnimation;
	}

	public Image getImage() {
		return activeAnimation.getSprite();
	}

	public boolean isVisible() {
		ShotObjectState state = shot.getShotObject().getShotObjectState();
		return state == ShotObjectState.MOVING || state == ShotObjectState.CRASHEDINGROUND || state == ShotObjectState.CRASHING;
	}

	@Override
	public int getX() {
		return this.shot.getX();
	}

	@Override
	public int getY() {
		return this.shot.getY();
	}

	public Shot getShot(){
		return this.shot;
	}
	
	private void loadSplashAnimation() {
		ArrayList<BufferedImage> spritesForSplash = new ArrayList<BufferedImage>();

		spritesForSplash.add(spriteSheetSnowball.getSubimage(0, 0, width, height));
		spritesForSplash.add(spriteSheetSnowball.getSubimage(width, 0, width, height));
		spritesForSplash.add(spriteSheetSnowball.getSubimage(2 * width, 0, width, height));
		spritesForSplash.add(spriteSheetSnowball.getSubimage(3 * width, 0, width, height));
		spritesForSplash.add(spriteSheetSnowball.getSubimage(4 * width, 0, width, height));
		spritesForSplash.add(spriteSheetSnowball.getSubimage(5 * width, 0, width, height));
		spritesForSplash.add(spriteSheetSnowball.getSubimage(6 * width, 0, width, height));
		spritesForSplash.add(spriteSheetSnowball.getSubimage(7 * width, 0, width, height));
		spritesForSplash.add(spriteSheetSnowball.getSubimage(8 * width, 0, width, height));
		spritesForSplash.add(spriteSheetSnowball.getSubimage(9 * width, 0, width, height));
		spritesForSplash.add(spriteSheetSnowball.getSubimage(10 * width, 0, width, height));
		spritesForSplash.add(spriteSheetSnowball.getSubimage(11 * width, 0, width, height));

		splashingAnimation = new AnimationController(spritesForSplash);
		splashingAnimation.setSpeed(10);
	}

	private void loadNormalSnowballAnimation() {
		ArrayList<BufferedImage> spritesForNormalSnowball = new ArrayList<BufferedImage>();
		spritesForNormalSnowball.add(spriteSheetSnowball.getSubimage(0, 0, width, height));

		normalSnowballAnimation = new AnimationController(spritesForNormalSnowball);
		normalSnowballAnimation.setSpeed(-1);
	}

	@Override
	public void updateAnimation() {
		ShotObjectState activeSnowballState = shot.getShotObjectState();

		switch (activeSnowballState) {
		case CRASHED:
			return;
		case CRASHEDINGROUND:
			this.activeAnimation = normalSnowballAnimation;
			break;
		case CRASHING:
			this.activeAnimation = splashingAnimation;
			break;
		case MOVING:
			this.activeAnimation = normalSnowballAnimation;
			break;
		}

		try {
			activeAnimation.update(System.currentTimeMillis());
		} catch (Exception e) {
			activeAnimation = normalSnowballAnimation;
		}
	}

	@Override
	public int hashCode() {
		return this.shot.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		GraphicalSnowball other = (GraphicalSnowball)obj;
		return this.shot.equals(other.shot);
	}
}