package ch.hsr.se2p.snowwars.client.lobby.view;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import ch.hsr.se2p.snowwars.client.lobby.applicationcontrolling.ClientLobbyController;
import ch.hsr.se2p.snowwars.client.lobby.viewmodel.ClientLobbyModel;

public class PanelMain extends JPanel implements Observer, PanelInterface {

    private static final long serialVersionUID = -4628393851839832247L;
    private final ClientViewMainInterface cvm;
    private ClientLobbyModel clientLobbyModel;
    private ClientLobbyController clientLobbyController;

    public PanelMain(ClientViewMainInterface cvm, ClientLobbyModel clientLobbyModel, ClientLobbyController clientLobbyController) {
        setBackground(new Color(32, 145, 210));
        this.cvm = cvm;
        this.clientLobbyModel = clientLobbyModel;
        this.clientLobbyController = clientLobbyController;
        clientLobbyModel.addObserver(this);
        createMainPanel();
    }

    private void createMainPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 33, 0, 98, 0 };
        gridBagLayout.rowHeights = new int[] { 46, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, 0.0, 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JButton playButton = new JButton();
        playButton.setName("playButton");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playPressed();
            }
        });
        GridBagConstraints gbc_playButton = new GridBagConstraints();
        playButton.setIcon(new ImageIcon(PanelLobby.class.getResource("/img/playButton.gif")));
        playButton.setBorder(null);
        playButton.setBackground(new Color(25, 145, 210));
        gbc_playButton.insets = new Insets(0, 60, 5, 5);
        gbc_playButton.gridx = 1;
        gbc_playButton.gridy = 1;
        add(playButton, gbc_playButton);

        JButton exitButton = new JButton();
        exitButton.setName("exitButton");
        exitButton.setIcon(new ImageIcon(PanelLobby.class.getResource("/img/exitButton.gif")));
        exitButton.setBorder(null);
        exitButton.setBackground(new Color(25, 145, 210));
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitPressed();
            }
        });
        GridBagConstraints gbc_exitButton = new GridBagConstraints();
        gbc_exitButton.insets = new Insets(0, 60, 5, 5);
        gbc_exitButton.gridx = 1;
        gbc_exitButton.gridy = 2;
        add(exitButton, gbc_exitButton);

    }

    private void playPressed() {
        cvm.addPanel(new PanelUser(cvm, clientLobbyModel, clientLobbyController), "userPanel");
        cvm.nextCard();
    }

    private void exitPressed() {
        cvm.exit();
    }

    @Override
    public void update(Observable o, Object arg) {
    }

    @Override
    public void enterPressed() {
        playPressed();
    }

    @Override
    public void escPressed() {
        escPressed();
    }
}
