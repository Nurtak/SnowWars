package ch.hsr.se2p.snowwars.view.game;

import java.awt.Graphics2D;

import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.remoteinterfaces.PlayerInterface;

public class PlayerInfoPanel {

	private final static int PANEL_WIDTH = 200;
	private final static int PANEL_MARGIN = 10;
	private final static int POSITION_Y = 20;
	private final static int HEALTH_BAR_HEIGHT = 20;

	private final PlayerInterface player;
	private final PlayerInfoPanelPosition position;

	private final int GAME_WIDTH;

	public static enum PlayerInfoPanelPosition {
		LEFT, RIGHT
	}

	public PlayerInfoPanel(PlayerInterface player, PlayerInfoPanelPosition pos, int gameWidth) {
		this.GAME_WIDTH = gameWidth;
		this.position = pos;
		this.player = player;
	}

	public void paint(Graphics2D g2d) {
		paintUsername(g2d);
		paintHealthBar(g2d);
	}
	
	private void paintUsername(Graphics2D g2d){
		int xPosition = 0;
		switch(position){
		case LEFT:
			xPosition = PANEL_MARGIN;
			break;
		case RIGHT:
			xPosition = GAME_WIDTH - PANEL_MARGIN - PANEL_WIDTH;
		}

		g2d.drawString(player.getUser().getName(), xPosition, POSITION_Y);
	}
	
	private void paintHealthBar(Graphics2D g2d){
		int remainingHealthPoints = player.getHitPoints();
		int lostHealtPoints = Player.MAX_HEALTH_POINTS - player.getHitPoints();
		int healthPointPixelRatio = PANEL_WIDTH / Player.MAX_HEALTH_POINTS;
		
		int xPositionRemainingBar = 0;
		int yPositionRemainingBar = POSITION_Y + PANEL_MARGIN;
		int remainingBarWidth = remainingHealthPoints * healthPointPixelRatio;
		
		int xPositionLostBar = 0;
		int yPositionLostBar = POSITION_Y + PANEL_MARGIN;
		int lostBarWidth = lostHealtPoints * healthPointPixelRatio;
			
		switch(position){
		case LEFT:
			xPositionRemainingBar = PANEL_MARGIN;
			xPositionLostBar = PANEL_MARGIN + remainingBarWidth;
			break;
		case RIGHT:
			xPositionRemainingBar = GAME_WIDTH - PANEL_MARGIN - PANEL_WIDTH;
			xPositionLostBar = xPositionRemainingBar + remainingBarWidth;
			break;
		}
		
		g2d.fillRect(xPositionRemainingBar, yPositionRemainingBar, remainingBarWidth, HEALTH_BAR_HEIGHT);
		
		if(lostBarWidth > 0){
			g2d.drawRect(xPositionLostBar, yPositionLostBar, lostBarWidth, HEALTH_BAR_HEIGHT);
		}
	}
}
