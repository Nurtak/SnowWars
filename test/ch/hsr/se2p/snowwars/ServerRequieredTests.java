package ch.hsr.se2p.snowwars;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ch.hsr.se2p.snowwars.client.application.SnowWarsClient;
import ch.hsr.se2p.snowwars.client.application.SnowWarsClientTest;
import ch.hsr.se2p.snowwars.server.application.SnowWarsServer;
import ch.hsr.se2p.snowwars.server.network.RMIRegistryTest;

@RunWith(Suite.class)
@SuiteClasses({ SnowWarsClientTest.class, RMIRegistryTest.class })
public class ServerRequieredTests {

    @BeforeClass
    public static void setUp() {
        new SnowWarsServer();

        Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();
        new SnowWarsClient();
        FrameFixture frame = WindowFinder.findFrame("SnowWarsLobby").using(robot);
        frame.button("playButton").click();
        frame.textBox("txtUsername").setText("Donald Duck");
        frame.button("playButton").click();
        frame.cleanUp();
    }

}
