package ch.hsr.se2p.snowwars.viewcontrolling.game;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class AnimationController {

	private ArrayList<BufferedImage> frames;
	private BufferedImage sprite;

	private long previousTime, speed;
	private int currentFrame;

	public enum KindOfAnimation {
		ENDLESS, ONEIMAGE, MOVING
	};

	public KindOfAnimation kindOfAnimation;

	public AnimationController(ArrayList<BufferedImage> frames) {
		this.frames = frames;
	}

	public void setKindOfAnimation(KindOfAnimation kind) {
		this.kindOfAnimation = kind;
	}

	public void setSpeed(long speed) {
		this.speed = speed;
	}

	public void update(long time) throws Exception {
		if (kindOfAnimation == KindOfAnimation.ONEIMAGE) {
			sprite = frames.get(currentFrame);
		} else if (kindOfAnimation == KindOfAnimation.MOVING) {
			setSpeed(100);
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
		} else {
			setSpeed(100);
			if (time - previousTime >= speed) {
				currentFrame++;
				if (currentFrame > frames.size()) {
					currentFrame = 0;
					sprite = frames.get(currentFrame);
				} else {
					sprite = frames.get(currentFrame);
				}
				previousTime = time;
			}
		}
	}

	public BufferedImage getSprite() {
		return sprite;
	}
}
