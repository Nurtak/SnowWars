package ch.hsr.se2p.snowwars.network.session.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSessionInterface;

public interface GameClientSessionInterface extends Remote {

    public void receiveShot(Shot shot) throws RemoteException;

    public void setHitPoints(Player player, int hitPoints) throws RemoteException;

    public LobbyServerSessionInterface youWon() throws SnowWarsRMIException, RemoteException;

    public LobbyServerSessionInterface youLost() throws SnowWarsRMIException, RemoteException;
}
