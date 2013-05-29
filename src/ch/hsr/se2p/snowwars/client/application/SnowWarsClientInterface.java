package ch.hsr.se2p.snowwars.client.application;

import ch.hsr.se2p.snowwars.network.serversession.GameServerSessionInterface;

public interface SnowWarsClientInterface {

    public void enterLobby();

    public void enterGame(GameServerSessionInterface gameServerSessionInterface);
}
