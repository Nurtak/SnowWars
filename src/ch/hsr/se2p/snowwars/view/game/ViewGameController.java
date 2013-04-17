package ch.hsr.se2p.snowwars.view.game;

import java.util.EmptyStackException;
import java.util.Observable;
import java.util.Stack;

import ch.hsr.se2p.snowwars.application.SnowWarsClient;
import ch.hsr.se2p.snowwars.model.Shot;
public class ViewGameController extends Observable {

	private SnowWarsClient snowWarsClient;
	private boolean noConnectionError;
	private Stack<Shot> shotStack = new Stack<Shot>();

	public ViewGameController(SnowWarsClient snc) {
		this.snowWarsClient = snc;
	}

	public void sendShotRequest(Shot shot) {
		snowWarsClient.sendShotRequestToServer(shot);
	}

	public void receivedShot(Shot shot) {
		shotStack.add(shot);
		updateObserver();
	}

	public Shot getNextShot() {
		Shot activeShot = null;
		try{
			activeShot = shotStack.pop();
		} catch(EmptyStackException e){}
		
		return activeShot;
	}

	public void showNoConnectionError() {
		this.noConnectionError = true;
		updateObserver();
	}

	public boolean getShowNoConnectionError() {
		boolean error = noConnectionError;
		noConnectionError = false;
		return error;
	}
	
	public void retryConnectToServer(){
		snowWarsClient.connectToServer();
	}

	private void updateObserver() {
		this.setChanged();
		this.notifyObservers();
	}

}
