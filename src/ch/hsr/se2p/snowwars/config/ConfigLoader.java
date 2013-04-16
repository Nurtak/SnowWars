package ch.hsr.se2p.snowwars.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

public class ConfigLoader {

    private final static Logger logger = Logger.getLogger(ConfigLoader.class.getPackage().getName());
    
    public SnowWarsConfig readConfigFile() {
        SnowWarsConfig snowWarsConfig = new SnowWarsConfig();
        try {
            logger.info("Load XML config...");
            XMLConfiguration xmlConfig = new XMLConfiguration("config/config.xml");
            
            snowWarsConfig.setRmiRegistryPort(xmlConfig.getInt("port.rmi_registry"));
            snowWarsConfig.setRmiRemotePort(xmlConfig.getInt("port.rmi_remote"));
            snowWarsConfig.setServerHostname(xmlConfig.getString("hostname"));
            snowWarsConfig.setServerRMILookupName(xmlConfig.getString("lookupname"));
            
        } catch (ConfigurationException cex) {
            // something went wrong, e.g. the file was not found
        }
        logger.info("XML config: OK");
        return snowWarsConfig;
    }

}
