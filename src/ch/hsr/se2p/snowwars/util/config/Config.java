package ch.hsr.se2p.snowwars.util.config;

public class Config {

    private String hostname;
    private int port;
    private String lookupname;

    protected Config(String hostname, int port, String lookupname) {
        this.hostname = hostname;
        this.lookupname = lookupname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public int getPort() {
        return port;
    }

    public String getLookupname() {
        return lookupname;
    }

}
