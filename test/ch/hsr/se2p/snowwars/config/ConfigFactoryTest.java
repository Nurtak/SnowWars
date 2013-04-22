package ch.hsr.se2p.snowwars.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigFactoryTest {

	@Test
	public void testGetSnowWarsConfig() {
		SnowWarsConfig snowWarsConfig = ConfigFactory.getSnowWarsConfig();
        assertEquals(1099, snowWarsConfig.getRmiRegistryPort());
        assertEquals("snowwars", snowWarsConfig.getServerRMILookupName());
	}

}
