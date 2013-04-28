package ch.hsr.se2p.snowwars.model;

import ch.hsr.se2p.snowwars.model.remoteinterfaces.UserInterface;

public class User implements UserInterface {

    private String name;

    /* (non-Javadoc)
     * @see ch.hsr.se2p.snowwars.model.UserInterface#getName()
     */
    @Override
    public String getName() {
        return name;
    }

    public User(String name) {
        this.name = name;
    }

    @Override
    public boolean receiveInvitation() {
        // TODO Auto-generated method stub
        return false;
    }

}
