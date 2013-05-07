package ch.hsr.se2p.snowwars.controller.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import ch.hsr.se2p.snowwars.application.SnowWarsClient;
import ch.hsr.se2p.snowwars.model.Game;
import ch.hsr.se2p.snowwars.model.Shot;

public class ViewGameController extends Observable implements Observer {
	public final static int GAME_WIDTH = 1000;
	public final static int GAME_HEIGHT = 600;
	protected final static String GAME_TITLE = "Snow Wars";

	private Set<GraphicalSnowball> graphicalSnowballs = new HashSet<GraphicalSnowball>();
	private GraphicalPlayer leftPlayer;
	private GraphicalPlayer rightPlayer;

	private Game game;
	private SnowWarsClient snowWarsClient;
	private boolean noConnectionError;

	public ViewGameController(SnowWarsClient snc, Game game) {
		this.snowWarsClient = snc;
		this.game = game;
		this.game.addObserver(this);
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

	public void closeProgram() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				snowWarsClient.closeProgram();
			}
		}).start();
	}

	public Game getGame() {
		return this.game;
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

	@Override
	public void update(Observable o, Object arg) {
		if (leftPlayer == null) {
			leftPlayer = new GraphicalPlayer(game.getPlayerLeft());
		}

		if (rightPlayer == null) {
			rightPlayer = new GraphicalPlayer(game.getPlayerRight());
		}

		leftPlayer.updateAnimation();
		rightPlayer.updateAnimation();

		ArrayList<Shot> shotList = game.getShots();
		for (Shot activeShot : shotList) {
			graphicalSnowballs.add(new GraphicalSnowball(activeShot));
		}
		for (GraphicalSnowball activeGraphicalSnowball : graphicalSnowballs) {
			activeGraphicalSnowball.updateAnimation();
		}

		this.setChanged();
		this.notifyObservers();
	}
	
	public Set<GraphicalSnowball> getGraphicalSnowballs(){
		return this.graphicalSnowballs;
	}
	
	public GraphicalPlayer getLeftPlayer(){
		return this.leftPlayer;
	}
	
	public GraphicalPlayer getRightPlayer(){
		return this.rightPlayer;
	}	
}