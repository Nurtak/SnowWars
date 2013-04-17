package ch.hsr.se2p.snowwars.view.game;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player {

	private final static String PLAYER_ICON_PATH = "img/viking.png";

	protected final static int PLAYER_LEFT_POSITION_X = 40;
	protected final static int PLAYER_LEFT_POSITION_Y = 300;
	
	private int x;
	private int y;
	
	private Image playerImage;


	public Player() {
		ImageIcon playerIcon = new ImageIcon(PLAYER_ICON_PATH);
		playerImage = playerIcon.getImage();
		
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
}