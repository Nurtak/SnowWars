package ch.hsr.se2p.snowwars.network.serversession;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.clientsession.LobbyClientSessionInterface;
import ch.hsr.se2p.snowwars.util.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.util.exception.UsernameAlreadyTakenException;

public interface ConnectedServerSessionInterface extends Remote {

	public boolean isNameAvailable(String name) throws RemoteException;

	public LobbyServerSessionInterface registerAtLobby(LobbyClientSessionInterface lobbyClientSessionInterface, User user) throws RemoteException, SnowWarsRMIException, UsernameAlreadyTakenException;

}
