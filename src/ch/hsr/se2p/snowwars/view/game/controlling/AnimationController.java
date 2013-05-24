package ch.hsr.se2p.snowwars.view.game.controlling;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AnimationController {

	private ArrayList<BufferedImage> frames;
	private BufferedImage sprite;

	private long previousTime, speed;
	private int currentFrame;

	public AnimationController(ArrayList<BufferedImage> frames) {
		this.frames = frames;
	}


	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public void update(long time) throws Exception {		
		if (speed < 0) {
			sprite = frames.get(currentFrame);
		} else  {
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
	

	public BufferedImage getSprite() {
		return sprite;
	}
}
