package ch.hsr.se2p.snowwars.model;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.Timer;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.model.ShotObject.ShotObjectState;

public abstract class AbstractGame extends Observable implements ActionListener {
	private final static Logger logger = Logger.getLogger(AbstractGame.class.getPackage().getName());

	private final static int TIMER_RECALC_INTERVAL = 10;
	protected final static int GROUND_LEVEL_Y = 400;

	protected final static int FORCE_REDUCE_FACTOR = 10;
	public final static int FORCE_REDUCE_FACTOR_STRENGTH = 3;
	protected final static double GRAVITATION = 9.81 / 80;

	private ArrayList<Shot> shots = new ArrayList<Shot>();
	private ArrayList<Knoll> knolls = new ArrayList<Knoll>();

	private Player playerLeft;
	private Player playerRight;

	private Timer recalcTimer;

	public AbstractGame() {
		recalcTimer = new Timer(TIMER_RECALC_INTERVAL, this);
		startTimer();
	}

	private void startTimer() {
		recalcTimer.start();
	}

	public void stopTimer() {
		recalcTimer.stop();
	}

	public Player getPlayerLeft() {
		return playerLeft;
	}

	public Player getPlayerRight() {
		return playerRight;
	}

	public void setPlayerLeft(Player playerLeft) {
		this.playerLeft = playerLeft;
		updateObserver();
	}

	public void setPlayerRight(Player playerRight) {
		this.playerRight = playerRight;
	}

	protected synchronized void checkCollision() {
		for (Shot activeShot : shots) {
			if (activeShot.getShotObjectState() != ShotObjectState.MOVING) {
				continue;
			}

			checkCollisionWithPlayer(activeShot);
			checkCollisionWithOtherShot(activeShot);
			checkCollisionWithGroundAndKnolls(activeShot);
		}
	}

	@Override
	public synchronized void actionPerformed(ActionEvent e) {
		recalculateGame();
	}
	
	private void recalculateGame(){
		for (Shot activeShot : this.getShots()) {
			activeShot.updateCoordinates();
			checkCollision();
		}
		updateObserver();
	}

	private void checkCollisionWithPlayer(Shot shot) {
		Rectangle shotRectangle = shot.getBounds();
		Rectangle playerLeftRectangle = playerLeft.getBounds();
		Rectangle playerRightRectangle = playerRight.getBounds();

		if (shotRectangle.intersects(playerRightRectangle)) {
			logger.info("Snowball hit right player");
			shot.stopShotObject();
			shot.setShotObjectState(ShotObjectState.CRASHING);
			updatePlayerHitPoints(Player.PlayerPosition.RIGHT, shot);
		}

		if (shotRectangle.intersects(playerLeftRectangle)) {
			logger.info("Snowball hit left player");
			shot.stopShotObject();
			shot.setShotObjectState(ShotObjectState.CRASHING);
			updatePlayerHitPoints(Player.PlayerPosition.LEFT, shot);
		}
	}

	private void checkCollisionWithOtherShot(Shot shot) {
		Rectangle shotRectangle = shot.getBounds();

		for (Shot activeShot : shots) {
			if (activeShot.getShotObjectState() != ShotObjectState.MOVING) {
				continue;
			}

			if (activeShot != shot) {
				Rectangle activeShotRectangle = activeShot.getBounds();

				if (activeShotRectangle.intersects(shotRectangle)) {
					if (activeShot.getShotObjectState() == ShotObjectState.CRASHEDINGROUND || shot.getShotObjectState() == ShotObjectState.CRASHEDINGROUND) {
						shot.setShotObjectState(ShotObjectState.CRASHEDINGROUND);
						activeShot.setShotObjectState(ShotObjectState.CRASHEDINGROUND);
					} else {
						activeShot.stopShotObject();
						shot.stopShotObject();
						activeShot.setShotObjectState(ShotObjectState.CRASHING);
						shot.setShotObjectState(ShotObjectState.CRASHING);
					}
				}
			}
		}
	}

	private void checkCollisionWithGroundAndKnolls(Shot activeShot) {
		Rectangle shotRectangle = activeShot.getBounds();

		for (Knoll activeKnoll : knolls) {
			Rectangle activeKnollRectangle = activeKnoll.getBounds();
			if (activeKnollRectangle.intersects(shotRectangle)) {
				activeKnoll.increaseHits();
				activeShot.setShotObjectState(ShotObjectState.CRASHED);
			}
		}

		if (activeShot.getY() > GROUND_LEVEL_Y) {
			knolls.add(new Knoll(activeShot.getX(), activeShot.getY()));
		}
	}

	public synchronized void receivedShot(Shot receivedShot) {
		shots.add(receivedShot);

		updateObserver();
	}

	public synchronized ArrayList<Shot> getShots() {
		return this.shots;
	}

	public void updateObserver() {
		this.setChanged();
		this.notifyObservers();
	}

	public abstract void shoot(Shot shot);

	public abstract void initializePlayers();

	public abstract void updatePlayerHitPoints(PlayerPosition playerPosition, Shot shot);
}
