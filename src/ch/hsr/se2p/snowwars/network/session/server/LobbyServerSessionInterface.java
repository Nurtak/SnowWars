package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.exception.UserIsNotInLobbyException;

public interface LobbyServerSessionInterface extends Remote {

    public Set<User> getUsers() throws RemoteException;

    public void inviteUser(User selectedUser) throws RemoteException, UserIsNotInLobbyException;

    public ConnectedServerSessionInterface leaveLobby() throws RemoteException, SnowWarsRMIException;

    public GameServerSessionInterface getGameSessionInterface() throws RemoteException, SnowWarsRMIException;
}
