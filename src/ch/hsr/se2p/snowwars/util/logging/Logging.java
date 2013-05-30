package ch.hsr.se2p.snowwars.util.logging;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class Logging {

    public static void installLogger() {
        try {
            Logger root = Logger.getRootLogger();
            PatternLayout layout = new PatternLayout("|%-32.32F|%-6p| %m%n");
            // String logfilePath = "logs/logfile.log";
            // RollingFileAppender newrfa = new RollingFileAppender(layout,
            // logfilePath);
            // root.addAppender(newrfa);
            root.addAppender(new ConsoleAppender(layout));
        } catch (Exception e) {
            System.err.println("ERROR: Could not install logger!" + e.getMessage());
        }
    }

}
