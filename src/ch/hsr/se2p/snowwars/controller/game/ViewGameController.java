package ch.hsr.se2p.snowwars.controller.game;

import java.util.EmptyStackException;
import java.util.Observable;
import java.util.Stack;

import ch.hsr.se2p.snowwars.application.SnowWarsClient;
import ch.hsr.se2p.snowwars.model.Throw;

public class ViewGameController extends Observable {

	private SnowWarsClient snowWarsClient;
	private boolean noConnectionError;
	private Stack<Throw> shotStack = new Stack<Throw>();

	public ViewGameController(SnowWarsClient snc) {
		this.snowWarsClient = snc;
	}

	public void sendThrow(final Throw shot) {
		new Thread(new Runnable(){
			@Override
			public void run() {
				snowWarsClient.sendShotRequestToServer(shot);
			}
		}).start();
	}

	public void receivedThrow(Throw receivedThrow) {
		shotStack.add(receivedThrow);
		updateObserver();
	}

	public Throw getNextShot() {
		Throw activeShot = null;
		try {
			activeShot = shotStack.pop();
		} catch (EmptyStackException e) {
		}

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

	public void retryConnectToServer() {
		new Thread(new Runnable(){
			@Override
			public void run() {
				snowWarsClient.connectToServer();
			}
		}).start();
	}

	public void closeProgram() {
		new Thread(new Runnable(){
			@Override
			public void run() {
				snowWarsClient.closeProgram();
			}
		}).start();
	}

	private void updateObserver() {
		this.setChanged();
		this.notifyObservers();
	}
}
