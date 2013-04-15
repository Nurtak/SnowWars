package ch.hsr.se2p.snowwars.network.client;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import ch.hsr.se2p.snowwars.application.RunSnowWarsClient;
import ch.hsr.se2p.snowwars.config.ClientConfig;
import ch.hsr.se2p.snowwars.network.server.RMIServerInterface;

public class RMIClient {

	private final ClientConfig clientConfig;

	public RMIClient(RunSnowWarsClient snowWarsClient) {
		this.clientConfig = snowWarsClient.getClientConfig();
	}

	public void connect() throws SnowWarsRMIException {
		try {
			Registry serverRegistry = LocateRegistry.getRegistry(clientConfig.getServerHostname(), clientConfig.getRmiRegistryPort());

			// RMIClientInterface client = new Client();
			// RMIClientInterface clientStub = (RMIClientInterface)
			// UnicastRemoteObject.exportObject(client, RMI_REGISTRY_PORT);

			// Remote Objekt (Stub)
			RMIServerInterface server = (RMIServerInterface) serverRegistry.lookup(clientConfig.getServerRMILookupName());

			// joining game on server
			System.out.println("Invoking result: " + server.joinGame());
		} catch (Exception e1) {
			throw new SnowWarsRMIException(e1.getMessage());
		}
	}
}

class SnowWarsRMIException extends Exception {
	private static final long serialVersionUID = 1172984083592488431L;

	public SnowWarsRMIException(String message) {
		super(message);
	}
}
