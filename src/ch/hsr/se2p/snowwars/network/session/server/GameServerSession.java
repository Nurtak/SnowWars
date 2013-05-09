package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.model.GameServer;
import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.session.client.GameClientSessionInterface;

public class GameServerSession extends UnicastRemoteObject implements
		GameServerSessionInterface {
	private final static Logger logger = Logger
			.getLogger(GameServerSession.class.getPackage().getName());
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
		logger.info("Received shot from player " + user.getName() + ": "
				+ shot.toString());

		// set first shot-coordinates
		if (gameServer.getPlayerLeft().getUser().equals(user)) {
			shot.getShotObject().setX(Player.SNOWBALL_LEFT_THROW_POS_X);
			shot.getShotObject().setY(Player.SNOWBALL_LEFT_THROW_POS_Y);
		} else {
			shot.getShotObject().setX(Player.SNOWBALL_RIGHT_THROW_POS_X);
			shot.getShotObject().setY(Player.SNOWBALL_RIGHT_THROW_POS_Y);
		}

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

	public User getUser() {
		return this.user;
	}

	@Override
	public void setGameClientSessionInterface(GameClientSessionInterface gcsi)
			throws RemoteException {
		this.gameClientSession = gcsi;
	}

}
