package batleship;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import javax.swing.JTextArea;
import java.awt.Insets;

public class BattleShip extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BattleShip frame = new BattleShip();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public BattleShip() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clicked_About();
			}
		});
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE, 1.0};
		contentPane.setLayout(gbl_contentPane);
		
		ShipField field1 = new ShipField("CPU");
		GridBagConstraints gbc_field1 = new GridBagConstraints();
		gbc_field1.insets = new Insets(0, 0, 5, 5);
		gbc_field1.fill = GridBagConstraints.BOTH;
		gbc_field1.gridx = 0;
		gbc_field1.gridy = 0;
		contentPane.add(field1, gbc_field1);
		
		ShipField field2 = new ShipField("P1");
		GridBagConstraints gbc_field2 = new GridBagConstraints();
		gbc_field2.insets = new Insets(0, 0, 0, 5);
		gbc_field2.fill = GridBagConstraints.BOTH;
		gbc_field2.gridx = 0;
		gbc_field2.gridy = 1;
		contentPane.add(field2, gbc_field2);
		
		HUD hud = new HUD();
		GridBagConstraints gbc_hud = new GridBagConstraints();
		gbc_hud.insets = new Insets(0, 0, 0, 5);
		gbc_hud.fill = GridBagConstraints.BOTH;
		gbc_hud.gridx = 1;
		gbc_hud.gridy = 0;
		gbc_hud.gridheight = 2;
		contentPane.add(hud, gbc_hud);
		
		
	}

	protected void clicked_About() {
		JOptionPane.showMessageDialog(this, "Made by the Algorithmic Armada");
	}

}
