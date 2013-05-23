package ch.hsr.se2p.snowwars.viewcontrolling.game;

import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import org.apache.log4j.Logger;

public class SoundPlayer {
	private final static Logger logger = Logger.getLogger(SoundPlayer.class.getPackage().getName());

	private final static String WIND_HOWL_SOUND_PATH = new String("/sounds/wind.wav");
	private final static String LOST_SOUND_PATH = new String("/sounds/lost.wav");
	private final static String WON_SOUND_PATH = new String("/sounds/won.wav");
	private final static String COUNTDOWN_SOUND_PATH = new String("/sounds/countdown.wav");
	private final static String START_SOUND_PATH = new String("/sounds/start.wav");

	private Clip windHowlClip;
	private Clip lostClip;
	private Clip wonClip;
	private Clip startClip;

	public SoundPlayer() {
		initializeWindHowl();
		initializeLost();
		initializeWon();
		initializeStart();
	}

	private void initializeWindHowl() {
		try {
			InputStream bufferedIn = SoundPlayer.class.getResourceAsStream(WIND_HOWL_SOUND_PATH);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
			AudioFormat audioFormat = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
			windHowlClip = (Clip) AudioSystem.getLine(info);
			windHowlClip.open(audioStream);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void initializeLost() {
		try {
			InputStream bufferedIn = SoundPlayer.class.getResourceAsStream(LOST_SOUND_PATH);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
			AudioFormat audioFormat = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
			lostClip = (Clip) AudioSystem.getLine(info);
			lostClip.open(audioStream);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void initializeWon() {
		try {
			InputStream bufferedIn = SoundPlayer.class.getResourceAsStream(WON_SOUND_PATH);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
			AudioFormat audioFormat = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
			wonClip = (Clip) AudioSystem.getLine(info);
			wonClip.open(audioStream);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private void initializeStart() {
		try {
			InputStream bufferedIn = SoundPlayer.class.getResourceAsStream(START_SOUND_PATH);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
			AudioFormat audioFormat = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
			startClip = (Clip) AudioSystem.getLine(info);
			startClip.open(audioStream);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void playWindHowl() {
		logger.info("Playing Windhowl sound...");
		windHowlClip.start();
	}

	public void stopWindHowl() {
		logger.info("Stopping Windhowl sound...");
		if(windHowlClip.isActive()){
			windHowlClip.close();
		}
	}

	public void playWon() {
		logger.info("Playing Won sound...");
		wonClip.start();
	}

	public void playLost() {
		logger.info("Playing Lost sound...");
		lostClip.start();
	}

	public void playStart() {
		logger.info("Playing Start sound...");
		startClip.start();
	}

	public void playCountdown() {
		try {
			InputStream bufferedIn = SoundPlayer.class.getResourceAsStream(COUNTDOWN_SOUND_PATH);
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
			AudioFormat audioFormat = audioStream.getFormat();
			DataLine.Info info = new DataLine.Info(Clip.class, audioFormat);
			Clip countdownClip = (Clip) AudioSystem.getLine(info);
			countdownClip.open(audioStream);
			countdownClip.start();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}