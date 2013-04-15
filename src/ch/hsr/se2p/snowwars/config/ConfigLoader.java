package ch.hsr.se2p.snowwars.config;

public class ConfigLoader {
	public ConfigLoader(){}
	
	public SnowWarsConfig readConfigFile(){
		SnowWarsConfig snowWarsConfig = new SnowWarsConfig();
		
		//snowWarsConfig.setServerHostname("sinv-56080.edu.hsr.ch");
		snowWarsConfig.setServerHostname("LOCALHOST");
		
		snowWarsConfig.setServerRMILookupName("SnowWarsServer");
		snowWarsConfig.setRmiRegistryPort(1099);
		snowWarsConfig.setRmiRemotePort(1098);
		return snowWarsConfig;
	}
}
