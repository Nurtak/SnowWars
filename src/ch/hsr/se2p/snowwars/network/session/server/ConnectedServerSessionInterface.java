package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.Remote;

import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.exception.UsernameAlreadyTakenException;

public interface ConnectedServerSessionInterface extends Remote {

    public boolean isNameAvalible(String name);

    public LobbyServerSessionInterface registerAtLobby(User user) throws SnowWarsRMIException, UsernameAlreadyTakenException;

}
