package ch.hsr.se2p.snowwars.network;

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

    @Test(expected = IOException.class)
    public void testServerport() throws SnowWarsRMIException, IOException {
        new RunRMIServer(new SnowWarsServer());
        ServerSocket serverSocket = new ServerSocket(snowWarsConfig.getRmiRegistryPort());
        // Port should be already in use
        serverSocket.close();
    }

//    @Test
//    public void testRMICommunication() {
//
//        try {
//            SnowWarsServer snowWarsServer = new SnowWarsServer();
//            RunRMIServer runRMIServer = new RunRMIServer(snowWarsServer);
//
//            Registry serverRegistry = LocateRegistry.getRegistry(snowWarsConfig.getHostname(), snowWarsConfig.getRmiRegistryPort());
//
//            SnowWarsClient snowWarsClient = new SnowWarsClient();
//            RunRMIClient runRMIClient = new RunRMIClient(snowWarsClient);
//
//            RMIClientInterface client = new RMIClient(runRMIClient);
//            RMIClientInterface clientStub = (RMIClientInterface) UnicastRemoteObject.exportObject(client, 0);
//
//            RMIServerInterface server = (RMIServerInterface) serverRegistry.lookup(snowWarsConfig.getServerRMILookupName());
//            // server.registerClient(clientStub);
//            // server.deregisterClient(clientStub);
//
//        } catch (SnowWarsRMIException | RemoteException | NotBoundException e) {
//            e.printStackTrace();
//            fail();
//        }
//
//    }

}