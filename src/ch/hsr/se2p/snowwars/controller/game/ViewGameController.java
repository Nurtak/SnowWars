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
import ch.hsr.se2p.snowwars.model.Throw;

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

	private GraphicalPlayer player;

	private Timer redrawTimer;

	private SnowWarsClient snowWarsClient;
	private boolean noConnectionError;

	public ViewGameController(SnowWarsClient snc) {
		this.snowWarsClient = snc;

		try {
			player = new GraphicalPlayer();
			graphicalObjects.add(player);
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

	public void checkCollision() {
		Rectangle r1 = player.getBounds();
		for (GraphicalSnowball graphicalSnowball : graphicalSnowballs) {
			if (!graphicalSnowball.isVisible()) {
				continue;
			}

			Rectangle r2 = graphicalSnowball.getBounds();

			if (r2.intersects(r1)) {
				graphicalSnowball.stopSnowball();
				graphicalSnowball.startSplashingAnimation();
			}
			for (GraphicalSnowball graphicalSnowball2 : graphicalSnowballs) {
				if (!graphicalSnowball2.isVisible()) {
					continue;
				}
				if (graphicalSnowball2 != graphicalSnowball) {
					Rectangle r3 = graphicalSnowball2.getBounds();
					if (r2.intersects(r3)) {
						graphicalSnowball.stopSnowball();
						graphicalSnowball.startSplashingAnimation();
					}
				}

			}
		}

	}

	public void receivedThrow(Throw receivedThrow) {
		synchronized (graphicalObjects) {
			GraphicalSnowball gs = new GraphicalSnowball(receivedThrow.getAngle(), receivedThrow.getStrength());
			graphicalObjects.add(gs);
			graphicalSnowballs.add(gs);
			player.startThrowAnimation();
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

	public void sendThrow(final Throw sendThrow) {
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
}
