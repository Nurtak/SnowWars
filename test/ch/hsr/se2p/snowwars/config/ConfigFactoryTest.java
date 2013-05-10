package ch.hsr.se2p.snowwars.config;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class ConfigFactoryTest {

    static Config snowWarsConfig;

    @BeforeClass
    public static void setUpConfig() {
        snowWarsConfig = ConfigLoader.getConfig();
    }
    
	@Test
	public void testGetSnowWarsConfig() {
        assertEquals(1099, snowWarsConfig.getRmiRegistryPort());
        assertEquals("snowwars", snowWarsConfig.getServerRMILookupName());
	}

}
