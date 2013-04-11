package ch.hsr.se2p.snowwars.application;

public class Run {

    /**
     * @param args
     *            "-server" for starting the server
     */
    public static void main(String[] args) {
        if (args.length != 0 && "-server".equalsIgnoreCase(args[0])) {
            new RunSnowWarsServer();
        } else {
            new RunSnowWarsClient();
        }

    }
}
