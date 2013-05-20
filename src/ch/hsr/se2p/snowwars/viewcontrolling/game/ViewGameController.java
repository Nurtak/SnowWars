package ch.hsr.se2p.snowwars.viewcontrolling.game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.application.SnowWarsClientInterface;
import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.model.GameClient;
import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.session.client.GameClientSessionInterface;
import ch.hsr.se2p.snowwars.view.game.GameFrame;

public class ViewGameController extends UnicastRemoteObject implements GameClientSessionInterface {
	private static final long serialVersionUID = -7593697054318420277L;

	private final static Logger logger = Logger.getLogger(ViewGameController.class.getPackage().getName());

	private GameFrame gameFrame;
	private ViewGameModel viewGameModel;
	private GameClient gameClient;
	private SnowWarsClientInterface snowWarsClientInterface;

	public ViewGameController(SnowWarsClientInterface snowWarsClientInterface, GameClient game) throws RemoteException {
		this.gameClient = game;
		this.snowWarsClientInterface = snowWarsClientInterface;
	}

	public void closeProgram() {
		snowWarsClientInterface.closeProgram();
	}

	public void showGui() {
		this.gameClient.initializePlayers();
		this.viewGameModel = new ViewGameModel(gameClient);
		this.gameFrame = new GameFrame(this, this.viewGameModel);
		this.viewGameModel.setGuiVisible(true);
		
		askUserIfReady();
	}

	@Override
	public void receiveShot(Shot shot) throws RemoteException {
		logger.info("Received shot from server: " + shot.toString());
		this.gameClient.shoot(shot);
	}
	
	@Override
	public void playerIsBuilding(PlayerPosition playerPosition) throws RemoteException {	
		logger.info(playerPosition + " player is now building a snowball...");
		gameClient.playerIsBuilding(playerPosition);
	}
	
	private void askUserIfReady(){
		JOptionPane.showMessageDialog(gameFrame, "Welcome to SnowWars. \nAre you ready?", "Welcome!", JOptionPane.INFORMATION_MESSAGE);
		try {
			gameClient.getGameServerSessionInterface().setReady();
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void youWon() throws SnowWarsRMIException, RemoteException {
		logger.info("Received Won-Notification! This SnowWarsClient won the match!");
		
		JOptionPane.showMessageDialog(gameFrame, "You won the match!", "=)", JOptionPane.INFORMATION_MESSAGE);
		
		this.gameFrame.setVisible(false);
		snowWarsClientInterface.startProgram();
	}

	@Override
	public void youLost() throws SnowWarsRMIException, RemoteException {
		logger.info("Received Lost-Notification! This SnowWarsClient lost the match!");
		
		JOptionPane.showMessageDialog(gameFrame, "You lost the match!", ":(", JOptionPane.ERROR_MESSAGE);
		
		this.gameFrame.setVisible(false);
		snowWarsClientInterface.startProgram();
	}

	@Override
	public void updatePlayerHitPoints(PlayerPosition playerPosition, int hitPoints) throws RemoteException {
		logger.info("Received Update of player hitpoints from server...");
		switch (playerPosition) {
			case LEFT :
				this.gameClient.getPlayerLeft().setHitPoints(hitPoints);
				break;
			case RIGHT :
				this.gameClient.getPlayerRight().setHitPoints(hitPoints);
				break;
		}
		this.gameClient.updateObserver();
	}

	@Override
	public void setCountdownTime(int time) throws RemoteException {
		logger.info("SnowWars game starts in " + time + " seconds...");
		this.viewGameModel.setCountDownTime(time);
	}

	@Override
	public void countdownEnded() throws RemoteException {
		logger.info("SnowWars game starts NOW!");
		this.viewGameModel.setCountdownActive(false);
	}
}