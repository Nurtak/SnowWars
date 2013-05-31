package ch.hsr.se2p.snowwars.model;

import static org.junit.Assert.assertEquals;

import java.awt.Rectangle;

import org.junit.Test;

import ch.hsr.se2p.snowwars.model.Player.PlayerPosition;

public class PlayerTest {

    public final static int PLAYER_LEFT_BOUNDS_X = 40;
    public final static int PLAYER_LEFT_BOUNDS_Y = 320;
    public final static int PLAYER_BOUNDS_HEIGHT = 60;
    public final static int PLAYER_BOUNDS_WIDTH = 150;

    @Test
    public void testSetHitPoints() {
        int hitPoints = 90;
        Player player = new Player(new User("Donald Duck"), PlayerPosition.LEFT);
        player.setHitPoints(hitPoints);
        assertEquals(hitPoints, player.getHitPoints());
    }

    @Test
    public void testGetPosition() {
        PlayerPosition playerPosition = PlayerPosition.LEFT;
        Player player = new Player(new User("Donald Duck"), playerPosition);
        assertEquals(playerPosition, player.getPosition());
    }

    @Test
    public void testGetBounds() {
        Player player = new Player(new User("Donald Duck"), PlayerPosition.LEFT);
        assertEquals(new Rectangle(PLAYER_LEFT_BOUNDS_X, PLAYER_LEFT_BOUNDS_Y, PLAYER_BOUNDS_HEIGHT, PLAYER_BOUNDS_WIDTH), player.getBounds());
    }

}
