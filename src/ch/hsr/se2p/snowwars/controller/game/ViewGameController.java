package ch.hsr.se2p.snowwars.controller.game;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ch.hsr.se2p.snowwars.application.SnowWarsClientInterface;
import ch.hsr.se2p.snowwars.model.AbstractGame;
import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.exception.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.session.client.GameClientSessionInterface;
import ch.hsr.se2p.snowwars.network.session.server.LobbyServerSessionInterface;
import ch.hsr.se2p.snowwars.view.game.GameFrame;

public class ViewGameController extends UnicastRemoteObject implements
		GameClientSessionInterface {
	private static final long serialVersionUID = -7593697054318420277L;

	// private final static Logger logger = Logger
	// .getLogger(ViewGameController.class.getPackage().getName());

	// private GameFrame gameFrame;
	private ViewGameModel viewGameModel;
	private SnowWarsClientInterface snowWarsClientInterface;

	public ViewGameController(SnowWarsClientInterface snowWarsClientInterface, AbstractGame game) throws RemoteException {
		this.snowWarsClientInterface = snowWarsClientInterface;
		this.viewGameModel = new ViewGameModel(game);
		new GameFrame(this, this.viewGameModel);
	}

	public void closeProgram() {
		snowWarsClientInterface.closeProgram();
	}

	public void showGui() {
		this.viewGameModel.setGuiVisible(true);
	}

	@Override
	public void receiveShot(Shot shot) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public LobbyServerSessionInterface youWon() throws SnowWarsRMIException,
			RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LobbyServerSessionInterface youLost() throws SnowWarsRMIException,
			RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePlayer(Player player) throws RemoteException {
		// TODO Auto-generated method stub

	}
}