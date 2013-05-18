package ch.hsr.se2p.snowwars.network.client;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Observable;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import ch.hsr.se2p.snowwars.config.Config;
import ch.hsr.se2p.snowwars.config.ConfigLoader;
import ch.hsr.se2p.snowwars.exceptions.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.server.RMIServerInterface;
import ch.hsr.se2p.snowwars.network.session.server.ConnectedServerSessionInterface;

public class StartRMIClient extends Observable {

    private final static Logger logger = Logger.getLogger(StartRMIClient.class.getPackage().getName());
    private final Config config;
    private ConnectedServerSessionInterface connectedServerSessionInterface;
    private RMIServerInterface rmiServerInterface;

    public StartRMIClient() {
        config = ConfigLoader.getConfig();
        setRMIPropertyAndSecurity();
        try {
            setServer();
            setConnectedServerSessionInterface();
        } catch (SnowWarsRMIException e) {
            JOptionPane.showMessageDialog(null, "No connection to server! (server was " + config.getHostname() + ")", ":(", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private void setRMIPropertyAndSecurity() {
        try {
            System.setProperty("java.security.policy", ClassLoader.getSystemResource("rmi.policy").toExternalForm());
            System.setProperty("java.rmi.system.hostname", InetAddress.getLocalHost().getHostAddress());
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new RMISecurityManager());
            }
        } catch (UnknownHostException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void setServer() throws SnowWarsRMIException {
        try {
            Registry serverRegistry = LocateRegistry.getRegistry(config.getHostname(), config.getRmiRegistryPort());
            rmiServerInterface = (RMIServerInterface) serverRegistry.lookup(config.getServerRMILookupName());
        } catch (RemoteException | NotBoundException e) {
            logger.error(e.getMessage(), e);
            throw new SnowWarsRMIException(e.getMessage());
        }
    }

    private void setConnectedServerSessionInterface() throws SnowWarsRMIException {
        try {
            connectedServerSessionInterface = rmiServerInterface.connect();
            logger.info("Successfully Connected to " + config.getHostname());
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
            throw new SnowWarsRMIException(e.getMessage());
        }
    }

    public ConnectedServerSessionInterface getConnectedServerSessionInterface() {
        return connectedServerSessionInterface;
    }

}
