package ch.hsr.se2p.snowwars.model;

import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSession;

public class GameServer extends AbstractGame {

	GameServerSession playerLeftGameServerSession;
	GameServerSession playerRightGameServerSession;

	public GameServer(GameServerSession playerLeftGameServerSession,
			GameServerSession playerRightGameServerSession) {
		this.playerLeftGameServerSession = playerLeftGameServerSession;
		this.playerRightGameServerSession = playerRightGameServerSession;

		initializePlayers();
	}

	@Override
	public void initializePlayers() {
		Player playerLeft = new Player(playerLeftGameServerSession.getUser(),
				Player.PlayerPosition.LEFT);
		Player playerRight = new Player(playerRightGameServerSession.getUser(),
				Player.PlayerPosition.RIGHT);

		this.setPlayerLeft(playerLeft);
		this.setPlayerRight(playerRight);
	}

	@Override
	public void updatePlayerHitPoints(PlayerPosition playerPosition, Shot shot) {
		int hitPoints = 0;
		switch(playerPosition){
		case LEFT:
			hitPoints = getPlayerLeft().getHitPoints();
			break;
		case RIGHT:
			hitPoints = getPlayerRight().getHitPoints();
			break;
		}
		
		hitPoints -= shot.getDamage();
		try {
			playerLeftGameServerSession.getGameClientSessionInterface().updatePlayerHitPoints(playerPosition, hitPoints);
			playerRightGameServerSession.getGameClientSessionInterface().updatePlayerHitPoints(playerPosition, hitPoints);
		} catch (RemoteException e) {
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
