package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ch.hsr.se2p.snowwars.model.Lobby;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;

public class GameServerSession extends UnicastRemoteObject implements GameServerSessionInterface {

    private static final long serialVersionUID = 8590130911163707937L;
    private Lobby lobby;
    private User user;

    public GameServerSession(Lobby lobby, User user) throws RemoteException {
        this.lobby = lobby;
        this.user = user;
    }

    @Override
    public void startBuildingSnowball() {
        // TODO Auto-generated method stub

    }

    @Override
    public void shoot(Shot shot) {
        // TODO Auto-generated method stub

    }

    @Override
    public LobbyServerSessionInterface chickenOut() throws SnowWarsRMIException {
        // TODO Auto-generated method stub
        return null;
    }

}
