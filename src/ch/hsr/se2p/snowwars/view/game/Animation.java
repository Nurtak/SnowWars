package ch.hsr.se2p.snowwars.view.game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Animation {

	private ArrayList<BufferedImage> frames;
	public BufferedImage sprite;

	private long previousTime, speed;
	private int frameAtPause, currentFrame;

	public Animation(ArrayList<BufferedImage> frames) {
		this.frames = frames;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public void update(long time) throws Exception {
		if (speed == -1) {
			sprite = frames.get(currentFrame);
		} else {
			if (time - previousTime >= speed) {
				// Update the animation
				currentFrame++;
				try {
					sprite = frames.get(currentFrame);
				} catch (IndexOutOfBoundsException e) {
					currentFrame = 0;
					throw new Exception("finished animation");
				}
				previousTime = time;
			}
		}
	}

	public void pause() {

	}

	public void resume() {
		currentFrame = frameAtPause;
	}
}
