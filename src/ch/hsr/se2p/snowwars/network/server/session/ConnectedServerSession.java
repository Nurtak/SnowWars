package ch.hsr.se2p.snowwars.network.server.session;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.exceptions.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.model.Lobby;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.client.session.LobbyClientSessionInterface;

public class ConnectedServerSession extends UnicastRemoteObject implements ConnectedServerSessionInterface {

	private static final long serialVersionUID = 217560933838608559L;
	private Lobby lobby;

	public ConnectedServerSession(Lobby lobby) throws RemoteException {
		this.lobby = lobby;
	}

	@Override
	public boolean isNameAvailable(String name) {
		return lobby.isNameAvailable(name);
	}

	@Override
	public LobbyServerSessionInterface registerAtLobby(LobbyClientSessionInterface lobbyClientSessionInterface, User user) throws SnowWarsRMIException, UsernameAlreadyTakenException {
		LobbyServerSession lobbySession;
		try {
			lobbySession = new LobbyServerSession(user, lobby, lobbyClientSessionInterface);
		} catch (RemoteException e) {
			throw new SnowWarsRMIException(e.getMessage());
		}
		return lobbySession;
	}

}
