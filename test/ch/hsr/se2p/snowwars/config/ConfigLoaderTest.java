package ch.hsr.se2p.snowwars.config;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigLoaderTest {

	@Test
	public void testreadConfigFile() {
		SnowWarsConfig swc = new ConfigLoader().readConfigFile();
        assertEquals(1099, swc.getRmiRegistryPort());
        assertEquals("snowwars", swc.getServerRMILookupName());
	}

}
