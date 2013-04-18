package ch.hsr.se2p.snowwars.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ShotTest {

    @Test
    public void testShot() {
        Throw shot = new Throw(0, 0, 0);
        assertEquals(0, shot.getAngle());
    }

}
