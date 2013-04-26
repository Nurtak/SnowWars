package ch.hsr.se2p.snowwars.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	private final static String FILENAME_SNOWBALL = new String("img/snowflake.png");
	private final static String FILENAME_SNOWBALL_CRASHED = new String("img/snowflake_crashed.png");
	private final static String FILENAME_BACKGROUND = new String("img/background.jpg");
	private final static String FILENAME_LOGO = new String("img/logo.png");
	private final static String FILENAME_SPRITESHEET = new String("img/spriteSheet.png");
	private final static String FILENAME_SPRITESHEET_SNOWBALL = new String ("img/Crash2.png");
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
	
	public BufferedImage getSnowballImage() throws IOException {
		return loadImage(FILENAME_SNOWBALL);
	}

	public BufferedImage getSnowballCrashedImage() throws IOException {
		return loadImage(FILENAME_SNOWBALL_CRASHED);
	}

	public BufferedImage getBackgroundImage() throws IOException {
		return loadImage(FILENAME_BACKGROUND);
	}

	public BufferedImage getLogoImage() throws IOException {
		return loadImage(FILENAME_LOGO);
	}

	public BufferedImage getSpriteSheetImage() throws IOException {
		return loadImage(FILENAME_SPRITESHEET);
	}
}
