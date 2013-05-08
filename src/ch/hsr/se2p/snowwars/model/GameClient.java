package ch.hsr.se2p.snowwars.model;

import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;

public class GameClient extends AbstractGame{

	GameServerSessionInterface gameServerSessionInterface;
	
	public GameClient(GameServerSessionInterface gameServerSessionInterface) {
		this.gameServerSessionInterface = gameServerSessionInterface;
		
		//playerLeft = gameServerSessionInterface.
	}

	@Override
	public void updatePlayerHitPoints() {}

}
