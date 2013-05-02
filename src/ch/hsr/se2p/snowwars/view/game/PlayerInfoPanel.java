package ch.hsr.se2p.snowwars.view.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import ch.hsr.se2p.snowwars.model.Player;

public class PlayerInfoPanel {

    private final static int PANEL_WIDTH = 300;
    private final static int PANEL_MARGIN = 10;
    private final static int POSITION_Y = 20;
    private final static int HEALTH_BAR_HEIGHT = 20;

    private final static Font USERNAME_FONT = new Font("Arial", Font.PLAIN, 16);
    private final static Color FONT_COLOR = Color.BLACK;
    private final static Color GREEN = new Color(23, 109, 12);
    private final static Color RED = new Color(198, 17, 17);
    private final static Color ORANGE = new Color(246, 94, 2);

    private final Player player;
    private final PlayerInfoPanelPosition position;

    private final int GAME_WIDTH;

    public static enum PlayerInfoPanelPosition {
        LEFT, RIGHT
    }

    public PlayerInfoPanel(Player player, PlayerInfoPanelPosition pos, int gameWidth) {
        this.GAME_WIDTH = gameWidth;
        this.position = pos;
        this.player = player;
    }

    public void paint(Graphics2D g2d) {
        paintUsername(g2d);
        paintHitPointBar(g2d);
    }

    private void paintUsername(Graphics2D g2d) {
        int xPosition = 0;
        switch (position)
        {
        case LEFT:
            xPosition = PANEL_MARGIN;
            break;
        case RIGHT:
            int stringLength = (int) g2d.getFontMetrics().getStringBounds(player.getUser().getName(), g2d).getWidth();
            xPosition = GAME_WIDTH - PANEL_MARGIN - stringLength;
        }

        g2d.setFont(USERNAME_FONT);
        g2d.setColor(FONT_COLOR);
        g2d.drawString(player.getUser().getName(), xPosition, POSITION_Y);
    }

    private void paintHitPointBar(Graphics2D g2d) {
        int remainingHitPoints = player.getHitPoints();
        int lostHitPoints = Player.MAX_HIT_POINTS - remainingHitPoints;
        int hitPointPixelRatio = PANEL_WIDTH / Player.MAX_HIT_POINTS;

        // calc remaining-hp-bar specs
        int xPositionRemainingBar = 0;
        int yPositionRemainingBar = POSITION_Y + PANEL_MARGIN;
        int remainingBarWidth = remainingHitPoints * hitPointPixelRatio;

        // calc lost-hp-bar-specs
        int xPositionLostBar = 0;
        int yPositionLostBar = POSITION_Y + PANEL_MARGIN;
        int lostBarWidth = lostHitPoints * hitPointPixelRatio;

        // calc specs of left or right bar
        switch (position)
        {
        case LEFT:
            xPositionRemainingBar = PANEL_MARGIN;
            xPositionLostBar = PANEL_MARGIN + remainingBarWidth;
            break;
        case RIGHT:
            xPositionLostBar = GAME_WIDTH - PANEL_MARGIN - PANEL_WIDTH;
            xPositionRemainingBar = xPositionLostBar + lostBarWidth;
            break;
        }

        // set color of bar
        if (remainingHitPoints > 66) {
            g2d.setColor(GREEN);
        } else if (remainingHitPoints > 33) {
            g2d.setColor(ORANGE);
        } else {
            g2d.setColor(RED);
        }

        g2d.fillRect(xPositionRemainingBar, yPositionRemainingBar, remainingBarWidth, HEALTH_BAR_HEIGHT);

        if (lostBarWidth > 0) {
            g2d.drawRect(xPositionLostBar, yPositionLostBar, lostBarWidth, HEALTH_BAR_HEIGHT);
        }
    }
}
