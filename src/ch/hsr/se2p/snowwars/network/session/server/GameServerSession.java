package ch.hsr.se2p.snowwars.network.session.server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

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
	private PlayerPosition playerPosition;
	private GameClientSessionInterface gameClientSession;
	private GameServer gameServer;

	public GameServerSession(User user) throws RemoteException {
		this.user = user;
	}

	public void setGameServer(GameServer gameServer) {
		this.gameServer = gameServer;

		// set this players position
		if (gameServer.getPlayerLeft().getUser().equals(user)) {
			playerPosition = PlayerPosition.LEFT;
		} else {
			playerPosition = PlayerPosition.RIGHT;
		}
	}

	@Override
	public void startBuilding() {
		logger.info("Player " + user.getName() + " is now building a snowball...");
		gameServer.build(playerPosition);
	}

	@Override
	public void shoot(Shot shot) {
		logger.info("Received shot from player " + user.getName() + ": " + shot.toString());
		shot.setShotOrigin(playerPosition);
		gameServer.shoot(shot);
	}

	@Override
	public void quitGame() throws RemoteException {
		logger.info("Player " + user.getName() + " chickened out!");

		gameServer.quitGame(playerPosition);
	}

	@Override
	public Player getLeftPlayer() throws RemoteException {
		return gameServer.getPlayerLeft();
	}

	@Override
	public Player getRightPlayer() throws RemoteException {
		return gameServer.getPlayerRight();
	}

	@Override
	public PlayerPosition getPlayerPosition() throws RemoteException {
		return playerPosition;
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
	public void youWon() throws RemoteException {
		// starts in new thread, because server don't wants to wait for
		// user-answer
		new Thread() {
			public void run() {
				try {
					gameClientSession.youWon();
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}.start();
	}

	@Override
	public void youLost() throws RemoteException {
		// starts in new thread, because server don't wants to wait for
		// user-answer
		new Thread() {
			public void run() {
				try {
					gameClientSession.youLost();
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}.start();
	}

	@Override
	public void setCountdownTime(final int time) throws RemoteException {
		// starts in new thread, because server don't wants to wait for
		// user-answer
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
		// starts in new thread, because server don't wants to wait for
		// user-answer
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

	@Override
	public void playerIsBuilding(final PlayerPosition playerPosition) throws RemoteException {
		// starts in new thread, because server don't wants to wait for
		// user-answer
		new Thread() {
			public void run() {
				try {
					gameClientSession.playerIsBuilding(playerPosition);
				} catch (RemoteException e) {

					logger.error(e.getMessage(), e);
				}
			}
		}.start();
	}

	@Override
	public void opponentQuitGame() throws RemoteException {
		// starts in new thread, because server don't wants to wait for
		// user-answer
		new Thread() {
			public void run() {
				try {
					gameClientSession.opponentQuitGame();
				} catch (RemoteException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}.start();
	}
}