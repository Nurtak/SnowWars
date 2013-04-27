package ch.hsr.se2p.snowwars.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ShotTest {

    @Test
    public void testGetDamage() {
        Shot shot = new Shot(0, 0, new Snowball(10));
        assertEquals(0, shot.getAngle());
        assertEquals(0, shot.getStrength());
        assertEquals(10, shot.getWeight());
        assertEquals(10, shot.getDamage());
    }

}
