package ch.hsr.se2p.snowwars.view.game.controlling;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.application.SnowWarsClientInterface;
import ch.hsr.se2p.snowwars.model.GameClient;
import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.client.session.GameClientSessionInterface;
import ch.hsr.se2p.snowwars.view.game.presentation.GameFrame;

public class ViewGameController extends UnicastRemoteObject implements GameClientSessionInterface {
	private static final long serialVersionUID = -7593697054318420277L;

	private final static Logger logger = Logger.getLogger(ViewGameController.class.getPackage().getName());

	private GameFrame gameFrame;
	private ViewGameModel viewGameModel;
	private GameClient gameClient;
	private SnowWarsClientInterface snowWarsClientInterface;
	private SoundPlayer soundPlayer;

	public ViewGameController(SnowWarsClientInterface snowWarsClientInterface, GameClient game) throws RemoteException {
		this.gameClient = game;
		this.snowWarsClientInterface = snowWarsClientInterface;
		this.soundPlayer = new SoundPlayer();
	}

	public void quitGame() {
		int returnValue = JOptionPane.showConfirmDialog(gameFrame, "Do you really want to quit the game?", "Chicken out", JOptionPane.YES_NO_OPTION);
		if (returnValue == 0) {
			logger.info("Quitting the game...");
			viewGameModel.quitGame();
			backToLobby();
		}
	}

	public void showGui() {
		this.gameClient.initializePlayers();
		this.viewGameModel = new ViewGameModel(gameClient);
		this.gameFrame = new GameFrame(this, this.viewGameModel);
		this.gameFrame.setVisible(true);
		this.soundPlayer.playWindHowl();
		askUserIfReady();
	}

	@Override
	public void receiveShot(Shot shot) throws RemoteException {
		logger.info("Received shot from server: " + shot.toString());
		this.gameClient.shoot(shot);
	}
	
	@Override
	public void playerIsBuilding(PlayerPosition playerPosition) throws RemoteException {
		viewGameModel.startBuildTimer(playerPosition);
	}
	
	private void askUserIfReady() {
		JOptionPane.showMessageDialog(gameFrame, "Welcome to SnowWars. \nAre you ready?", "Welcome!", JOptionPane.INFORMATION_MESSAGE);
		try {
			gameClient.getGameServerSessionInterface().setReady();
		} catch (RemoteException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void youWon() throws RemoteException {
		logger.info("Received Won-Notification! This SnowWarsClient won the match!");
		soundPlayer.playWon();
		JOptionPane.showMessageDialog(gameFrame, "You won the match!", "=)", JOptionPane.INFORMATION_MESSAGE);
		backToLobby();
	}

	@Override
	public void youLost() throws RemoteException {
		logger.info("Received Lost-Notification! This SnowWarsClient lost the match!");
		soundPlayer.playLost();
		JOptionPane.showMessageDialog(gameFrame, "You lost the match!", ":(", JOptionPane.ERROR_MESSAGE);
		backToLobby();
	}
	
	@Override
	public void opponentQuitGame() throws RemoteException {
		logger.info("Your opponent chickened out!");
		soundPlayer.playWon();
		JOptionPane.showMessageDialog(gameFrame, "You won the match, your opponent gave up!", ":)", JOptionPane.INFORMATION_MESSAGE);
		backToLobby();
	}
	
	private void backToLobby(){
		this.gameFrame.setVisible(false);
		this.soundPlayer.stopWindHowl();
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
		this.soundPlayer.playCountdown();
		this.viewGameModel.setCountDownTime(time);
	}

	@Override
	public void countdownEnded() throws RemoteException {
		logger.info("SnowWars game starts NOW!");
		this.soundPlayer.playStart();
		this.viewGameModel.setCountdownActive(false);
	}
}