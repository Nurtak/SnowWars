package ch.hsr.se2p.snowwars.config;

import org.apache.log4j.Logger;

public class ConfigLoader {

	private final static Logger logger = Logger.getLogger(ConfigLoader.class.getPackage().getName());

	public static Config getConfig() {
		Config snowWarsConfig;
		// try {
		logger.info("Load XML config...");

		// File configfile = new File("config.xml");
		// if (!configfile.exists()) {
		// int readBytes;
		// byte[] buffer = new byte[4096];
		// try (InputStream stream =
		// ConfigLoader.class.getResourceAsStream("/config.xml"); OutputStream
		// resStreamOut = new FileOutputStream(configfile)) {
		// while ((readBytes = stream.read(buffer)) > 0) {
		// resStreamOut.write(buffer, 0, readBytes);
		// }
		// } catch (IOException e1) {
		// e1.printStackTrace();
		// }
		// }

		// XMLConfiguration xmlConfig = new XMLConfiguration("config.xml");

		snowWarsConfig = new Config("152.96.236.2", "snowwars", 1099, 1098);

		// snowWarsConfig = new Config(xmlConfig.getString("hostname"),
		// xmlConfig.getString("lookupname"),
		// xmlConfig.getInt("port.rmi_registry"),
		// xmlConfig.getInt("port.rmi_remote"));
		logger.info("XML config: OK");
		return snowWarsConfig;

		// } catch (ConfigurationException cex) {
		// logger.error(cex.getMessage());
		// return null;
		// }
	}

}
