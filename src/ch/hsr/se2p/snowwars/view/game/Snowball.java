package ch.hsr.se2p.snowwars.view.game;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Snowball {

	private int x, y;
	private Image image;
	boolean visible;
	private final int BOARD_WIDTH = 1000;
	private double vy, vx;

	public Snowball(int x, int y, int angle, int strength) {
		ImageIcon ii = new ImageIcon(this.getClass().getResource("snowflake.png"));
		image = ii.getImage();
		visible = true;
		this.x = x;
		this.y = y;

		double vySin = Math.sin(Math.toRadians(angle));
		double vxCos = Math.cos(Math.toRadians(angle));

		this.vy = (int) (vySin * strength) * -1;
		this.vx = (int) (vxCos * strength);

		this.vy = this.vy / 20;
		this.vx = this.vx / 20;

		System.out.println("vx: " + this.vx + ", vy: " + this.vy);
	}

	public Image getImage() {
		return image;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean isVisible() {
		return visible;
	}

	public void move() {
		this.vy += (9.81 / 70);

		System.out.println("previous x: " + this.x + ", previous y: " + this.y);

		this.x = (int) ((int) this.x + this.vx);
		this.y = (int) ((int) this.y + this.vy);

		System.out.println("now x: " + this.x + ", now y: " + this.y);
		System.out.println("----------------------------------------");

		if (x > BOARD_WIDTH || y > 400) {
			visible = false;
		}

	}
}
