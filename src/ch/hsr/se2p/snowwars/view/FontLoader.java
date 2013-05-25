package ch.hsr.se2p.snowwars.view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontLoader {

	private static final String FONT_PATH = "/fonts/fonts/";

	public static final Font OrangeJuice;
	

	static {
		Font tryFont = null;
		try {
			tryFont = Font.createFont(Font.TRUETYPE_FONT, new File(FONT_PATH + "Feed The Bears.ttf"));
		} catch (FontFormatException e) {
			throw new ExceptionInInitializerError(e);
		} catch (IOException e) {
			throw new ExceptionInInitializerError(e);
		} finally {
			OrangeJuice = tryFont.deriveFont(Font.PLAIN, 35);

		}
	}

}
