package ch.hsr.se2p.snowwars.network;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.junit.BeforeClass;
import org.junit.Test;

import ch.hsr.se2p.snowwars.application.SnowWarsClient;
import ch.hsr.se2p.snowwars.application.SnowWarsServer;
import ch.hsr.se2p.snowwars.config.ConfigLoader;
import ch.hsr.se2p.snowwars.config.SnowWarsConfig;
import ch.hsr.se2p.snowwars.network.client.RMIClient;
import ch.hsr.se2p.snowwars.network.client.RMIClientInterface;
import ch.hsr.se2p.snowwars.network.client.RunRMIClient;
import ch.hsr.se2p.snowwars.network.server.RMIServerInterface;
import ch.hsr.se2p.snowwars.network.server.RunRMIServer;

public class RMITest {

    static SnowWarsConfig snowWarsConfig;

    @BeforeClass
    public static void setUpConfig() {
        ConfigLoader configLoader = new ConfigLoader();
        snowWarsConfig = configLoader.readConfigFile();
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
