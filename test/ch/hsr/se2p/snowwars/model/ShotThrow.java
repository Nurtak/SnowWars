package ch.hsr.se2p.snowwars.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class ShotThrow {

    @Test
    public void testShot() {
        Throw shot = new Throw(0, 0, new Snowball(10));
        assertEquals(0, shot.getAngle());
    }

}
