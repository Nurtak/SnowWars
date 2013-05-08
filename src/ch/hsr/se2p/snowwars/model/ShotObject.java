package ch.hsr.se2p.snowwars.model;

import java.awt.Rectangle;
import java.io.Serializable;

import ch.hsr.se2p.snowwars.controller.game.ViewGameModel;

public abstract class ShotObject implements Serializable {

	private static final long serialVersionUID = -4934533719382550831L;

	private int x, y, dx, dy;

	public abstract int getWeight();

	public abstract int getDamage();

	public abstract Rectangle getBounds();

	public enum ShotObjectState {
		CRASHED, CRASHING, CRASHEDINGROUND, MOVING
	};

	private ShotObjectState shotObjectState;

	public ShotObject() {
		this.shotObjectState = ShotObjectState.MOVING;
	}

	public void updateCoordinates() {
		if (shotObjectState == ShotObjectState.MOVING) {
			this.dy += AbstractGame.GRAVITATION;

			this.x = (int) ((int) this.x + this.dx);
			this.y = (int) ((int) this.y + this.dy);

			if (x > ViewGameModel.GAME_WIDTH) {
				shotObjectState = ShotObjectState.CRASHED;
			}
		}
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getDx() {
		return dx;
	}

	protected void setDx(int dx) {
		this.dx = dx;
	}

	public int getDy() {
		return dy;
	}

	protected void setDy(int dy) {
		this.dy = dy;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public ShotObjectState getShotObjectState() {
		return this.shotObjectState;
	}

	public void setShotObjectState(ShotObjectState newState) {
		this.shotObjectState = newState;
	}

	public void stopShotObject() {
		this.shotObjectState = ShotObjectState.CRASHING;
	}
}
