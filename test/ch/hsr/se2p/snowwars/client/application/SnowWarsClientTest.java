package ch.hsr.se2p.snowwars.client.application;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.hsr.se2p.snowwars.util.logging.Logging;

public class SnowWarsClientTest {

    private FrameFixture frame;

    @Before
    public void setUp() throws Exception {
        Logging.installLogger();
        BasicRobot robotf = (BasicRobot) BasicRobot.robotWithCurrentAwtHierarchy();
        new SnowWarsClient();
        frame = WindowFinder.findFrame("SnowWarsLobby").withTimeout(1000).using(robotf);
    }

    @After
    public void tearDown() throws Exception {
        frame.cleanUp();
    }

    @Test
    public void test() {
        frame.button("playButton").click();
        frame.textBox("txtUsername").setText("Donald Duck");
        frame.button("playButton").click();
        frame.list("lstUsers").selectItem("Gustav Gans");

        // Invite
        //frame.button("inviteButton").click();
        
        frame.button("backButton").click();
        frame.button("backButton").click();
        
        // Can not exit via GUI!
        // frame.button("exitButton").click();
        
        
        
    }
}
