package ch.hsr.se2p.snowwars.client.game.viewmodel;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.client.viewutils.BufferedImageLoader;
import ch.hsr.se2p.snowwars.model.Knoll;

public class GraphicalKnoll extends GraphicalObject {
	private final static Logger logger = Logger.getLogger(GraphicalKnoll.class.getPackage().getName());

	BufferedImage spriteSheetKnoll;
	BufferedImageLoader loader;
	public AnimationController knollSize1;
	public AnimationController knollSize2;
	public AnimationController knollSize3;
	public AnimationController knollSize4;
	public AnimationController knollSize5;
	public AnimationController activeAnimation;
	private final Knoll knoll;

	public GraphicalKnoll(Knoll knoll) {
		this.knoll = knoll;
		loader = BufferedImageLoader.getInstance();
		try {
			loader.getKnollSpriteSheet();
		} catch (IOException e2) {
			logger.error("Bild nicht gefunden");
		}

		loadSize1();
		loadSize2();
	}

	private void loadSize1() {
		ArrayList<BufferedImage> spritesForKnoll1 = new ArrayList<BufferedImage>();
		spritesForKnoll1.add(spriteSheetKnoll.getSubimage(0, 0, 0, 0));
		knollSize1 = new AnimationController(spritesForKnoll1);
		knollSize1.setSpeed(-1);
	}

	private void loadSize2() {
		ArrayList<BufferedImage> spritesForKnoll2 = new ArrayList<BufferedImage>();
		spritesForKnoll2.add(spriteSheetKnoll.getSubimage(0, 0, 0, 0));
		knollSize2 = new AnimationController(spritesForKnoll2);
		knollSize2.setSpeed(-1);
	}

	@Override
	public int getX() {
		return this.knoll.getX();
	}

	@Override
	public int getY() {
		return this.knoll.getY();
	}

	@Override
	public Image getImage() {
		return activeAnimation.getSprite();
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	public Rectangle getBounds() {
		return new Rectangle(getX() + 5, getY() + 5, 20, 20);
	}

	public int getHits() {
		return this.knoll.getHits();
	}

	@Override
	public void updateAnimation() {
		int hits = getHits();

		switch (hits) {
			case 1 :
				this.activeAnimation = knollSize1;
				break;
			case 2 :
				this.activeAnimation = knollSize2;
				break;
			case 3 :
				this.activeAnimation = knollSize3;
				break;
			case 4 :
				this.activeAnimation = knollSize4;
				break;
			case 5 :
				this.activeAnimation = knollSize5;
				break;
		}

		try {
			activeAnimation.update(System.currentTimeMillis());
		} catch (Exception e) {
			activeAnimation = knollSize1;
		}
	}

	public Knoll getKnoll() {
		return knoll;
	}

}
