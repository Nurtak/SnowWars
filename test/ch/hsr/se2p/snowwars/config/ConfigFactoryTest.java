package ch.hsr.se2p.snowwars.config;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigFactoryTest {

    static SnowWarsConfig snowWarsConfig;

    @BeforeClass
    public static void setUpConfig() {
        snowWarsConfig = ConfigFactory.getSnowWarsConfig();
    }
    
	@Test
	public void testGetSnowWarsConfig() {
        assertEquals(1099, snowWarsConfig.getRmiRegistryPort());
        assertEquals("snowwars", snowWarsConfig.getServerRMILookupName());
	}

}
