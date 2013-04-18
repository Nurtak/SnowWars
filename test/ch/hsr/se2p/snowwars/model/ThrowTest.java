package ch.hsr.se2p.snowwars.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ThrowTest {

    @Test
    public void testGetDamage() {
        Throw swthrow = new Throw(0, 0, new Snowball(10));
        assertEquals(0, swthrow.getAngle());
        assertEquals(0, swthrow.getStength());
        assertEquals(10, swthrow.getWeight());
        assertEquals(10, swthrow.getDamage());
    }

}
