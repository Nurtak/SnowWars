package ch.hsr.se2p.snowwars.network.session.client;

import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSessionInterface;

public class GameClientSession implements GameClientSessionInterface {
    
    private LobbyClientSession lobbyClientSession;

    public GameClientSession(LobbyClientSession lobbyClientSession) {
        this.lobbyClientSession = lobbyClientSession;
    }
    
    @Override
    public void receiveShot(Shot shot) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setHitPoints(Player player, int hitPoints) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public LobbyServerSessionInterface youWon() throws SnowWarsRMIException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public LobbyServerSessionInterface youLost() throws SnowWarsRMIException {
        // TODO Auto-generated method stub
        return null;
    }

}
