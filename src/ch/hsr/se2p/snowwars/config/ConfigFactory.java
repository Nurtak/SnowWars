package ch.hsr.se2p.snowwars.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

public class ConfigFactory {

    private final static Logger logger = Logger.getLogger(ConfigFactory.class.getPackage().getName());

    public static SnowWarsConfig getSnowWarsConfig() {
        SnowWarsConfig snowWarsConfig;
        try {
            logger.info("Load XML config...");
            XMLConfiguration xmlConfig = new XMLConfiguration("config/config.xml");

            snowWarsConfig = new SnowWarsConfig(xmlConfig.getString("hostname"), 
                    xmlConfig.getString("lookupname"), 
                    xmlConfig.getInt("port.rmi_registry"),
                    xmlConfig.getInt("port.rmi_remote"));
            logger.info("XML config: OK");
            return snowWarsConfig;

        } catch (ConfigurationException cex) {
            cex.printStackTrace();
            return null;
        }
    }

}
