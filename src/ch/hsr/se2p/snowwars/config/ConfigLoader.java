package ch.hsr.se2p.snowwars.config;

public class ConfigLoader {
	public ConfigLoader(){}
	
	public ClientConfig loadClientConfig(){
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setServerHostname("sinv-56080.edu.hsr.ch");
		clientConfig.setServerRMILookupName("SnowWarsServer");
		clientConfig.setRmiRegistryPort(1099);
		clientConfig.setRmiRemotePort(1098);
		return clientConfig;
	}
	
	public ServerConfig loadServerConfig(){
		return new ServerConfig();
	}
}
