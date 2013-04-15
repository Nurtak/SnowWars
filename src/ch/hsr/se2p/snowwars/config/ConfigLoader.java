package ch.hsr.se2p.snowwars.config;

public class ConfigLoader {
	public ConfigLoader(){}
	
	public ClientConfig loadClientConfig(){
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.setServerHostname("asdf");
		return clientConfig;
	}
	
	public ServerConfig loadServerConfig(){
		return new ServerConfig();
	}
}
