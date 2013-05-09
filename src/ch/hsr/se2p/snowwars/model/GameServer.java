package ch.hsr.se2p.snowwars.model;

import ch.hsr.se2p.snowwars.network.session.server.GameServerSession;

public class GameServer extends AbstractGame{

	GameServerSession playerLeftGameServerSession;
	GameServerSession playerRightGameServerSession;
	
	public GameServer(GameServerSession playerLeftGameServerSession, GameServerSession playerRightGameServerSession) {
		this.playerLeftGameServerSession = playerLeftGameServerSession;
		this.playerRightGameServerSession = playerRightGameServerSession;
		
		initializePlayers();
	}

	@Override
	public void updatePlayerHitPoints() {
		
	}

	@Override
	public void initializePlayers() {
		Player playerLeft = new Player(playerLeftGameServerSession.getUser(), Player.PlayerPosition.LEFT);
		Player playerRight = new Player(playerRightGameServerSession.getUser(), Player.PlayerPosition.RIGHT);
		
		this.setPlayerLeft(playerLeft);
		this.setPlayerRight(playerRight);
	}

}
