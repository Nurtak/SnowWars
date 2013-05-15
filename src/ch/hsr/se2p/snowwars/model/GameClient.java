package ch.hsr.se2p.snowwars.model;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;

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

	public GameServerSessionInterface getGameServerSessionInterface(){
		return this.gameServerSessionInterface;
	}
	
	@Override
	public void shoot(Shot shot) {
		this.getShots().add(shot);
	}

	@Override
	public void updatePlayerHitPoints(PlayerPosition playerPosition, Shot shot) {
	}
}
