package ch.hsr.se2p.snowwars.model;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSession;

public class GameServer extends AbstractGame {
	private final static Logger logger = Logger.getLogger(GameServer.class.getPackage().getName());

	GameServerSession playerLeftGameServerSession;
	GameServerSession playerRightGameServerSession;

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

		int hitPoints = player.getHitPoints();
		int newHitPoints = hitPoints - shot.getDamage();
		logger.info("Changing Hitpoints of player " + player.getUser().getName() + " from " + hitPoints + " to " + newHitPoints);

		try {
			player.setHitPoints(newHitPoints);
			playerLeftGameServerSession.getGameClientSessionInterface().updatePlayerHitPoints(playerPosition, hitPoints);
			playerRightGameServerSession.getGameClientSessionInterface().updatePlayerHitPoints(playerPosition, hitPoints);

			if (hitPoints <= 0) {
				logger.info("Player " + player.getUser().getName() + " lost the game!");
				if (playerPosition == Player.PlayerPosition.LEFT) {
					playerLeftGameServerSession.getGameClientSessionInterface().youLost();
					playerRightGameServerSession.getGameClientSessionInterface().youWon();
				} else {
					playerLeftGameServerSession.getGameClientSessionInterface().youWon();
					playerRightGameServerSession.getGameClientSessionInterface().youLost();
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
			playerLeftGameServerSession.getGameClientSessionInterface().receiveShot(shot);
			playerRightGameServerSession.getGameClientSessionInterface().receiveShot(shot);
			this.getShots().add(shot);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
