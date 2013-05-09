package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ch.hsr.se2p.snowwars.model.GameServer;
import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.session.client.GameClientSessionInterface;

public class GameServerSession extends UnicastRemoteObject implements
		GameServerSessionInterface {

	private static final long serialVersionUID = 8590130911163707937L;

	private User user;
	private GameClientSessionInterface gameClientSession;
	private GameServer gameServer;

	public GameServerSession(User user) throws RemoteException {
		this.user = user;
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

	@Override
	public Player getLeftPlayer() throws RemoteException {
		return gameServer.getPlayerLeft();
	}

	@Override
	public Player getRightPlayer() throws RemoteException {
		return gameServer.getPlayerRight();
	}
	
	public User getUser(){
		return this.user;
	}

	@Override
	public void setGameClientSessionInterface(GameClientSessionInterface gcsi) throws RemoteException {
		this.gameClientSession = gcsi;
	}

}
