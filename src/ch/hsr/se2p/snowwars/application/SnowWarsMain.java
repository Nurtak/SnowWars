package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class SnowWarsMain {

    /**
     * @param args
     *            "-server" for starting the server
     */
    public static void main(String[] args) {
        installLogger();
        if (args.length != 0 && "-server".equalsIgnoreCase(args[0])) {
            new SnowWarsServer().startProgram();
        } else {
            new SnowWarsClient().startProgram();
        }
    }

    /**
     * Installs Log4j-Library
     */
    public static void installLogger() {
        try {
            Logger root = Logger.getRootLogger();
            PatternLayout layout = new PatternLayout("|%-32.32F|%-6p| %m%n");
            String logfilePath = "logs/logfile.log";
            RollingFileAppender newrfa = new RollingFileAppender(layout, logfilePath);
            root.addAppender(newrfa);
            root.addAppender(new ConsoleAppender(layout));
        } catch (Exception e) {
            System.out.println("ERROR: Could not install logger!" + e.getMessage());
        }
    }
}
