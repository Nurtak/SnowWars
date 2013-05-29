package ch.hsr.se2p.snowwars.server.network;

import java.rmi.RemoteException;

import ch.hsr.se2p.snowwars.network.serversession.ConnectedServerSessionInterface;
import ch.hsr.se2p.snowwars.network.serversession.RMIServerInterface;
import ch.hsr.se2p.snowwars.server.applicationcontrolling.ConnectedServerSession;
import ch.hsr.se2p.snowwars.server.applicationcontrolling.Lobby;

public class RMIServer implements RMIServerInterface {

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
