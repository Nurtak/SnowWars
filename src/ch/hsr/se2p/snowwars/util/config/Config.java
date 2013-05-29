package ch.hsr.se2p.snowwars.util.config;

public class Config {

    private String hostname;
    private String rmiLookupName;
    private int rmiRegistryPort;
    private int rmiRemotePort;

    protected Config(String hostname, String rmiLookupName, int rmiRegistryPort, int rmiRemotePort) {
        this.hostname = hostname;
        this.rmiLookupName = rmiLookupName;
        this.rmiRegistryPort = rmiRegistryPort;
        this.rmiRemotePort = rmiRemotePort;
    }

    public String getHostname() {
        return hostname;
    }

    public int getRmiRegistryPort() {
        return rmiRegistryPort;
    }

    public int getRmiRemotePort() {
        return rmiRemotePort;
    }

    public String getServerRMILookupName() {
        return rmiLookupName;
    }

}
