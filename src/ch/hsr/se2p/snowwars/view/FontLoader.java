package ch.hsr.se2p.snowwars.view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.log4j.Logger;

public class FontLoader {

	private final static Logger logger = Logger.getLogger(FontLoader.class.getPackage().getName());

	private static final String FONT_PATH = "/fonts/";
	private static final String FONT_FILE_PATH = FONT_PATH + "PreppyGirlsHandwriting.ttf";
	private Font gameFont = null;
	
	private static FontLoader instance = null;
    private FontLoader() {
    }

    public static FontLoader getInstance() {
        if (instance == null) {
            instance = new FontLoader();
        }
        return instance;
    }
	
    public Font getGameFont(int size){
    	initFont();
    	return gameFont.deriveFont(Font.PLAIN, size);
    }
    
    private void initFont(){
      	if(gameFont == null){
    		try {
    			URL fontURL = FontLoader.class.getResource(FONT_FILE_PATH);
    			File fontFile = new File(fontURL.toURI());
    			gameFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
    			logger.info("Successfully loaded font from " + FONT_FILE_PATH);
    		} catch (FontFormatException e) {
    			logger.error(e.getMessage(), e);
    		} catch (IOException e) {
    			logger.error(e.getMessage(), e);
    		} catch (URISyntaxException e) {
    			logger.error(e.getMessage(), e);
    		}
    	}
    }
}
