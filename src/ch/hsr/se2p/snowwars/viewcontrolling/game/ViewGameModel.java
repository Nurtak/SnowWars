package ch.hsr.se2p.snowwars.viewcontrolling.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import ch.hsr.se2p.snowwars.model.GameClient;
import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.model.Player.PlayerState;
import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.model.Snowball;

public class ViewGameModel extends Observable implements Observer, Serializable {
	private static final long serialVersionUID = 9153023421344526026L;

	private GameClient gameClient;

	public final static int GAME_WIDTH = 1000;
	public final static int GAME_HEIGHT = 600;
	protected final static String GAME_TITLE = "Snow Wars";

	private Set<GraphicalSnowball> graphicalSnowballs = new HashSet<GraphicalSnowball>();
	private GraphicalPlayer leftPlayer;
	private GraphicalPlayer rightPlayer;
	private boolean guiVisible = false;

	private long startBuildTimeLeftPlayer = 0;
	private long startBuildTimeRightPlayer = 0;

	private int countdownTime = -1;
	private boolean countdownActive = true;

	public ViewGameModel(GameClient game) {
		this.gameClient = game;
		game.addObserver(this);
	}

	public Player getPlayerRight() {
		return gameClient.getPlayerRight();
	}

	public Player getPlayerLeft() {
		return gameClient.getPlayerLeft();
	}

	public void quitGame() {
		gameClient.quitGame();
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

	public void startBuildTimer(PlayerPosition playerPosition) {
		switch(playerPosition){
			case LEFT:
				this.startBuildTimeLeftPlayer = System.currentTimeMillis();
				break;
			case RIGHT:
				this.startBuildTimeRightPlayer = System.currentTimeMillis();
				break;
		}
	}
	
	public double getBuildTime(PlayerPosition playerPosition){
		long buildTime = 0;
		switch(playerPosition){
			case LEFT:
				buildTime = System.currentTimeMillis() - startBuildTimeLeftPlayer;
				break;
			case RIGHT:
				buildTime = System.currentTimeMillis() - startBuildTimeRightPlayer;
				break;
		}
		double buildTimeSeconds = buildTime / 1000.0;
		return buildTimeSeconds;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (leftPlayer == null) {
			leftPlayer = new GraphicalPlayer(gameClient.getPlayerLeft());
		}

		if (rightPlayer == null) {
			rightPlayer = new GraphicalPlayer(gameClient.getPlayerRight());
		}

		leftPlayer.updateAnimation();
		rightPlayer.updateAnimation();

		ArrayList<Shot> shots = gameClient.getShots();
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

				// add new graphicslSowball if wasn't found in list
				if (!found) {
					graphicalSnowballs.add(new GraphicalSnowball(activeShot));

					switch (activeShot.getShotOrigin()) {
						case LEFT :
							gameClient.getPlayerLeft().setPlayerState(PlayerState.THROWING);
							break;
						case RIGHT :
							gameClient.getPlayerRight().setPlayerState(PlayerState.THROWING);
							break;
					}
				}
			}
		}

		for (GraphicalSnowball activeGraphicalSnowball : graphicalSnowballs) {
			activeGraphicalSnowball.updateAnimation();
		}

		updateObserver();
	}

	protected void setCountdownActive(boolean countdownActive) {
		this.countdownActive = countdownActive;
	}

	protected void setCountDownTime(int time) {
		this.countdownTime = time;
		updateObserver();
	}

	public boolean getCountdownActive() {
		return this.countdownActive;
	}

	public int getCountdownTime() {
		return this.countdownTime;
	}

	private void updateObserver() {
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

	public void startNewShotRequest(int angle, int strength) {
		if (!countdownActive) {
			PlayerPosition playerPos = gameClient.getPlayerPosition();
			// weight starts with 1, because 0 weight meant 0 gravity
			Snowball sb = new Snowball(1.0 + getBuildTime(playerPos));
			Shot activeShot = new Shot(angle, strength, sb);

			gameClient.startNewShotRequest(activeShot);
		}
	}

	public void startNewBuildRequest() {
		gameClient.startNewBuildRequest();
	}
}
