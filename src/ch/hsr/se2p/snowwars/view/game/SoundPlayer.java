package ch.hsr.se2p.snowwars.view.game;

import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

import org.apache.log4j.Logger;

public class SoundPlayer {
	private final static Logger logger = Logger.getLogger(SoundPlayer.class.getPackage().getName());

	private final static String WIND_HOWL_SOUND_PATH = new String("/sounds/wind.wav");
	private Clip clip;

	public SoundPlayer() {
		try {
			this.clip = AudioSystem.getClip();
		} catch (LineUnavailableException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void playWindHowl() {
		try {
			InputStream bufferedIn = SoundPlayer.class.getResourceAsStream(WIND_HOWL_SOUND_PATH);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
			clip.open(audioStream);
			clip.start();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void stopWindHowl() {
		clip.stop();
	}
}