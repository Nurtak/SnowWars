package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.Remote;
import java.rmi.RemoteException;


import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.exceptions.UsernameAlreadyTakenException;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.session.client.LobbyClientSessionInterface;

public interface ConnectedServerSessionInterface extends Remote {

    public boolean isNameAvailable(String name) throws RemoteException;

    public LobbyServerSessionInterface registerAtLobby(LobbyClientSessionInterface lobbyClientSessionInterface, User user) throws RemoteException,
            SnowWarsRMIException, UsernameAlreadyTakenException;

}
