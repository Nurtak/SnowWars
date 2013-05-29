package ch.hsr.se2p.snowwars.server.network;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.network.serversession.RMIServerInterface;
import ch.hsr.se2p.snowwars.server.applicationcontrolling.Lobby;
import ch.hsr.se2p.snowwars.util.config.Config;
import ch.hsr.se2p.snowwars.util.config.ConfigLoader;
import ch.hsr.se2p.snowwars.util.exception.SnowWarsRMIException;

public class StartRMIServer {

    private final static Logger logger = Logger.getLogger(StartRMIServer.class.getPackage().getName());
    private final Config config;
    private RMIServer rmiServer;

    public StartRMIServer() throws SnowWarsRMIException {
        config = ConfigLoader.getConfig();
        setRMIPropertyAndSecurity();
        setRegistryAndStub();
    }

    private void setRMIPropertyAndSecurity() {
        try {
            logger.info("Initializing SnowWars RMI Server...");
            System.setProperty("java.security.policy", ClassLoader.getSystemResource("rmi.policy").toExternalForm());
            System.setProperty("java.rmi.server.hostname", InetAddress.getLocalHost().getHostAddress());
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void setRegistryAndStub() {
        try {
            Lobby lobby = new Lobby();
            rmiServer = new RMIServer(lobby);
            RMIServerInterface stub = (RMIServerInterface) UnicastRemoteObject.exportObject(rmiServer, 0);

            // Registry
            logger.info("Creating Registry...");
            LocateRegistry.createRegistry(config.getRmiRegistryPort());
            Registry registry = LocateRegistry.getRegistry();

            // Bind Stub
            registry.rebind(config.getServerRMILookupName(), stub);
            logger.info("SnowWars Server is working...");
        } catch (RemoteException e) {
            logger.error("Could not start a RMI proxy!");
            logger.error(e.getMessage(), e);
            System.exit(0);
        }
    }

    public void shutdown() {
        if (rmiServer != null) {
            try {
                Naming.unbind(config.getServerRMILookupName());
                UnicastRemoteObject.unexportObject(rmiServer, true);
            } catch (RemoteException | MalformedURLException | NotBoundException e) {
                logger.info(e);
            }
        } else {
            logger.info("nothing to shutdown");
        }
    }
}
