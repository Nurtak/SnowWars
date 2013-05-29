package ch.hsr.se2p.snowwars.network;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.hsr.se2p.snowwars.server.network.StartRMIServer;
import ch.hsr.se2p.snowwars.util.config.Config;
import ch.hsr.se2p.snowwars.util.config.ConfigLoader;
import ch.hsr.se2p.snowwars.util.exception.SnowWarsRMIException;

public class RMIRegistryTest {

    private static Config config;

    @Before
    public void setUp() {   
        config = ConfigLoader.getConfig();
    }

    @Test(expected = IOException.class)
    public void testServerUsesRightRegistryPort() throws SnowWarsRMIException, IOException {
        ServerSocket serverSocket = new ServerSocket(config.getRmiRegistryPort());
        serverSocket.close();
    }
}
