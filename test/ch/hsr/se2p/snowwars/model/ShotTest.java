package ch.hsr.se2p.snowwars.model;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

public class ShotTest {

    @Test
    public void testShot() {
        Shot shot = new Shot(0, 0, 0);
        assertEquals(0, shot.getAngle());
    }

}
