package ch.hsr.se2p.snowwars.network.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import ch.hsr.se2p.snowwars.application.SnowWarsClient;
import ch.hsr.se2p.snowwars.config.SnowWarsConfig;
import ch.hsr.se2p.snowwars.network.SnowWarsRMIException;
import ch.hsr.se2p.snowwars.network.server.RMIServerInterface;

public class RunRMIClient {

	private final SnowWarsConfig snowWarsConfig;

	public RunRMIClient(SnowWarsClient snowWarsClient) {
		this.snowWarsConfig = snowWarsClient.getClientConfig();
	}

	public void connect() throws SnowWarsRMIException {
		try {
			Registry serverRegistry = LocateRegistry.getRegistry(snowWarsConfig.getServerHostname(), snowWarsConfig.getRmiRegistryPort());

			RMIClientInterface client = new RMIClient();
			RMIClientInterface clientStub = (RMIClientInterface)UnicastRemoteObject.exportObject(client, snowWarsConfig.getRmiRegistryPort());

			// Remote Objekt (Stub)
			RMIServerInterface server = (RMIServerInterface) serverRegistry.lookup(snowWarsConfig.getServerRMILookupName());

			// joining game on server
			System.out.println("Invoking result: " + server.joinGame(clientStub));
		} catch (Exception e1) {
			throw new SnowWarsRMIException(e1.getMessage());
		}
	}
}