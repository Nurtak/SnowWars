package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

import ch.hsr.se2p.snowwars.model.Lobby;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.client.RMIClientInterface;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.exception.UserIsNotInLobbyException;
import ch.hsr.se2p.snowwars.network.exception.UsernameAlreadyTakenException;

public class LobbyServerSession extends UnicastRemoteObject implements LobbyServerSessionInterface {

    private static final long serialVersionUID = -3804423975783216087L;
    private RMIClientInterface client;
    private User user;
    private Lobby lobby;

    public LobbyServerSession(RMIClientInterface client, User user, Lobby lobby) throws RemoteException, UsernameAlreadyTakenException {
        this.client = client;
        this.user = user;
        this.lobby = lobby;

        lobby.addNewUser(user, this);
    }

    @Override
    public ConnectedServerSessionInterface leaveLobby() throws SnowWarsRMIException {
        lobby.leave(user);
        ConnectedServerSession connectedSession;
        try {
            connectedSession = new ConnectedServerSession(client, lobby);
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
        lobby.inviteUser(user, selectedUser);
    }

    @Override
    public GameServerSessionInterface getGameSessionInterface() throws SnowWarsRMIException {
        GameServerSession gameSession;
        try {
            gameSession = new GameServerSession(client, user, lobby);
        } catch (RemoteException e) {
            throw new SnowWarsRMIException(e.getMessage());
        }
        return gameSession;
    }
}
