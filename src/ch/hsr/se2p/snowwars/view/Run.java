package ch.hsr.se2p.snowwars.view;
import javax.swing.JFrame;


public class Run {

    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        Renderer renderer = new Renderer();
        renderer.setVisible(true);
        frame.add(renderer);
        frame.setVisible(true);
    }

}
