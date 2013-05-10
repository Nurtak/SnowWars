package ch.hsr.se2p.snowwars.controller.game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;


import ch.hsr.se2p.snowwars.application.SnowWarsClientInterface;
import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.model.GameClient;
import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.session.client.GameClientSessionInterface;
import ch.hsr.se2p.snowwars.view.game.GameFrame;

public class ViewGameController extends UnicastRemoteObject implements
		GameClientSessionInterface {
	private static final long serialVersionUID = -7593697054318420277L;

	private final static Logger logger = Logger
			.getLogger(ViewGameController.class.getPackage().getName());

	// private GameFrame gameFrame;
	private ViewGameModel viewGameModel;
	private GameClient game;
	private SnowWarsClientInterface snowWarsClientInterface;

	public ViewGameController(SnowWarsClientInterface snowWarsClientInterface,
			GameClient game) throws RemoteException {
		this.game = game;
		this.snowWarsClientInterface = snowWarsClientInterface;
	}

	public void closeProgram() {
		snowWarsClientInterface.closeProgram();
	}

	public void showGui() {
		this.game.initializePlayers();
		this.viewGameModel = new ViewGameModel(game);
		new GameFrame(this, this.viewGameModel);
		this.viewGameModel.setGuiVisible(true);
	}

	@Override
	public void receiveShot(Shot shot) throws RemoteException {
		logger.info("Received shot from server: " + shot.toString());

		this.game.shoot(shot);
	}

	@Override
	public void youWon() throws SnowWarsRMIException, RemoteException {
		logger.info("Received Won-Notification! This SnowWarsClient won the match!");
	}

	@Override
	public void youLost() throws SnowWarsRMIException, RemoteException {
		logger.info("Received Lost-Notification! This SnowWarsClient lost the match!");
	}

	@Override
	public void updatePlayerHitPoints(PlayerPosition playerPosition,
			int hitPoints) throws RemoteException {
		switch (playerPosition) {
		case LEFT:
			this.game.getPlayerLeft().setHitPoints(hitPoints);
			break;
		case RIGHT:
			this.game.getPlayerRight().setHitPoints(hitPoints);
			break;
		}
		this.game.updateObserver();
	}
}