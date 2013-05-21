package ch.hsr.se2p.snowwars.network;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.hsr.se2p.snowwars.config.Config;
import ch.hsr.se2p.snowwars.config.ConfigLoader;
import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.server.StartRMIServer;

public class RMIRegistryTest {

	private static Config config;

	@BeforeClass
	public static void setUpConfig() {
		config = ConfigLoader.getConfig();
	}

	@Test(expected = IOException.class)
	public void testServerUsesRightRegistryPort() throws SnowWarsRMIException, IOException {
		new StartRMIServer();
		ServerSocket serverSocket = new ServerSocket(config.getRmiRegistryPort());
		serverSocket.close();
	}
}
