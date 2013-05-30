package ch.hsr.se2p.snowwars.util.config;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ConfigTest {

    private static Config config;

    @Before
    public void setUp() {
        config = ConfigLoader.getConfig();
    }

    @Test
    public void testGetRmiRegistryPort() {
        assertEquals(1099, config.getPort());
    }

    @Test
    public void testGetServerRMILookupName() {
        assertEquals("snowwars", config.getLookupname());
    }

}
