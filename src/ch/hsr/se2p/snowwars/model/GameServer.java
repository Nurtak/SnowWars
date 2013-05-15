package ch.hsr.se2p.snowwars.model;

import java.rmi.RemoteException;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSession;

public class GameServer extends AbstractGame {
	private final static Logger logger = Logger.getLogger(GameServer.class.getPackage().getName());

	GameServerSession playerLeftGameServerSession;
	GameServerSession playerRightGameServerSession;

	AtomicInteger playerReadyCount = new AtomicInteger(0);

	public GameServer(GameServerSession playerLeftGameServerSession, GameServerSession playerRightGameServerSession) {
		this.playerLeftGameServerSession = playerLeftGameServerSession;
		this.playerRightGameServerSession = playerRightGameServerSession;

		initializePlayers();
	}

	@Override
	public void initializePlayers() {
		Player playerLeft = new Player(playerLeftGameServerSession.getUser(), Player.PlayerPosition.LEFT);
		Player playerRight = new Player(playerRightGameServerSession.getUser(), Player.PlayerPosition.RIGHT);

		this.setPlayerLeft(playerLeft);
		this.setPlayerRight(playerRight);
	}

	@Override
	public void updatePlayerHitPoints(PlayerPosition playerPosition, Shot shot) {
		Player player = null;
		switch (playerPosition) {
			case LEFT :
				player = getPlayerLeft();
				break;
			case RIGHT :
				player = getPlayerRight();
				break;
		}

		int oldHitPoints = player.getHitPoints();
		int newHitPoints = oldHitPoints - shot.getDamage();
		logger.info("Changing Hitpoints of player " + player.getUser().getName() + " from " + oldHitPoints + " to " + newHitPoints);

		try {
			player.setHitPoints(newHitPoints);
			playerLeftGameServerSession.updatePlayerHitPoints(playerPosition, newHitPoints);
			playerRightGameServerSession.updatePlayerHitPoints(playerPosition, newHitPoints);

			if (newHitPoints <= 0) {
				logger.info("Player " + player.getUser().getName() + " lost the game!");
				if (playerPosition == Player.PlayerPosition.LEFT) {
					playerLeftGameServerSession.youLost();
					playerRightGameServerSession.youWon();
				} else {
					playerLeftGameServerSession.youWon();
					playerRightGameServerSession.youLost();
				}
				stopTimer();
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (SnowWarsRMIException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void shoot(Shot shot) {
		try {
			playerLeftGameServerSession.receiveShot(shot);
			playerRightGameServerSession.receiveShot(shot);
			this.getShots().add(shot);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void setPlayerReady() {
		playerReadyCount.addAndGet(1);

		logger.info("Player is ready. There are " + playerReadyCount.get() + " ready players now.");
		if (playerReadyCount.get() == 2) {
			startCountdown();
		}
	}

	private void startCountdown() {
		int countdownSeconds = 3;

		while (countdownSeconds > 0) {
			logger.info("New Game starts in " + countdownSeconds + "...");
			try {
				playerLeftGameServerSession.setCountdownTime(countdownSeconds);
				playerRightGameServerSession.setCountdownTime(countdownSeconds);
			} catch (RemoteException e) {
				e.printStackTrace();
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			countdownSeconds--;
		}

		logger.info("Game starts NOW!");
		
		try {
			playerLeftGameServerSession.countdownEnded();
			playerRightGameServerSession.countdownEnded();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
