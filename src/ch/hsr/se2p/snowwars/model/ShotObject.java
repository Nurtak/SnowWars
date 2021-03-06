package ch.hsr.se2p.snowwars.model;

import java.awt.Rectangle;
import java.io.Serializable;

import ch.hsr.se2p.snowwars.client.game.viewmodel.ViewGameModel;

public abstract class ShotObject implements Serializable {

	private static final long serialVersionUID = -4934533719382550831L;
	public static final double DAMAGE_MULTIPLIER = 1.0;

	private int x, y;
	private double dx, dy; 
	
	public abstract double getWeight();

	public abstract int getDamage();

	public abstract Rectangle getBounds();
	
	public enum ShotObjectState {
		CRASHED, CRASHING, CRASHEDINGROUND, MOVING
	};

	private ShotObjectState shotObjectState;

	public ShotObject() {
		shotObjectState = ShotObjectState.MOVING;
	}

	public void updateCoordinates() {
		if (shotObjectState == ShotObjectState.MOVING) {
		    
			double gravitationImpact = AbstractGame.GRAVITATION * getWeight();			
			dy += gravitationImpact;
			x += (int) dx;
			y += (int) dy;

			if (y >= AbstractGame.GROUND_LEVEL_Y) {
				shotObjectState = ShotObjectState.CRASHEDINGROUND;
			}

			if (x > ViewGameModel.GAME_WIDTH || x < 0) {
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

	public double getDx() {
		return dx;
	}

	protected void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	protected void setDy(double dy) {
		this.dy = dy;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public ShotObjectState getShotObjectState() {
		return shotObjectState;
	}

	public void setShotObjectState(ShotObjectState newState) {
		shotObjectState = newState;
	}

	public void stopShotObject() {
		shotObjectState = ShotObjectState.CRASHING;
	}

}
