package ch.hsr.se2p.snowwars.controller.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import ch.hsr.se2p.snowwars.model.AbstractGame;
import ch.hsr.se2p.snowwars.model.GameClient;
import ch.hsr.se2p.snowwars.model.Player.PlayerState;
import ch.hsr.se2p.snowwars.model.Shot;

public class ViewGameModel extends Observable implements Observer, Serializable {
	private static final long serialVersionUID = 9153023421344526026L;

	private GameClient game;

	public final static int GAME_WIDTH = 1000;
	public final static int GAME_HEIGHT = 600;
	protected final static String GAME_TITLE = "Snow Wars";

	private Set<GraphicalSnowball> graphicalSnowballs = new HashSet<GraphicalSnowball>();
	private GraphicalPlayer leftPlayer;
	private GraphicalPlayer rightPlayer;
	private boolean guiVisible = false;

	public ViewGameModel(GameClient game) {
		this.game = game;
		game.addObserver(this);
	}

	public AbstractGame getGame() {
		return game;
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

		ArrayList<Shot> shots = game.getShots();
		boolean found;
		synchronized (shots) {
			for (Shot activeShot : shots) {
				found = false;
				for (GraphicalSnowball activeSnowball : graphicalSnowballs) {
					if (activeSnowball.getShot().equals(activeShot)) {
						found = true;
						break;
					}
				}

				if (!found) {
					graphicalSnowballs.add(new GraphicalSnowball(activeShot));
					
					switch(activeShot.getShotOrigin()){
					case LEFT:
						game.getPlayerLeft().setPlayerState(PlayerState.THROWING);
						break;
					case RIGHT:
						game.getPlayerRight().setPlayerState(PlayerState.THROWING);
						break;
					}
				}
			}
		}

		for (GraphicalSnowball activeGraphicalSnowball : graphicalSnowballs) {
			activeGraphicalSnowball.updateAnimation();
		}

		this.setChanged();
		this.notifyObservers();
	}

	public Set<GraphicalSnowball> getGraphicalSnowballs() {
		return this.graphicalSnowballs;
	}

	public GraphicalPlayer getLeftPlayer() {
		return this.leftPlayer;
	}

	public GraphicalPlayer getRightPlayer() {
		return this.rightPlayer;
	}

	public boolean getGuiVisible() {
		return this.guiVisible;
	}

	public void setGuiVisible(boolean visibility) {
		this.guiVisible = true;
	}

	public void startNewShotRequest(Shot shotRequest) {
		game.startNewShotRequest(shotRequest);
	}
}
