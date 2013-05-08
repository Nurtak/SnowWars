package ch.hsr.se2p.snowwars.model;

import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;

public class GameClient extends AbstractGame{

	public GameClient(GameServerSessionInterface gameServerSessionInterface) {
		super(gameServerSessionInterface);
	}

	@Override
	public void updatePlayerHitPoints() {}

}
