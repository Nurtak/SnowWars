package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.Remote;
import java.rmi.RemoteException;


import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.session.client.GameClientSessionInterface;

public interface GameServerSessionInterface extends Remote {

    public void startBuildingSnowball() throws RemoteException;

    public void shoot(Shot shot) throws RemoteException;
    
    public Player getLeftPlayer() throws RemoteException;
    
    public Player getRightPlayer() throws RemoteException;
    
    public void setGameClientSessionInterface(GameClientSessionInterface gcsi) throws RemoteException;
    
    public LobbyServerSessionInterface chickenOut() throws RemoteException, SnowWarsRMIException;
}
