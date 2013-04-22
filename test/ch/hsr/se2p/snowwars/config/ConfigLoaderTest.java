package ch.hsr.se2p.snowwars.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigLoaderTest {

	@Test
	public void testReadConfigFile() {
		SnowWarsConfig snowWarsConfig = new ConfigLoader().readConfigFile();
        assertEquals(1099, snowWarsConfig.getRmiRegistryPort());
        assertEquals("snowwars", snowWarsConfig.getServerRMILookupName());
        assertEquals(1099, snowWarsConfig.getRmiRegistryPort());
	}

}
