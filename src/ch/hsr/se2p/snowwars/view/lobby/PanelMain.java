package ch.hsr.se2p.snowwars.view.lobby;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import ch.hsr.se2p.snowwars.application.SnowWarsClient;

public class PanelMain extends JPanel {

    private static final long serialVersionUID = -4628393851839832247L;
    private SnowWarsClient snowWarsClient;
    private final ViewMain vm;

    public PanelMain(SnowWarsClient snowWarsClient, final ViewMain vm) {
        this.snowWarsClient = snowWarsClient;
        this.vm = vm;
        createMainPanel();
    }

    private void createMainPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 0, 0 };
        gridBagLayout.rowHeights = new int[] { 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
        setLayout(gridBagLayout);

        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vm.addPanel(new PanelUser(vm, snowWarsClient.getRunRMIClient().getConnectedServerSessionInterface()), "userPanel");
                vm.nextCard();
            }
        });
        GridBagConstraints gbc_playButton = new GridBagConstraints();
        gbc_playButton.fill = GridBagConstraints.BOTH;
        gbc_playButton.insets = new Insets(0, 0, 5, 0);
        gbc_playButton.gridx = 0;
        gbc_playButton.gridy = 0;
        add(playButton, gbc_playButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vm.exit();
            }
        });
        GridBagConstraints gbc_exitButton = new GridBagConstraints();
        gbc_exitButton.fill = GridBagConstraints.BOTH;
        gbc_exitButton.gridx = 0;
        gbc_exitButton.gridy = 1;
        add(exitButton, gbc_exitButton);

    }
}
