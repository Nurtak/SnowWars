package ch.hsr.se2p.snowwars.model;

import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;

public class GameServer extends AbstractGame{

	GameServerSessionInterface playerLeftGameServerSession;
	GameServerSessionInterface playerRightGameServerSession;
	
	public GameServer(GameServerSessionInterface playerLeftGameServerSession, GameServerSessionInterface playerRightGameServerSession) {
		this.playerLeftGameServerSession = playerLeftGameServerSession;
		this.playerRightGameServerSession = playerRightGameServerSession;
	}

	@Override
	public void updatePlayerHitPoints() {
		
	}

}
