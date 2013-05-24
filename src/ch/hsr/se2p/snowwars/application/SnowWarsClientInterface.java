package ch.hsr.se2p.snowwars.application;

import ch.hsr.se2p.snowwars.network.server.session.ConnectedServerSessionInterface;
import ch.hsr.se2p.snowwars.network.server.session.GameServerSessionInterface;

public interface SnowWarsClientInterface {

	public void enterLobby(ConnectedServerSessionInterface connectedServerSessionInterface);

	public void enterGame(GameServerSessionInterface gameServerSessionInterface);
	
	public void startProgram();
}
