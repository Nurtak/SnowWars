package ch.hsr.se2p.snowwars.view;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	private final static String FILENAME_SNOWBALL = new String("/img/snowflake.png");
	private final static String FILENAME_SNOWBALL_CRASHED = new String("/img/snowflake_crashed.png");
	private final static String FILENAME_BACKGROUND = new String("/img/background.jpg");
	private final static String FILENAME_LOGO = new String("/img/logo.png");
	private final static String FILENAME_SPRITESHEET = new String("/img/spriteSheet.png");
	private static BufferedImageLoader bil;

	private BufferedImageLoader() {}

	public static BufferedImageLoader getInstance() {
		if (bil == null) {
			bil = new BufferedImageLoader();
		}
		return bil;
	}

	private BufferedImage loadImage(String pathRelativeToThis) throws IOException {
		URL url = this.getClass().getResource(pathRelativeToThis);
		BufferedImage img = ImageIO.read(url);
		return img;
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
