package ch.hsr.se2p.snowwars.network.server;

import java.rmi.RemoteException;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.model.Lobby;
import ch.hsr.se2p.snowwars.network.session.server.ConnectedServerSession;
import ch.hsr.se2p.snowwars.network.session.server.ConnectedServerSessionInterface;

public class RMIServer implements RMIServerInterface {

    private final static Logger logger = Logger.getLogger(RMIServer.class.getPackage().getName());
    private Lobby lobby;

    public RMIServer(Lobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public ConnectedServerSessionInterface connect() throws RemoteException {
        ConnectedServerSession connectedSession = new ConnectedServerSession(lobby);
        return connectedSession;
    }

}
