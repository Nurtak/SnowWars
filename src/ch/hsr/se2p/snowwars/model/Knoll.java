package ch.hsr.se2p.snowwars.model;

import java.awt.Rectangle;

public class Knoll {

	private int x, y;
	private int hits;
	public enum KnollSize {
		SIZE1, SIZE2, SIZE3, SIZE4, SIZE5
	};
	public KnollSize knollsize;

	public Knoll(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Rectangle getBounds() {
		switch (hits) {
			case 1 :
				return new Rectangle(0, 0, 0, 0);
			case 2 :
				return new Rectangle(0, 0, 0, 0);
			case 3 :
				return new Rectangle(0, 0, 0, 0);
			case 4 :
				return new Rectangle(0, 0, 0, 0);
			case 5 :
				return new Rectangle(0, 0, 0, 0);
			case 6 :
				hits = 0;
			default :
				return new Rectangle(0, 0, 0, 0);
		}
	}

	public KnollSize getKnollSize() {
		return this.knollsize;
	}

	public void setKnollSize(KnollSize newState) {
		this.knollsize = newState;
	}

	public int getHits() {
		return hits;
	}

	public void increaseHits() {
		hits = hits + 1;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
