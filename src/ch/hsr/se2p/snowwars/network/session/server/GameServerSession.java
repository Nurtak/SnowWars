package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.model.GameServer;
import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.model.User;
import ch.hsr.se2p.snowwars.network.session.client.GameClientSessionInterface;

public class GameServerSession extends UnicastRemoteObject implements GameServerSessionInterface, GameClientSessionInterface {
	private final static Logger logger = Logger.getLogger(GameServerSession.class.getPackage().getName());
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

	@Override
	public void startBuildingSnowball() {
		// TODO Auto-generated method stub
	}

	@Override
	public void shoot(Shot shot) {
		logger.info("Received shot from player " + user.getName() + ": " + shot.toString());

		// set shot origin
		if (gameServer.getPlayerLeft().getUser().equals(user)) {
			shot.setShotOrigin(PlayerPosition.LEFT);
		} else {
			shot.setShotOrigin(PlayerPosition.RIGHT);
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
	public void setGameClientSessionInterface(GameClientSessionInterface gcsi) throws RemoteException {
		this.gameClientSession = gcsi;
	}
	
	@Override
	public void setReady() throws RemoteException {
		gameServer.setPlayerReady();
	}

	@Override
	public void receiveShot(Shot shot) throws RemoteException {
		gameClientSession.receiveShot(shot);
	}

	@Override
	public void updatePlayerHitPoints(PlayerPosition playerPosition, int hitPoints) throws RemoteException {
		gameClientSession.updatePlayerHitPoints(playerPosition, hitPoints);
	}

	@Override
	public void youWon() throws SnowWarsRMIException, RemoteException {
		// starts in new thread, because server don't wants to wait for user-answer
		new Thread() {
			public void run() {
				try {
					gameClientSession.youWon();
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
				} catch (SnowWarsRMIException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}.start();
	}

	@Override
	public void youLost() throws SnowWarsRMIException, RemoteException {
		// starts in new thread, because server don't wants to wait for user-answer
		new Thread() {
			public void run() {
				try {
					gameClientSession.youLost();
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
				} catch (SnowWarsRMIException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}.start();
	}

	@Override
	public void setCountdownTime(final int time) throws RemoteException {
		// starts in new thread, because server don't wants to wait for user-answer
		new Thread() {
			public void run() {
				try {
					gameClientSession.setCountdownTime(time);
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}.start();
	}

	@Override
	public void countdownEnded() throws RemoteException {
		// starts in new thread, because server don't wants to wait for user-answer
		new Thread() {
			public void run() {
				try {
					gameClientSession.countdownEnded();
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}.start();
	}
}