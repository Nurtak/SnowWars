package ch.hsr.se2p.snowwars.application;

import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SnowWarsClientTest {

    private FrameFixture frame;
    
    @Before
    public void setUp() throws Exception {
        frame = new FrameFixture(new SnowWarsClient().getClientViewMain());
        frame.show();
    }
    
    @After
    public void tearDown() throws Exception {
        frame.close();
    }

    @Test
    public void test() {
        frame.button("playButton").click();
        frame.textBox("txtUsername").setText("Donald Duck");
        frame.button("playButton").click();
    }

    
    
}
