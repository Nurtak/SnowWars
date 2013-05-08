package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ch.hsr.se2p.snowwars.model.GameServer;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.session.client.GameClientSessionInterface;

public class GameServerSession extends UnicastRemoteObject implements
		GameServerSessionInterface {

	private static final long serialVersionUID = 8590130911163707937L;

	private GameClientSessionInterface gameClientSession;
	private GameServer gameServer;

	public GameServerSession() throws RemoteException {
	}

	public void setGameClientSessionInterface(GameClientSessionInterface gcsi) {
		this.gameClientSession = gcsi;
	}

	public void setGameServer(GameServer gameServer) {
		this.gameServer = gameServer;
	}

	public GameClientSessionInterface getGameClientSessionInterface() {
		return gameClientSession;
	}

	@Override
	public void startBuildingSnowball() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shoot(Shot shot) {
		gameServer.shoot(shot);
	}

	@Override
	public LobbyServerSessionInterface chickenOut() throws SnowWarsRMIException {
		return null;
	}

}
