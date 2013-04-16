package ch.hsr.se2p.snowwars.config;

public class SnowWarsConfig {

	private String hostname;
	private String rmiLookupName;
	private int rmiRegistryPort;
	private int rmiRemotePort;

	public String getHostname() {
		return hostname;
	}

	public void setServerHostname(String serverHostname) {
		this.hostname = serverHostname;
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
		return rmiLookupName;
	}

	public void setServerRMILookupName(String serverRMILookupName) {
		this.rmiLookupName = serverRMILookupName;
	}
}
