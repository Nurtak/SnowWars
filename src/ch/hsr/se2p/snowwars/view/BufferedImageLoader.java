package ch.hsr.se2p.snowwars.view;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {
    private final static String IMAGE_PATH = "/img/";
    private final static String FILENAME_BACKGROUND = IMAGE_PATH + "background.jpg";
    private final static String FILENAME_LOGO = IMAGE_PATH + "logo.png";
    private final static String FILENAME_THROW_PLAYER_LEFT = IMAGE_PATH + "ThrowLeftPlayer.png";
    private final static String FILENAME_THROW_PLAYER_RIGHT = IMAGE_PATH + "ThrowRightPlayer5.png";
    private final static String FILENAME_SPRITESHEET_SNOWBALL = IMAGE_PATH + "snowball.png";
    private final static String FILENAME_SPRITESHEET_KNOLL = IMAGE_PATH + "Knoll.png";
    private final static String FILENAME_BENDDOWN_PLAYER_LEFT = IMAGE_PATH + "BendDownLeftPlayer3.png";
    private final static String	FILENAME_BENDDOWN_PLAYER_RIGHT = IMAGE_PATH + "BendDownRightPlayer3.png";
    private final static String FILENAME_BACK_BUTTON = IMAGE_PATH + "BackButton.gif";
    
    private static BufferedImageLoader instance = null;

    private BufferedImageLoader() {
    }

    public static BufferedImageLoader getInstance() {
        if (instance == null) {
            instance = new BufferedImageLoader();
        }
        return instance;
    }

    private BufferedImage loadImage(String pathRelativeToThis) throws IOException {
        return ImageIO.read(BufferedImage.class.getResourceAsStream(pathRelativeToThis));
    }

    public BufferedImage getBackButton() throws IOException{
        return loadImage(FILENAME_BACK_BUTTON);
    }
    
    public BufferedImage getBendDownLeftPlayer() throws IOException{
        return loadImage(FILENAME_BENDDOWN_PLAYER_LEFT);
    }
    
    public BufferedImage getBendDownRightPlayer() throws IOException{
    	return loadImage(FILENAME_BENDDOWN_PLAYER_RIGHT);
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
