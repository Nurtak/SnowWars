package ch.hsr.se2p.snowwars.view.game;

import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player {

	private String player = "Viking.png";

	private int dx;
	private int dy;
	private int x;
	private int y;
	private Image image;

	private ArrayList<Snowball> snowballs;

	//private final int PLAYER_SIZE = 70;

	public Player() {
		ImageIcon ii = new ImageIcon(this.getClass().getResource(player));
		image = ii.getImage();
		snowballs = new ArrayList<Snowball>();

		x = 40;
		y = 300;
	}

	public void move() {
		x += dx;
		y += dy;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImage() {
		return image;
	}

	public ArrayList<Snowball> getSnowballs() {
		return snowballs;
	}

	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_SPACE) {
		}
	}

	public void fire(Snowball sn) {
		snowballs.add(sn);
	}

}