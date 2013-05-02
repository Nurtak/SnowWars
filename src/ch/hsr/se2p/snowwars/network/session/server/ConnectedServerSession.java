package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ch.hsr.se2p.snowwars.model.Lobby;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.client.RMIClientInterface;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.exception.UsernameAlreadyTakenException;

public class ConnectedServerSession extends UnicastRemoteObject implements ConnectedServerSessionInterface {

    private static final long serialVersionUID = 217560933838608559L;
    private RMIClientInterface client;
    private Lobby lobby;

    public ConnectedServerSession(RMIClientInterface client, Lobby lobby) throws RemoteException {
        this.client = client;
        this.lobby = lobby;
    }

    @Override
    public boolean isNameAvalible(String name) {
        return lobby.isNameAvalible(name);
    }

    @Override
    public LobbyServerSessionInterface registerAtLobby(User user) throws SnowWarsRMIException, UsernameAlreadyTakenException {
        LobbyServerSession lobbySession;
        try {
            lobbySession = new LobbyServerSession(client, user, lobby);
        } catch (RemoteException e) {
            throw new SnowWarsRMIException(e.getMessage());
        }
        return lobbySession;
    }

}
