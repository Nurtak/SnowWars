package ch.hsr.se2p.snowwars.network.session.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;

public interface GameClientSessionInterface extends Remote {

    public void receiveShot(Shot shot) throws RemoteException;

    public void updatePlayer(Player player) throws RemoteException;

    public void youWon() throws SnowWarsRMIException, RemoteException;

    public void youLost() throws SnowWarsRMIException, RemoteException;
}
