package ch.hsr.se2p.snowwars.client.viewutils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import ch.hsr.se2p.snowwars.client.viewutils.BufferedImageLoader;

public class BufferedImageLoaderTest {

    private BufferedImageLoader bufferedImageLoader;
    
    @Before
    public void setUp() throws Exception {
        bufferedImageLoader = BufferedImageLoader.getInstance();
    }

    @Test
    public void testGetInstance() {
        assertEquals(bufferedImageLoader, BufferedImageLoader.getInstance());
    }

    @Test
    public void testGetLogoImage() {
        try {
            assertTrue(bufferedImageLoader.getLogoImage().getWidth() == 336);
            assertTrue(bufferedImageLoader.getLogoImage().getHeight() == 191);
        } catch (IOException e) {
            fail();
        }
    }

}
