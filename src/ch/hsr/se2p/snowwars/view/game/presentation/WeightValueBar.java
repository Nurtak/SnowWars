package ch.hsr.se2p.snowwars.view.game.presentation;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import ch.hsr.se2p.snowwars.model.Player;
import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;
import ch.hsr.se2p.snowwars.view.FontLoader;

public class WeightValueBar {

	private PlayerPosition position;
	private final double weightValue;

	private final static int Y_DISTANCE_TO_PLAYER = 200;
	private final static int PANEL_MARGIN = 20;
	private final static int BORDER_RIGHT = 50;

	private final static String INFO_MESSAGE = "(affects damage and flight)";
	private final static String WEIGHT_STRING = "Weight: ";

	public WeightValueBar(PlayerPosition playerPosition, double weightValue) {
		this.position = playerPosition;
		this.weightValue = weightValue;
	}

	public void paint(Graphics2D g2d) {
		String weightString = WEIGHT_STRING + weightValue;

		int xPosition = 0;
		int yPosition = 0;
		switch (position) {
			case LEFT :
				xPosition = Player.PLAYER_LEFT_POSITION_X;
				yPosition = Player.PLAYER_LEFT_POSITION_Y + Y_DISTANCE_TO_PLAYER;
				break;
			case RIGHT :
				//-100 because needs distance from right border
				xPosition = Player.PLAYER_RIGHT_POSITION_X - BORDER_RIGHT;
				yPosition = Player.PLAYER_RIGHT_POSITION_Y + Y_DISTANCE_TO_PLAYER;
				break;
		}

		g2d.setColor(Color.BLACK);
		g2d.drawString(weightString, xPosition, yPosition);
		g2d.setFont(FontLoader.getInstance().getGameFont(13));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		yPosition += PANEL_MARGIN;
		g2d.setColor(Color.BLACK);
		g2d.drawString(INFO_MESSAGE, xPosition, yPosition);
		g2d.setFont(FontLoader.getInstance().getGameFont(25));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
}
