package ch.hsr.se2p.snowwars.model;

import java.awt.Rectangle;

public class Snowball extends ShotObject {

	private static final long serialVersionUID = -7432399665707233393L;
	public static final double DAMAGE_MULTIPLIER = 4.0;
	private static final int WIDTH = 20;
	private static final int HEIGHT = 20;
	private double weight;

	public Snowball(double weight) {
		this.weight = weight;
	}

	@Override
	public double getWeight() {
		return weight;
	}

	@Override
	public int getDamage() {
		return (int) Math.round(weight * DAMAGE_MULTIPLIER);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), WIDTH, HEIGHT);
	}
	
}