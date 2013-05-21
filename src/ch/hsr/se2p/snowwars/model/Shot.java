package ch.hsr.se2p.snowwars.model;

import java.awt.Rectangle;
import java.io.Serializable;

import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.model.ShotObject.ShotObjectState;

public class Shot implements Serializable {
	private static final long serialVersionUID = 1528366581031974029L;

	protected final static int FORCE_REDUCE_FACTOR = 15;
	private final int angle;
	private final int strength;
	private PlayerPosition shotOrigin;
	private ShotObject shotObject;

	public Shot(int angle, int strength, ShotObject throwingObject) {
		this.angle = angle;
		this.strength = strength;
		this.shotObject = throwingObject;
		
		double vySin = Math.sin(Math.toRadians(angle));
		double vxCos = Math.cos(Math.toRadians(angle));

		double dy = (vySin * strength) * -1;
		dy = dy / FORCE_REDUCE_FACTOR;

		double dx = (vxCos * strength);
		dx = dx / FORCE_REDUCE_FACTOR;

		throwingObject.setDy(dy);
		throwingObject.setDx(dx);
	}

	public int getAngle() {
		return angle;
	}

	public int getStrength() {
		return strength;
	}

	public double getWeight() {
		return shotObject.getWeight();
	}

	public int getDamage() {
		return shotObject.getDamage();
	}

	public void updateCoordinates() {
		shotObject.updateCoordinates();
	}

	public int getY() {
		return shotObject.getY();
	}

	public int getX() {
		return shotObject.getX();
	}

	public ShotObjectState getShotObjectState() {
		return shotObject.getShotObjectState();
	}

	public void setShotObjectState(ShotObjectState newState) {
		this.shotObject.setShotObjectState(newState);
	}

	public Rectangle getBounds() {
		return shotObject.getBounds();
	}

	public void stopShotObject() {
		this.shotObject.stopShotObject();
	}

	public ShotObject getShotObject() {
		return this.shotObject;
	}

	public void setShotOrigin(PlayerPosition shotOrigin) {
		this.shotOrigin = shotOrigin;
		switch (shotOrigin) {
			case LEFT :
				shotObject.setX(Player.SNOWBALL_LEFT_THROW_POS_X);
				shotObject.setY(Player.SNOWBALL_LEFT_THROW_POS_Y);
				break;
			case RIGHT :
				shotObject.setX(Player.SNOWBALL_RIGHT_THROW_POS_X);
				shotObject.setY(Player.SNOWBALL_RIGHT_THROW_POS_Y);
		}
	}

	public PlayerPosition getShotOrigin() {
		return this.shotOrigin;
	}

	@Override
	public String toString() {
		return "Angle(" + angle + ") " + "Strength(" + strength + ") " + "Weight(" + shotObject.getWeight() + ") " + "DamageValue(" + shotObject.getDamage() + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + angle;
		result = (int) (prime * result + shotObject.getWeight());
		result = prime * result + strength;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (getClass() != obj.getClass())
			return false;
		Shot other = (Shot) obj;
		if (angle != other.angle)
			return false;
		if (shotObject.getWeight() != other.getShotObject().getWeight()) {
			return false;
		}
		if (strength != other.strength) {
			return false;
		}
		return true;
	}

}
