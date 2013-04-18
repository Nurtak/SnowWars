package ch.hsr.se2p.snowwars.view.game;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import ch.hsr.se2p.snowwars.view.BufferedImageLoader;

public class GraphicalPlayer {

	protected final static int PLAYER_LEFT_POSITION_X = 40;
	protected final static int PLAYER_LEFT_POSITION_Y = 300;

	int width = 158;
	int height = 149;

	public Animation throwingSprites;
	public Animation standing;

	private BufferedImage spriteSheet;
	public BufferedImageLoader loader;

	public GraphicalPlayer() throws IOException {
		loader = BufferedImageLoader.getInstance();
		spriteSheet = loader.getSpriteSheetImage();
		loadThrowAnimation();
		loadStandingAnimation();
	}

	private void loadThrowAnimation() {
		ArrayList<BufferedImage> spritesForThrow = new ArrayList<BufferedImage>();

		spritesForThrow.add(spriteSheet.getSubimage(0, 0, width, height));
		spritesForThrow.add(spriteSheet.getSubimage(width, 0, width, height));
		spritesForThrow.add(spriteSheet.getSubimage(2 * width, 0, width, height));
		spritesForThrow.add(spriteSheet.getSubimage(0, height, width, height));
		spritesForThrow.add(spriteSheet.getSubimage(width, height, width, height));
		spritesForThrow.add(spriteSheet.getSubimage(2 * width, height, width, height));

		throwingSprites = new Animation(spritesForThrow);
		throwingSprites.setSpeed(100);
	}

	private void loadStandingAnimation() {
		ArrayList<BufferedImage> spritesForStand = new ArrayList<BufferedImage>();
		spritesForStand.add(spriteSheet.getSubimage(0, 0, width, height));
		standing = new Animation(spritesForStand);
		standing.setSpeed(-1);
	}

	public int getX() {
		return PLAYER_LEFT_POSITION_X;
	}

	public int getY() {
		return PLAYER_LEFT_POSITION_Y;
	}
}