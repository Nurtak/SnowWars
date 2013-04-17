package ch.hsr.se2p.snowwars.view.game;

import java.util.Observable;
import java.util.Stack;

import ch.hsr.se2p.snowwars.application.SnowWarsClient;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.network.SnowWarsRMIException;

public class ViewGameController extends Observable {

	private SnowWarsClient snowWarsClient;
	private String errorMessage = "";
	private Stack<Shot> shotStack = new Stack<Shot>();

	public ViewGameController(SnowWarsClient snc) {
		this.snowWarsClient = snc;
	}

	public void sendShotRequest(Shot shot) {
		try {
			snowWarsClient.sendShotRequestToServer(shot);
		} catch (SnowWarsRMIException e) {
			showErrorMessage("Not connected to server!");
		}
	}

	public void receivedShot(Shot shot) {
		shotStack.add(shot);
		updateObserver();
	}

	public Shot getNextShot() {
		return shotStack.pop();
	}

	public void showErrorMessage(String error) {
		this.errorMessage = error;
		updateObserver();
	}

	public String getErrorMessage() {
		String returnMessage = errorMessage;
		errorMessage = "";
		return returnMessage;
	}

	private void updateObserver() {
		this.setChanged();
		this.notifyObservers();
	}

}
