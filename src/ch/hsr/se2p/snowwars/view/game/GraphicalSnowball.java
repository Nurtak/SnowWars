package ch.hsr.se2p.snowwars.view.game;

import java.awt.Image;

import javax.swing.ImageIcon;

public class GraphicalSnowball {

	private int x, y;
	private double dy, dx;
	private Image snowballImage, snowballCrashedImage;
	boolean visible;

	public GraphicalSnowball(int angle, int strength) {
		ImageIcon snowFlakeII = new ImageIcon("img/snowflake.png");
		ImageIcon snowFlakeCrashedII = new ImageIcon("img/snowflake_crashed.png");

		snowballImage = snowFlakeII.getImage();
		snowballCrashedImage = snowFlakeCrashedII.getImage();
		visible = true;

		this.x = GraphicalPlayer.PLAYER_LEFT_POSITION_X + 50;
		this.y = GraphicalPlayer.PLAYER_LEFT_POSITION_Y + 50;

		double vySin = Math.sin(Math.toRadians(angle));
		double vxCos = Math.cos(Math.toRadians(angle));

		this.dy = (int) (vySin * strength) * -1;
		this.dx = (int) (vxCos * strength);

		this.dy = this.dy / Board.FORCE_REDUCE_FACTOR;
		this.dx = this.dx / Board.FORCE_REDUCE_FACTOR;
	}

	public Image getImage() {
		if (visible) {
			return snowballImage;
		} else {
			return snowballCrashedImage;
		}
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
		this.dy += Board.GRAVITATION;

		this.x = (int) ((int) this.x + this.dx);
		this.y = (int) ((int) this.y + this.dy);

		if (x > ViewGame.GAME_WIDTH || y > Board.GROUND_LEVEL_Y) {
			visible = false;
		}
	}
}
