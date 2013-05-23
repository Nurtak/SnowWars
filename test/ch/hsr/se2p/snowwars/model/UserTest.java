package ch.hsr.se2p.snowwars.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private String name;
    private User user;
    
    @Before
    public void setUp() throws Exception {
        name = "Donald Duck";
        user = new User(name);
    }

    @Test
    public void testHashCode() {
        User newUser = new User(name);
        assertTrue(user.hashCode() == newUser.hashCode());
        assertFalse(user.hashCode() == new User("Gustav Gans").hashCode());       
    }

    @Test
    public void testGetName() {
        assertEquals(name, user.getName());
    }

    @Test
    public void testToString() {
        assertEquals(user.getName(), user.toString());
    }

    @Test
    public void testEqualsObject() {
        User newUser = new User(name);
        assertEquals(newUser, user);
        assertNotSame(new User("Gustav Gans").hashCode(), user);  
    }

}
