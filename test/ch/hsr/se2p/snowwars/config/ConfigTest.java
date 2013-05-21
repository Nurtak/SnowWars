package ch.hsr.se2p.snowwars.config;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigTest {

    private static Config config;

    @BeforeClass
    public static void setUpConfig() {
        config = ConfigLoader.getConfig();
    }
    
	@Test
	public void testgetConfig() {
        assertEquals(1099, config.getRmiRegistryPort());
        assertEquals("snowwars", config.getServerRMILookupName());
	}

}
