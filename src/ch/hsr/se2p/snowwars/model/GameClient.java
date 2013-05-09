package ch.hsr.se2p.snowwars.model;

import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;

public class GameClient extends AbstractGame {

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
			e.printStackTrace();
		}
	}

	public void startNewShotRequest(Shot shotRequest) {
		try {
			gameServerSessionInterface.shoot(shotRequest);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void shoot(Shot shot) {
		this.getShots().add(shot);
	}

	@Override
	public void updatePlayerHitPoints(PlayerPosition playerPosition, Shot shot) {}
}
