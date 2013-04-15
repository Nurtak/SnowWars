package ch.hsr.se2p.snowwars.config;

public class SnowWarsConfig {

	private String serverHostname;
	private String serverRMILookupName;
	private int rmiRegistryPort;
	private int rmiRemotePort;

	public String getServerHostname() {
		return serverHostname;
	}

	public void setServerHostname(String serverHostname) {
		this.serverHostname = serverHostname;
	}

	public int getRmiRegistryPort() {
		return rmiRegistryPort;
	}

	public void setRmiRegistryPort(int rmiRegistryPort) {
		this.rmiRegistryPort = rmiRegistryPort;
	}

	public int getRmiRemotePort() {
		return rmiRemotePort;
	}

	public void setRmiRemotePort(int rmiRemotePort) {
		this.rmiRemotePort = rmiRemotePort;
	}

	public String getServerRMILookupName() {
		return serverRMILookupName;
	}

	public void setServerRMILookupName(String serverRMILookupName) {
		this.serverRMILookupName = serverRMILookupName;
	}
}
