package ch.hsr.se2p.snowwars.view.game;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Renderer extends JPanel implements ActionListener {
    
    private static final long serialVersionUID = -2451769054525194135L;
    
    private Timer timer = new Timer(1, this);
    private int counter = 0;

    public Renderer() {
        timer.start();
        setIgnoreRepaint(true);
        
    }

    public void paint(Graphics g) {
        super.paint(g);
        
        g.setColor(Color.RED);
        g.fillRect(counter++, 300, 50, 50);
        g.setColor(Color.BLUE);
        g.fillRect(300, 300, 50, 50);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();

    }

}
