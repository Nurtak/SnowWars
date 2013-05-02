package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.exception.UsernameAlreadyTakenException;

public interface ConnectedServerSessionInterface extends Remote {

    public boolean isNameAvalible(String name) throws RemoteException;

    public LobbyServerSessionInterface registerAtLobby(User user) throws RemoteException, SnowWarsRMIException, UsernameAlreadyTakenException;

}
