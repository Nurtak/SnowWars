package ch.hsr.se2p.snowwars.view.lobby;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPanel extends JPanel{
	private static final long serialVersionUID = -4628393851839832247L;

	private final ViewMain vm;
	
	public MainPanel(final ViewMain vm){
		this.vm = vm;
		
		GridBagLayout gbl_mainPanel = new GridBagLayout();
		gbl_mainPanel.columnWidths = new int[]{0};
		gbl_mainPanel.rowHeights = new int[]{0};
		gbl_mainPanel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_mainPanel.rowWeights = new double[]{Double.MIN_VALUE};
		setLayout(gbl_mainPanel);
		
		JButton testButton = new JButton("test");
		testButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vm.clicked();
			}
		});
		
		add(testButton);
	}
}
