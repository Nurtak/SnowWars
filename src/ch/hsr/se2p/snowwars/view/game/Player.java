package ch.hsr.se2p.snowwars.view.game;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Player {

	private final static String PLAYER_ICON_PATH = "img/viking.png";

	protected final static int PLAYER_LEFT_POSITION_X = 40;
	protected final static int PLAYER_LEFT_POSITION_Y = 300;
	
	private int x;
	private int y;
	
	private Image playerImage;

	private ArrayList<Snowball> snowballs;

	public Player() {
		ImageIcon playerIcon = new ImageIcon(PLAYER_ICON_PATH);
		playerImage = playerIcon.getImage();
		snowballs = new ArrayList<Snowball>();
		
		this.x = PLAYER_LEFT_POSITION_X;
		this.y = PLAYER_LEFT_POSITION_Y;
	}

	public void move() {}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImage() {
		return playerImage;
	}

	public ArrayList<Snowball> getSnowballs() {
		return snowballs;
	}

	public void fire(Snowball snowBall) {
		snowballs.add(snowBall);
	}
}