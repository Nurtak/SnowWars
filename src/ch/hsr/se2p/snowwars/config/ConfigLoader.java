package ch.hsr.se2p.snowwars.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

public class ConfigLoader {

	private final static Logger logger = Logger.getLogger(ConfigLoader.class.getPackage().getName());

	public static Config getConfig() {
		Config snowWarsConfig;
		try {
			logger.info("Load XML config...");
			XMLConfiguration xmlConfig = new XMLConfiguration("config/config.xml");

			snowWarsConfig = new Config(xmlConfig.getString("hostname"), xmlConfig.getString("lookupname"), xmlConfig.getInt("port.rmi_registry"), xmlConfig.getInt("port.rmi_remote"));
			logger.info("XML config: OK");
			return snowWarsConfig;

		} catch (ConfigurationException cex) {
			logger.error(cex.getMessage());
			return null;
		}
	}

}
