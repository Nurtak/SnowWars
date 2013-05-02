package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.Remote;

import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;

public interface GameServerSessionInterface extends Remote {

    public void startBuildingSnowball();

    public void shoot(Shot shot);
    
    public LobbyServerSessionInterface chickenOut() throws SnowWarsRMIException;
}
