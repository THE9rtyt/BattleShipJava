package batleship;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextArea;

import java.awt.Font;

public class HUD extends JPanel {

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public HUD() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{36, 0, 0};
		gridBagLayout.rowHeights = new int[]{13, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblCur = new JLabel("Current Player:");
		lblCur.setFont(new Font("Arial Black", Font.PLAIN, 14));
		GridBagConstraints gbc_lblCur = new GridBagConstraints();
		gbc_lblCur.insets = new Insets(0, 0, 5, 5);
		gbc_lblCur.anchor = GridBagConstraints.EAST;
		gbc_lblCur.gridx = 0;
		gbc_lblCur.gridy = 0;
		add(lblCur, gbc_lblCur);
		
		JLabel lblCurPlayer = new JLabel("1");
		lblCurPlayer.setFont(new Font("Arial Black", Font.PLAIN, 14));
		GridBagConstraints gbc_lblCurPlayer = new GridBagConstraints();
		gbc_lblCurPlayer.anchor = GridBagConstraints.WEST;
		gbc_lblCurPlayer.insets = new Insets(0, 0, 5, 0);
		gbc_lblCurPlayer.gridx = 1;
		gbc_lblCurPlayer.gridy = 0;
		add(lblCurPlayer, gbc_lblCurPlayer);
		
		JTextArea txtConsole = new JTextArea();
		txtConsole.setText("");
		txtConsole.setEditable(false);
		GridBagConstraints gbc_txtrYeet = new GridBagConstraints();
		gbc_txtrYeet.gridwidth = 2;
		gbc_txtrYeet.insets = new Insets(0, 0, 0, 5);
		gbc_txtrYeet.fill = GridBagConstraints.BOTH;
		gbc_txtrYeet.gridx = 0;
		gbc_txtrYeet.gridy = 1;
		add(txtConsole, gbc_txtrYeet);

	}

}
