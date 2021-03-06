package ch.hsr.se2p.snowwars.util.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

public class ConfigLoader {

    private final static Logger logger = Logger.getLogger(ConfigLoader.class.getPackage().getName());

    public static Config getConfig() {
        Config config;
        try {
            logger.info("Load XML config...");

            File configfile = new File("config.xml");
            if (!configfile.exists()) {
                // Copying existing default config.xml
                int readBytes;
                byte[] buffer = new byte[4096];
                try (InputStream stream = ConfigLoader.class.getResourceAsStream("/config.xml"); OutputStream resStreamOut = new FileOutputStream(configfile)) {
                    while ((readBytes = stream.read(buffer)) > 0) {
                        resStreamOut.write(buffer, 0, readBytes);
                    }
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }

            XMLConfiguration xmlConfig = new XMLConfiguration("config.xml");
            config = new Config(xmlConfig.getString("hostname"), xmlConfig.getInt("port"), xmlConfig.getString("lookupname"));
            logger.info("Hostname: " + config.getHostname());
            logger.info("Port is: " + config.getPort());
            logger.info("Lookupname is: " + config.getLookupname());
            logger.info("XML config: OK");

            return config;
        } catch (ConfigurationException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

}
