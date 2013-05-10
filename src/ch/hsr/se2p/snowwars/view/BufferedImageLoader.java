package ch.hsr.se2p.snowwars.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	private final static String FILENAME_BACKGROUND = new String("img/background.jpg");
	private final static String FILENAME_LOGO = new String("img/logo.png");
	private final static String FILENAME_THROW_PLAYER_LEFT = new String("img/ThrowLeftPlayer2.png");
	private final static String FILENAME_THROW_PLAYER_RIGHT = new String("img/SpriteSheetThrowRightPlayer.png");
	private final static String FILENAME_SPRITESHEET_SNOWBALL = new String("img/snowball.png");
	private final static String FILENAME_SPRITESHEET_KNOLL = new String("img/Knoll.png");
	private static BufferedImageLoader bil;

	private BufferedImageLoader() {
	}

	public static BufferedImageLoader getInstance() {
		if (bil == null) {
			bil = new BufferedImageLoader();
		}
		return bil;
	}

	private BufferedImage loadImage(String pathRelativeToThis) throws IOException {
		File imageFile = new File(pathRelativeToThis);
		if (!imageFile.exists()) {
			throw new IOException("Image not found: " + pathRelativeToThis);
		}
		return ImageIO.read(imageFile);
	}

	public BufferedImage getSnowballSpriteSheet() throws IOException {
		return loadImage(FILENAME_SPRITESHEET_SNOWBALL);
	}

	public BufferedImage getBackgroundImage() throws IOException {
		return loadImage(FILENAME_BACKGROUND);
	}

	public BufferedImage getLogoImage() throws IOException {
		return loadImage(FILENAME_LOGO);
	}

	public BufferedImage getPlayerLeftSpriteSheet() throws IOException {
		return loadImage(FILENAME_THROW_PLAYER_LEFT);
	}

	public BufferedImage getPlayerRightSpriteSheet() throws IOException {
		return loadImage(FILENAME_THROW_PLAYER_RIGHT);
	}

	public BufferedImage getKnollSpriteSheet() throws IOException {
		return loadImage(FILENAME_SPRITESHEET_KNOLL);
	}
}
