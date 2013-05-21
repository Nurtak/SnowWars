package ch.hsr.se2p.snowwars.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShotTest {

	@Test
    public void testGetDamage() {
        Shot shot = new Shot(10, 10, new Snowball(10.0));
        assertEquals(10, shot.getAngle());
        assertEquals(10, shot.getStrength());
        assertEquals(10.0, shot.getWeight(), 0.001);
        assertEquals(10.0, shot.getDamage(), 0.001);
    }
}