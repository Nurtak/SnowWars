package ch.hsr.se2p.snowwars.controller.game;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import ch.hsr.se2p.snowwars.view.BufferedImageLoader;

public class GraphicalPlayer extends GraphicalObject {
	protected final static int PLAYER_LEFT_POSITION_X = 40;
	protected final static int PLAYER_LEFT_POSITION_Y = 300;
	private final int WIDTH = 158;
	private final int HEIGHT = 149;

	public AnimationController throwingSprites;
	public AnimationController standing;
	public AnimationController activeAnimation;

	private BufferedImage spriteSheet;
	public BufferedImageLoader loader;

	public GraphicalPlayer() throws IOException {
		loader = BufferedImageLoader.getInstance();
		spriteSheet = loader.getSpriteSheetImage();
		loadThrowAnimation();
		loadStandingAnimation();
		activeAnimation = standing;
	}

	private void loadThrowAnimation() {
		ArrayList<BufferedImage> spritesForThrow = new ArrayList<BufferedImage>();

		spritesForThrow.add(spriteSheet.getSubimage(0, 0, WIDTH, HEIGHT));
		spritesForThrow.add(spriteSheet.getSubimage(WIDTH, 0, WIDTH, HEIGHT));
		spritesForThrow.add(spriteSheet.getSubimage(2 * WIDTH, 0, WIDTH, HEIGHT));
		spritesForThrow.add(spriteSheet.getSubimage(0, HEIGHT, WIDTH, HEIGHT));
		spritesForThrow.add(spriteSheet.getSubimage(WIDTH, HEIGHT, WIDTH, HEIGHT));
		spritesForThrow.add(spriteSheet.getSubimage(2 * WIDTH, HEIGHT, WIDTH, HEIGHT));

		throwingSprites = new AnimationController(spritesForThrow);
		throwingSprites.setSpeed(100);
	}

	private void loadStandingAnimation() {
		ArrayList<BufferedImage> spritesForStand = new ArrayList<BufferedImage>();
		spritesForStand.add(spriteSheet.getSubimage(0, 0, WIDTH, HEIGHT));
		standing = new AnimationController(spritesForStand);
		standing.setSpeed(-1);
	}

	@Override
	public int getX() {
		return PLAYER_LEFT_POSITION_X;
	}

	@Override
	public int getY() {
		return PLAYER_LEFT_POSITION_Y;
	}

	public Rectangle getBounds() {
		return new Rectangle(40, 350, 80, 80);
	}

	@Override
	public void updateValues() {
		try {
			activeAnimation.update(System.currentTimeMillis());
		} catch (Exception e) {
			activeAnimation = standing;
		}
	}

	public void startThrowAnimation() {
		this.activeAnimation = throwingSprites;
	}

	@Override
	public Image getImage() {
		return activeAnimation.getSprite();
	}

	@Override
	public boolean isVisible() {
		return true;
	}

}