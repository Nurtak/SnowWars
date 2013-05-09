package ch.hsr.se2p.snowwars.view.game;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundPlayer {

	private static SoundPlayer soundPlayer;
	private SoundManager soundManager;

	private SoundPlayer() {
		soundManager = new SoundManager();
	}

	public static SoundPlayer getInstance() {
		if (soundPlayer == null) {
			soundPlayer = new SoundPlayer();
		}
		return soundPlayer;
	}

	public void playWindSound() {
		soundManager.playSound(0);
	}

	public void playSplashSound() {
		soundManager.playSound(1);

	}
}

class SoundManager {
	private Vector<AudioFormat> afs;
	private Vector<Integer> sizes;
	private Vector<Info> infos;
	private Vector<byte[]> audios;
	private int num = 0;

	public SoundManager() {
		afs = new Vector<AudioFormat>();
		sizes = new Vector<Integer>();
		infos = new Vector<Info>();
		audios = new Vector<byte[]>();
		
		addClip("sounds/wind.wav");
		addClip("sounds/snowball.wav");
	}

	public void addClip(String s) {
		File file = new File(s);
		InputStream stream;
		try {
			stream = new FileInputStream(file);
			AudioInputStream audioInputStream;
			audioInputStream = AudioSystem.getAudioInputStream(loadStream(stream));
			AudioFormat af = audioInputStream.getFormat();
			int size = (int) (af.getFrameSize() * audioInputStream.getFrameLength());
			byte[] audio = new byte[size];
			DataLine.Info info = new DataLine.Info(Clip.class, af, size);
			audioInputStream.read(audio, 0, size);

			afs.add(af);
			sizes.add(new Integer(size));
			infos.add(info);
			audios.add(audio);

			num++;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private ByteArrayInputStream loadStream(InputStream inputstream) throws IOException {
		ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
		byte data[] = new byte[1024];
		for (int i = inputstream.read(data); i != -1; i = inputstream.read(data))
			bytearrayoutputstream.write(data, 0, i);

		inputstream.close();
		bytearrayoutputstream.close();
		data = bytearrayoutputstream.toByteArray();
		return new ByteArrayInputStream(data);
	}

	public void playSound(int x){
		if (x > num) {
			System.out.println("playSound: sample nr[" + x + "] is not available");
		} else {
			Clip clip;
			try {
				clip = (Clip) AudioSystem.getLine((DataLine.Info) infos.elementAt(x));
				clip.open((AudioFormat) afs.elementAt(x), (byte[]) audios.elementAt(x), 0, ((Integer) sizes.elementAt(x)).intValue());
				clip.start();
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			}

		}
	}
}