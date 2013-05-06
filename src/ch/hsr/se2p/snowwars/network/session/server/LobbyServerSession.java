package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

import ch.hsr.se2p.snowwars.model.Lobby;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.exception.UserIsNotInLobbyException;
import ch.hsr.se2p.snowwars.network.exception.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.network.session.client.LobbyClientSessionInterface;

public class LobbyServerSession extends UnicastRemoteObject implements LobbyServerSessionInterface {

    private static final long serialVersionUID = -3804423975783216087L;

    private User user;
    private Lobby lobby;
    private LobbyClientSessionInterface lobbyClientSessionInterface;

    public LobbyServerSession(User user, Lobby lobby, LobbyClientSessionInterface lobbyClientSessionInterface) throws RemoteException,
            UsernameAlreadyTakenException {
        this.user = user;
        this.lobby = lobby;
        this.lobbyClientSessionInterface = lobbyClientSessionInterface;
        lobby.addSession(this);
    }

    @Override
    public ConnectedServerSessionInterface leaveLobby() throws SnowWarsRMIException {
        lobby.leave(this);
        ConnectedServerSession connectedSession;
        try {
            connectedSession = new ConnectedServerSession(lobby);
        } catch (RemoteException e) {
            throw new SnowWarsRMIException(e.getMessage());
        }
        return connectedSession;
    }

    @Override
    public Set<User> getUsers() {
        return lobby.getUsers();
    }

    @Override
    public void inviteUser(User selectedUser) throws UserIsNotInLobbyException {
//        lobby.inviteUser(this, selectedUser);
    }

    /**
     * @return the lobbyClientSessionInterface
     */
    public LobbyClientSessionInterface getLobbyClientSessionInterface() {
        return lobbyClientSessionInterface;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

}
