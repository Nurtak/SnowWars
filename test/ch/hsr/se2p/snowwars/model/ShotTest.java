package ch.hsr.se2p.snowwars.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.se2p.snowwars.model.Shot;
import ch.hsr.se2p.snowwars.model.ShotObject;
import ch.hsr.se2p.snowwars.model.Snowball;

public class ShotTest {

    private Shot shot;
    private int angle;
    private int strength;
    private double weight;
    private ShotObject snowball;

    @Before
    public void setUp() {
        angle = 10;
        strength = 10;
        weight = 10.0;
        snowball = new Snowball(weight);
        shot = new Shot(angle, strength, snowball);
    }

    @Test
    public void testGetAngle() {
        assertEquals(angle, shot.getAngle());
    }

    @Test
    public void testGetStrength() {
        assertEquals(strength, shot.getStrength());
    }

    @Test
    public void testGetWeight() {
        assertEquals(weight, shot.getWeight(), 0.001);
    }

    @Test
    public void testGetDamage() {
        assertEquals(weight * Snowball.DAMAGE_MULTIPLIER, shot.getDamage(), 0.001);
    }

}
