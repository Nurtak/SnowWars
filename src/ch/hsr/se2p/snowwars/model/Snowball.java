package ch.hsr.se2p.snowwars.model;

import java.awt.Rectangle;

public class Snowball extends ShotObject {

	private static final long serialVersionUID = -7432399665707233393L;

	private double damageMultiplier = 4.0;
	private double weight;
	private final static int SNOWBALL_WIDTH = 20;
	private final static int SNOWBALL_HEIGHT = 20;

	public Snowball(double weight) {
		this.weight = weight;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public int getDamage() {
		return (int) Math.round(weight * damageMultiplier);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), SNOWBALL_WIDTH, SNOWBALL_HEIGHT);
	}
}