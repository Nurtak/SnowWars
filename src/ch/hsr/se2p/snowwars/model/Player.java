package ch.hsr.se2p.snowwars.model;

import java.awt.Rectangle;
import java.io.Serializable;

public class Player implements Serializable {
	private static final long serialVersionUID = 4159132927256644041L;

	public final static int MAX_HIT_POINTS = 100;
	public final static int PLAYER_LEFT_POSITION_X = 40;
	public final static int PLAYER_LEFT_POSITION_Y = 300;
	public final static int PLAYER_RIGHT_POSITION_X = 840;
	public final static int PLAYER_RIGHT_POSITION_Y = 300;

	public final static int SNOWBALL_LEFT_THROW_POS_X = PLAYER_LEFT_POSITION_X + 25;
	public final static int SNOWBALL_LEFT_THROW_POS_Y = PLAYER_LEFT_POSITION_Y - 25;
	public final static int SNOWBALL_RIGHT_THROW_POS_X = PLAYER_RIGHT_POSITION_X + 25;
	public final static int SNOWBALL_RIGHT_THROW_POS_Y = PLAYER_RIGHT_POSITION_Y - 25;
	
	public final static int PLAYER_LEFT_BOUNDS_X = 40;
	public final static int PLAYER_LEFT_BOUNDS_Y = 320;
	public final static int PLAYER_RIGHT_BOUNDS_X = 860;
	public final static int PLAYER_RIGHT_BOUNDS_Y = 320;
	public final static int PLAYER_BOUNDS_HEIGHT = 60;
	public final static int PLAYER_BOUNDS_WIDTH = 150;

	private User user;
	private int hitPoints;

	public enum PlayerState {
		STANDING, BUILDING, THROWING
	};
	private PlayerState playerState;

	public static enum PlayerPosition {
		LEFT, RIGHT
	};
	private PlayerPosition position;

	public Player(User user, PlayerPosition pos) {
		this(user, pos, 100);
	}

	public Player(User user, PlayerPosition pos, int hitPoints) {
		this.user = user;
		this.position = pos;
		this.hitPoints = hitPoints;
		this.playerState = PlayerState.STANDING;
	}

	public User getUser() {
		return user;
	}

	public int getHitPoints() {
		return hitPoints;
	}

	public void setHitPoints(int hitPoints) {
		this.hitPoints = hitPoints;
	}

	public PlayerPosition getPosition() {
		return this.position;
	}

	public PlayerState getPlayerState() {
		return this.playerState;
	}

	public void setPlayerState(PlayerState playerState) {
		this.playerState = playerState;
	}

	public Rectangle getBounds() {
		switch (position) {
			case LEFT :
				return new Rectangle(PLAYER_LEFT_BOUNDS_X, PLAYER_LEFT_BOUNDS_Y,PLAYER_BOUNDS_HEIGHT, PLAYER_BOUNDS_WIDTH);
			default :
				return new Rectangle(PLAYER_RIGHT_BOUNDS_X,PLAYER_RIGHT_BOUNDS_Y, PLAYER_BOUNDS_HEIGHT, PLAYER_BOUNDS_WIDTH);
		}
	}
}
