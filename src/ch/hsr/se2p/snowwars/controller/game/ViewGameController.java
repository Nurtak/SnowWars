package ch.hsr.se2p.snowwars.controller.game;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.Timer;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.application.SnowWarsClient;
import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Shot;

public class ViewGameController extends Observable implements ActionListener {
	private final static Logger logger = Logger.getLogger(ViewGameController.class.getPackage().getName());

	protected final static int GAME_WIDTH = 1000;
	protected final static int GAME_HEIGHT = 600;
	protected final static String GAME_TITLE = "Snow Wars";

	private final static int TIMER_REDRAW_INTERVAL = 10;
	protected final static int GROUND_LEVEL_Y = 400;

	protected final static int FORCE_REDUCE_FACTOR = 15;
	public final static int FORCE_REDUCE_FACTOR_STRENGTH = 2;
	protected final static double GRAVITATION = 9.81 / 50;

	private ArrayList<GraphicalObject> graphicalObjects = new ArrayList<GraphicalObject>();
	private ArrayList<GraphicalSnowball> graphicalSnowballs = new ArrayList<GraphicalSnowball>();
	
	private GraphicalPlayer playerLeft;
	private GraphicalPlayer playerRight;

	private Timer redrawTimer;

	private SnowWarsClient snowWarsClient;
	private boolean noConnectionError;

	public ViewGameController(SnowWarsClient snc) {
		this.snowWarsClient = snc;

		try {
			playerLeft = new GraphicalPlayer(GraphicalPlayer.PlayerPosition.LEFT);
			playerRight = new GraphicalPlayer(GraphicalPlayer.PlayerPosition.RIGHT);

			graphicalObjects.add(playerLeft);
			graphicalObjects.add(playerRight);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		
		redrawTimer = new Timer(TIMER_REDRAW_INTERVAL, this);
		redrawTimer.start();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		synchronized (graphicalObjects) {
			for (GraphicalObject graphicalObject : graphicalObjects) {
				graphicalObject.updateValues();
				checkCollision();
			}
		}

		this.setChanged();
		this.notifyObservers();
	}

	private void checkCollision() {
		for (GraphicalSnowball graphicalSnowball : graphicalSnowballs) {
			if (!graphicalSnowball.isVisible()) {
				continue;
			}

			checkCollisionWithPlayer(graphicalSnowball);
			checkCollisionWithOtherSnowball(graphicalSnowball);
		}
	}

	private void checkCollisionWithPlayer(GraphicalSnowball activeSnowball) {
		Rectangle snowballRectangle = activeSnowball.getBounds();
		Rectangle playerLeftRectangle = playerLeft.getBounds();
		Rectangle playerRightRectangle = playerRight.getBounds();

		if (snowballRectangle.intersects(playerRightRectangle)) {
			logger.info("Snowball hit right player");
			activeSnowball.stopSnowball();
			activeSnowball.startSplashingAnimation();
		}

		if (snowballRectangle.intersects(playerLeftRectangle)) {
			logger.info("Snowball hit left player");
			activeSnowball.stopSnowball();
			activeSnowball.startSplashingAnimation();
		}
	}

	private void checkCollisionWithOtherSnowball(GraphicalSnowball graphicalSnowball) {
		Rectangle graphicalSnowballRectangle = graphicalSnowball.getBounds();

		for (GraphicalSnowball activeSnowball : graphicalSnowballs) {
			if (!activeSnowball.isVisible()) {
				continue;
			}

			if (activeSnowball != graphicalSnowball) {
				Rectangle activeSnowballRectangle = activeSnowball.getBounds();
				if (activeSnowballRectangle.intersects(graphicalSnowballRectangle)) {
					activeSnowball.stopSnowball();
					activeSnowball.startSplashingAnimation();
				}
			}
		}
	}

	public void receivedShot(Shot receivedShot) {
		synchronized (graphicalObjects) {
			GraphicalSnowball gs = new GraphicalSnowball(receivedShot.getAngle(), receivedShot.getStrength());
			graphicalObjects.add(gs);
			graphicalSnowballs.add(gs);
			playerLeft.startThrowAnimation();
		}

		this.setChanged();
		this.notifyObservers();
	}

	public void showNoConnectionError() {
		this.noConnectionError = true;
		this.setChanged();
		this.notifyObservers();
	}

	public boolean getShowNoConnectionError() {
		boolean error = noConnectionError;
		noConnectionError = false;
		return error;
	}

	public void sendThrow(final Shot sendThrow) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				snowWarsClient.sendShotRequestToServer(sendThrow);
			}
		}).start();
	}

	public void retryConnectToServer() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				snowWarsClient.connectToServer();
			}
		}).start();
	}

	public void closeProgram() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				snowWarsClient.closeProgram();
			}
		}).start();
	}

	public ArrayList<GraphicalObject> getGraphicalObjects() {
		synchronized (graphicalObjects) {
			return this.graphicalObjects;
		}
	}

	public int getGameWidth() {
		return GAME_WIDTH;
	}

	public int getGameHeight() {
		return GAME_HEIGHT;
	}

	public String getGameTitle() {
		return GAME_TITLE;
	}
	
	public Player getPlayerLeft(){
		return this.snowWarsClient.getPlayerLeft();
	}

	public Player getPlayerRight(){
		return this.snowWarsClient.getPlayerRight();
	}
}
