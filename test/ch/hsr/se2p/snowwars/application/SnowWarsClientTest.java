package ch.hsr.se2p.snowwars.application;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.hsr.se2p.snowwars.client.application.SnowWarsClient;

public class SnowWarsClientTest {

    private FrameFixture frameFixture;

    @Before
    public void setUp() throws Exception {
        Logger root = Logger.getRootLogger();
        PatternLayout layout = new PatternLayout("|%-32.32F|%-6p| %m%n");
        root.addAppender(new ConsoleAppender(layout));

        frameFixture = new FrameFixture(new SnowWarsClient().getClientViewMain());
        frameFixture.show();
    }

    @After
    public void tearDown() throws Exception {
        frameFixture.cleanUp();
    }

    @Test
    public void test() {
        frameFixture.button("playButton").click();
        frameFixture.textBox("txtUsername").setText("Donald Duck");
        frameFixture.button("playButton").click();
    }

}
