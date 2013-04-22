package ch.hsr.se2p.snowwars.network;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.hsr.se2p.snowwars.application.SnowWarsServer;
import ch.hsr.se2p.snowwars.config.ConfigFactory;
import ch.hsr.se2p.snowwars.config.SnowWarsConfig;
import ch.hsr.se2p.snowwars.network.server.RunRMIServer;

public class RMITest {

    static SnowWarsConfig snowWarsConfig;

    @BeforeClass
    public static void setUpConfig() {
        snowWarsConfig = ConfigFactory.getSnowWarsConfig();
    }
    
    @Test
    public void testRegistryPortIsNotInUse() throws SnowWarsRMIException, IOException {
        ServerSocket serverSocket = new ServerSocket(snowWarsConfig.getRmiRegistryPort());
        serverSocket.close();
    }

    @Test(expected = IOException.class)
    public void testServerUsesRightRegistryPort() throws SnowWarsRMIException, IOException {
        new RunRMIServer(new SnowWarsServer());
        ServerSocket serverSocket = new ServerSocket(snowWarsConfig.getRmiRegistryPort());
        serverSocket.close();
    }
    

    // @Test
    // public void testRMICommunication() {
    //
    // try {
    // SnowWarsServer snowWarsServer = new SnowWarsServer();
    // RunRMIServer runRMIServer = new RunRMIServer(snowWarsServer);
    //
    // Registry serverRegistry =
    // LocateRegistry.getRegistry(snowWarsConfig.getHostname(),
    // snowWarsConfig.getRmiRegistryPort());
    //
    // SnowWarsClient snowWarsClient = new SnowWarsClient();
    // RunRMIClient runRMIClient = new RunRMIClient(snowWarsClient);
    //
    // RMIClientInterface client = new RMIClient(runRMIClient);
    // RMIClientInterface clientStub = (RMIClientInterface)
    // UnicastRemoteObject.exportObject(client, 0);
    //
    // RMIServerInterface server = (RMIServerInterface)
    // serverRegistry.lookup(snowWarsConfig.getServerRMILookupName());
    // // server.registerClient(clientStub);
    // // server.deregisterClient(clientStub);
    //
    // } catch (SnowWarsRMIException | RemoteException | NotBoundException e) {
    // e.printStackTrace();
    // fail();
    // }
    //
    // }

}
