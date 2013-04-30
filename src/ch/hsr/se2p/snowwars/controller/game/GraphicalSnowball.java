package ch.hsr.se2p.snowwars.controller.game;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.view.BufferedImageLoader;

public class GraphicalSnowball extends GraphicalObject {
	private final static Logger logger = Logger.getLogger(GraphicalSnowball.class.getPackage().getName());

	private int x, y;
	private double dy, dx;

	public AnimationController splashingAnimation;
	public AnimationController normalSnowball;
	public AnimationController activeAnimation;
	private BufferedImage spriteSheetSnowball;
	public BufferedImageLoader loader;
	private int width = 35;
	private int height = 35;
	public enum SnowballState {CRASHED, CRASHING, CRASHEDINGROUND, MOVING};
	public SnowballState snowballState;

	public GraphicalSnowball(int angle, int strength) {
		loader = BufferedImageLoader.getInstance();
		try {
			spriteSheetSnowball = loader.getSnowballSpriteSheet();
		} catch (IOException e1) {
			logger.error("Bild nicht gefunden");
		}

		loadSplashAnimation();
		loadNormalSnowballAnimation();
		activeAnimation = normalSnowball;
		snowballState = SnowballState.MOVING;

		this.x = GraphicalPlayer.PLAYER_LEFT_POSITION_X + 50;
		this.y = GraphicalPlayer.PLAYER_LEFT_POSITION_Y;

		double vySin = Math.sin(Math.toRadians(angle));
		double vxCos = Math.cos(Math.toRadians(angle));

		this.dy = (int) (vySin * strength) * -1;
		this.dx = (int) (vxCos * strength);
		
		this.dy = this.dy / ViewGameController.FORCE_REDUCE_FACTOR;
		this.dx = this.dx / ViewGameController.FORCE_REDUCE_FACTOR;
	}

	public void stopSnowball() {
		snowballState = SnowballState.CRASHING;
	}

	public Image getImage() {
		return activeAnimation.getSprite();
	}

	public boolean isVisible() {
		return snowballState == SnowballState.MOVING || snowballState == SnowballState.CRASHEDINGROUND || snowballState == SnowballState.CRASHING;
	}

	public void updateValues() {
		if (snowballState == SnowballState.MOVING) {
			this.dy += ViewGameController.GRAVITATION;

			this.x = (int) ((int) this.x + this.dx);
			this.y = (int) ((int) this.y + this.dy);

			if (x > ViewGameController.GAME_WIDTH || y > ViewGameController.GROUND_LEVEL_Y) {
				snowballState = SnowballState.CRASHEDINGROUND;
			}
		}

		try {
			activeAnimation.update(System.currentTimeMillis());
		} catch (Exception e) {
			snowballState = SnowballState.CRASHED; 
			activeAnimation = normalSnowball;
		}
	}

	@Override
	public int getX() {
		return this.x;
	}

	@Override
	public int getY() {
		return this.y;
	}

	public void setX(int x){
		this.x =x;
	}
	
	public void setY(int y){
		this.y = y;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(getX()+5, getY()+5, 30, 30);
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

		normalSnowball = new AnimationController(spritesForNormalSnowball);
		normalSnowball.setSpeed(-1);
	}

	public void startSplashingAnimation() {
		snowballState = SnowballState.CRASHING;
		this.activeAnimation = splashingAnimation;
	}

}
