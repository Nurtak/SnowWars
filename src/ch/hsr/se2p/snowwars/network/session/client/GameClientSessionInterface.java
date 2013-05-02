package ch.hsr.se2p.snowwars.network.session.client;

import java.rmi.Remote;

import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSessionInterface;

public interface GameClientSessionInterface extends Remote {

    public void receiveShot(Shot shot);

    public void setHitPoints(Player player, int hitPoints);

    public LobbyServerSessionInterface youWon() throws SnowWarsRMIException;

    public LobbyServerSessionInterface youLost() throws SnowWarsRMIException;
}
