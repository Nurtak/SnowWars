package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;

public interface GameServerSessionInterface extends Remote {

    public void startBuildingSnowball() throws RemoteException;

    public void shoot(Shot shot) throws RemoteException;
    
    public LobbyServerSessionInterface chickenOut() throws RemoteException, SnowWarsRMIException;
}
