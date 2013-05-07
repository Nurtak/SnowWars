package ch.hsr.se2p.snowwars.application;

import ch.hsr.se2p.snowwars.network.session.server.ConnectedServerSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.GameServerSessionInterface;

public interface SnowWarsClientInterface {

    public void enterLobby(ConnectedServerSessionInterface connectedServerSessionInterface);

    public void enterGame(SnowWarsClientInterface snowWarsClientInterface, GameServerSessionInterface gameServerSessionInterface);
}
