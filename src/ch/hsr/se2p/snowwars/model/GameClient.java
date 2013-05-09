package ch.hsr.se2p.snowwars.model;

import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;

public class GameClient extends AbstractGame{

	GameServerSessionInterface gameServerSessionInterface;
	
	public GameClient(GameServerSessionInterface gameServerSessionInterface) {
		this.gameServerSessionInterface = gameServerSessionInterface;
	}

	@Override
	public void updatePlayerHitPoints() {}

	//called by abstractGame in constructor, server and client got different algorithms to get player
	@Override
	public void initializePlayers() {
		try {
			this.setPlayerLeft(gameServerSessionInterface.getLeftPlayer());
			this.setPlayerRight(gameServerSessionInterface.getRightPlayer());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
