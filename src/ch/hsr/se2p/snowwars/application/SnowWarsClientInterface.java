package ch.hsr.se2p.snowwars.application;

import ch.hsr.se2p.snowwars.controller.game.ViewGameController;
import ch.hsr.se2p.snowwars.network.session.server.ConnectedServerSessionInterface;

public interface SnowWarsClientInterface {

	public void enterLobby(ConnectedServerSessionInterface connectedServerSessionInterface);

	public void enterGame(ViewGameController viewGameController);

	public void closeProgram();
}
