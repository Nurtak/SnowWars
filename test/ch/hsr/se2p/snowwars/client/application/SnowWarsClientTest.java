package ch.hsr.se2p.snowwars.client.application;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.core.Robot;
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
        
        Robot robot = BasicRobot.robotWithCurrentAwtHierarchy();
        new SnowWarsClient();
        frame = WindowFinder.findFrame("SnowWarsLobby").using(robot);
    }

    @After
    public void tearDown() throws Exception {
        frame.cleanUp();
    }

    @Test
    public void testLoginWithEmptyUsername() {
        frame.button("playButton").click();
        frame.textBox("txtUsername").setText("");
        frame.button("playButton").click();
        frame.optionPane().requireErrorMessage().requireMessage("Please enter an username!");     
    }
    
    @Test
    public void testLoginWithTooLongUsername() {
        frame.button("playButton").click();
        frame.textBox("txtUsername").setText("ABCDEFGHIJKLMNOPQRSZUVWXYZ");
        frame.button("playButton").click();
        frame.optionPane().requireErrorMessage().requireMessage("Username is too long! (>25 characters)");     
    }
    
    @Test
    public void testLoginAsValidUser() {
        frame.button("playButton").click();
        frame.textBox("txtUsername").setText("Mickey Mouse");
        frame.button("playButton").click();        
        frame.button("backButton").click();
    }
    
    /*
     * 'Donald Duck' has to be in the lobby!
     */
    @Test
    public void testLoginAsExistingUser() {
        frame.button("playButton").click();
        frame.textBox("txtUsername").setText("Donald Duck");
        frame.button("playButton").click();
        frame.optionPane().requireErrorMessage().requireMessage("Username is already taken!");     
    }
    
    /*
     * 'Donald Duck' has to be in the lobby!
     */
    @Test
    public void testInviteDonaldDuck() {
        frame.button("playButton").click();
        frame.textBox("txtUsername").setText("Mickey Mouse");
        frame.button("playButton").click();
        frame.list("lstUsers").selectItem("Donald Duck");
//        frame.button("inviteButton").click();
//        
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        
//        frame.cleanUp();
//        frame = WindowFinder.findFrame("SnowWarsGame").using(robot);
//        frame.optionPane().requireInformationMessage().requireMessage("Welcome to SnowWars. \nAre you ready?");         
        
        frame.button("backButton").click();
    }
}
