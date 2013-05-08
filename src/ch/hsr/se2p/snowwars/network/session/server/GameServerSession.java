package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ch.hsr.se2p.snowwars.model.GameServer;
import ch.hsr.se2p.snowwars.model.Lobby;
import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.session.client.GameClientSessionInterface;

public class GameServerSession extends UnicastRemoteObject implements GameServerSessionInterface {

    private static final long serialVersionUID = 8590130911163707937L;
    private User user;
    
    private GameClientSessionInterface gameClientSession;
    private GameServer gameServer;
    
    public GameServerSession(GameServer gameServer, GameClientSessionInterface gcs) throws RemoteException {
    	this.gameClientSession = gcs;
    	this.gameServer = gameServer;
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
