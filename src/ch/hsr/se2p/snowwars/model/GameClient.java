package ch.hsr.se2p.snowwars.model;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.model.Player.PlayerState;
import ch.hsr.se2p.snowwars.network.server.session.GameServerSessionInterface;

public class GameClient extends AbstractGame {

	private final static Logger logger = Logger.getLogger(GameClient.class.getPackage().getName());

	GameServerSessionInterface gameServerSessionInterface;

	public GameClient(GameServerSessionInterface gameServerSessionInterface) {
		this.gameServerSessionInterface = gameServerSessionInterface;
	}

	// called by abstractGame in constructor, server and client got different
	// algorithms to get player
	@Override
	public void initializePlayers() {
		try {
			this.setPlayerLeft(gameServerSessionInterface.getLeftPlayer());
			this.setPlayerRight(gameServerSessionInterface.getRightPlayer());
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void startNewShotRequest(Shot shotRequest) {
		try {
			gameServerSessionInterface.shoot(shotRequest);
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void startNewBuildRequest() {
		try {
			gameServerSessionInterface.startBuilding();
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void quitGame() {
		try {
			gameServerSessionInterface.quitGame();
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public GameServerSessionInterface getGameServerSessionInterface() {
		return this.gameServerSessionInterface;
	}

	@Override
	public void shoot(Shot shot) {
		this.getShots().add(shot);
	}

	public PlayerPosition getPlayerPosition(){
		try {
			return gameServerSessionInterface.getPlayerPosition();
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	
	public void playerIsBuilding(PlayerPosition playerPosition) {
		switch (playerPosition) {
			case LEFT :
				this.getPlayerLeft().setPlayerState(PlayerState.BUILDING);
				break;
			case RIGHT :
				this.getPlayerRight().setPlayerState(PlayerState.BUILDING);
				break;
		}
	}

	@Override
	public void updatePlayerHitPoints(PlayerPosition playerPosition, Shot shot) {
	}
}
