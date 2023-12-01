package batleship;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//ShipField class represents the panel for the player's ship field
public class ShipField extends JPanel {
	private static final int EMPTY = 0;
	private static final int SHIP = 1;
	private static final int HIT = 2;
	private static final int SUNK = 3;

	private static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 12);
	private static final Font BUTTON_FONT = new Font("Arial", Font.PLAIN, 12);

	private int ships; // Number of ships placed on the field
	private ArrayList<int[]>[] shipsList; // List of ship coordinates
	private int hits; // Number of hits on ships

	private int[][][] fieldStatus; // 3D array to store the status of each cell on the field
	// fieldStatus[x][y][0] represents empty/ship/hit/sunk
	// fieldStatus[x][y][1] represents the ship number
	// fieldStatus[x][y][2] vert/horz ship display

	private JButton[][] fieldButtons; // Buttons representing each cell on the field

	private static final long serialVersionUID = 1L;

	// Constructor for ShipField panel
	public ShipField(String name, ActionListener listener) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		// Set up the layout with GridBagLayout
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		gridBagLayout.rowWeights = new double[] { 2.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0 };
		setLayout(gridBagLayout);
		// Add labels for grid coordinates
		JLabel lblPlayer = new JLabel(name);
		lblPlayer.setFont(LABEL_FONT);
		GridBagConstraints gbc_lblPlayer = new GridBagConstraints();
		gbc_lblPlayer.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayer.gridx = 0;
		gbc_lblPlayer.gridy = 0;
		add(lblPlayer, gbc_lblPlayer);

		JLabel lbl1 = new JLabel("1");
		lbl1.setFont(LABEL_FONT);
		GridBagConstraints gbc_lbl1 = new GridBagConstraints();
		gbc_lbl1.insets = new Insets(0, 0, 5, 5);
		gbc_lbl1.gridx = 1;
		gbc_lbl1.gridy = 0;
		add(lbl1, gbc_lbl1);

		JLabel lbl2 = new JLabel("2");
		lbl2.setFont(LABEL_FONT);
		GridBagConstraints gbc_lbl2 = new GridBagConstraints();
		gbc_lbl2.insets = new Insets(0, 0, 5, 5);
		gbc_lbl2.gridx = 2;
		gbc_lbl2.gridy = 0;
		add(lbl2, gbc_lbl2);

		JLabel lbl3 = new JLabel("3");
		lbl3.setFont(LABEL_FONT);
		GridBagConstraints gbc_lbl3 = new GridBagConstraints();
		gbc_lbl3.insets = new Insets(0, 0, 5, 5);
		gbc_lbl3.gridx = 3;
		gbc_lbl3.gridy = 0;
		add(lbl3, gbc_lbl3);

		JLabel lbl4 = new JLabel("4");
		lbl4.setFont(LABEL_FONT);
		GridBagConstraints gbc_lbl4 = new GridBagConstraints();
		gbc_lbl4.insets = new Insets(0, 0, 5, 5);
		gbc_lbl4.gridx = 4;
		gbc_lbl4.gridy = 0;
		add(lbl4, gbc_lbl4);

		JLabel lbl5 = new JLabel("5");
		lbl5.setFont(LABEL_FONT);
		GridBagConstraints gbc_lbl5 = new GridBagConstraints();
		gbc_lbl5.insets = new Insets(0, 0, 5, 5);
		gbc_lbl5.gridx = 5;
		gbc_lbl5.gridy = 0;
		add(lbl5, gbc_lbl5);

		JLabel lbl6 = new JLabel("6");
		lbl6.setFont(LABEL_FONT);
		GridBagConstraints gbc_lbl6 = new GridBagConstraints();
		gbc_lbl6.insets = new Insets(0, 0, 5, 5);
		gbc_lbl6.gridx = 6;
		gbc_lbl6.gridy = 0;
		add(lbl6, gbc_lbl6);

		JLabel lbl7 = new JLabel("7");
		lbl7.setFont(LABEL_FONT);
		GridBagConstraints gbc_lbl7 = new GridBagConstraints();
		gbc_lbl7.insets = new Insets(0, 0, 5, 5);
		gbc_lbl7.gridx = 7;
		gbc_lbl7.gridy = 0;
		add(lbl7, gbc_lbl7);

		JLabel lbl8 = new JLabel("8");
		lbl8.setFont(LABEL_FONT);
		GridBagConstraints gbc_lbl8 = new GridBagConstraints();
		gbc_lbl8.insets = new Insets(0, 0, 5, 5);
		gbc_lbl8.gridx = 8;
		gbc_lbl8.gridy = 0;
		add(lbl8, gbc_lbl8);

		JLabel lbl9 = new JLabel("9");
		lbl9.setFont(LABEL_FONT);
		GridBagConstraints gbc_lbl9 = new GridBagConstraints();
		gbc_lbl9.insets = new Insets(0, 0, 5, 5);
		gbc_lbl9.gridx = 9;
		gbc_lbl9.gridy = 0;
		add(lbl9, gbc_lbl9);

		JLabel lblA = new JLabel("A");
		lblA.setFont(LABEL_FONT);
		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.gridx = 0;
		gbc_lblA.gridy = 1;
		add(lblA, gbc_lblA);

		JLabel lblB = new JLabel("B");
		lblB.setFont(LABEL_FONT);
		GridBagConstraints gbc_lblB = new GridBagConstraints();
		gbc_lblB.insets = new Insets(0, 0, 5, 5);
		gbc_lblB.gridx = 0;
		gbc_lblB.gridy = 2;
		add(lblB, gbc_lblB);

		JLabel lblC = new JLabel("C");
		lblC.setFont(LABEL_FONT);
		GridBagConstraints gbc_lblC = new GridBagConstraints();
		gbc_lblC.insets = new Insets(0, 0, 5, 5);
		gbc_lblC.gridx = 0;
		gbc_lblC.gridy = 3;
		add(lblC, gbc_lblC);

		JLabel lblD = new JLabel("D");
		lblD.setFont(LABEL_FONT);
		GridBagConstraints gbc_lblD = new GridBagConstraints();
		gbc_lblD.insets = new Insets(0, 0, 5, 5);
		gbc_lblD.gridx = 0;
		gbc_lblD.gridy = 4;
		add(lblD, gbc_lblD);

		JLabel lblE = new JLabel("E");
		lblE.setFont(LABEL_FONT);
		GridBagConstraints gbc_lblE = new GridBagConstraints();
		gbc_lblE.insets = new Insets(0, 0, 5, 5);
		gbc_lblE.gridx = 0;
		gbc_lblE.gridy = 5;
		add(lblE, gbc_lblE);

		JLabel lblF = new JLabel("F");
		lblF.setFont(LABEL_FONT);
		GridBagConstraints gbc_lblF = new GridBagConstraints();
		gbc_lblF.insets = new Insets(0, 0, 5, 5);
		gbc_lblF.gridx = 0;
		gbc_lblF.gridy = 6;
		add(lblF, gbc_lblF);

		JLabel lblG = new JLabel("G");
		lblG.setFont(LABEL_FONT);
		GridBagConstraints gbc_lblG = new GridBagConstraints();
		gbc_lblG.insets = new Insets(0, 0, 5, 5);
		gbc_lblG.gridx = 0;
		gbc_lblG.gridy = 7;
		add(lblG, gbc_lblG);

		JLabel lblH = new JLabel("H");
		lblH.setFont(LABEL_FONT);
		GridBagConstraints gbc_lblH = new GridBagConstraints();
		gbc_lblH.insets = new Insets(0, 0, 5, 5);
		gbc_lblH.gridx = 0;
		gbc_lblH.gridy = 8;
		add(lblH, gbc_lblH);

		JLabel lblI = new JLabel("I");
		lblI.setFont(LABEL_FONT);
		GridBagConstraints gbc_lblI = new GridBagConstraints();
		gbc_lblI.insets = new Insets(0, 0, 0, 5);
		gbc_lblI.gridx = 0;
		gbc_lblI.gridy = 9;
		add(lblI, gbc_lblI);

		// Initialize field buttons with appropriate properties and listeners
		fieldButtons = new JButton[9][9];
		for (int i = 0; i < fieldButtons.length; i++) {
			for (int j = 0; j < fieldButtons.length; j++) {
				fieldButtons[i][j] = new JButton();
				fieldButtons[i][j].setFont(BUTTON_FONT);
				fieldButtons[i][j].setName(i + " " + j);
				fieldButtons[i][j].addActionListener(listener);

				GridBagConstraints gbc_fieldbtn = new GridBagConstraints();
				gbc_fieldbtn.gridy = i + 1;
				gbc_fieldbtn.gridx = j + 1;
				add(fieldButtons[i][j], gbc_fieldbtn);
			}
		}

		resetField();
	}

	public void resetField() {
		// Initialize fieldStatus array to represent an empty field
		fieldStatus = new int[9][9][3];
		for (int i = 0; i < fieldStatus.length; i++) {
			for (int j = 0; j < fieldStatus.length; j++) {
				fieldStatus[i][j][0] = EMPTY;
			}
		}

		this.showField(true); // redraw field
		shipsList = new ArrayList[6];// Initialize the array to store ship coordinates
		ships = 0; // clear placed ships
		hits = 0;// Initialize the number of hits
	}

	// Additional method to set the enabled/disabled state of the panel
	@Override
	public void setEnabled(boolean enabled) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				fieldButtons[i][j].setEnabled(enabled);
			}
		}
	}

	// Method to place a ship on the field
	public boolean placeShip(int r, int c, int size, int direction) {
		try {
			int dir = direction - 1 - direction % 2; // 0,1,2,3 -> -1,-1,1,1
			int start = direction % 2 == 0 ? r : c; // start at x or y based on odd/even of direction
			for (int i = start; i != start + size * dir; i += dir) { // loop from start to end of ship
				if (i >= 9 || (direction % 2 == 0 ? fieldStatus[i][c][0] : fieldStatus[r][i][0]) != EMPTY)
					return false;
			}
			ships++;

			ArrayList<int[]> newShip = new ArrayList<int[]>();
			for (int i = start; i != start + size * dir; i += dir) { // loop from start to end of ship
				int[] point = { direction % 2 == 0 ? i : r, direction % 2 == 0 ? c : i }; // figure out point to update

				fieldStatus[point[0]][point[1]][0] = SHIP; // update to be a floating ship
				fieldStatus[point[0]][point[1]][1] = ships; // set ship # on field
				fieldStatus[point[0]][point[1]][2] = direction % 2; // set ship rotation
				newShip.add(point);
				hits++;
			}

			shipsList[ships] = newShip;
			return true;
		} catch (IndexOutOfBoundsException iob) {
			return false;
		}
	}

	// methods displays the field and hides ships/sinkings if hidden
	public void showField(boolean hidden) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				String image = "";
				switch (fieldStatus[i][j][0]) {
				case SHIP:
					if (!hidden) {
						image = "ship" + (fieldStatus[i][j][2] == 0 ? "vert" : "");
					} // else: draw empty
				case EMPTY:
					// image = "water";
					break;
				case SUNK:
					if (!hidden) {
						image = "sunk";
						break;
					} // else: draw hit
				case HIT:
					image = "hit";
					break;
				}
				if (image != "") {
					fieldButtons[i][j].setText("");
					fieldButtons[i][j].setIcon(resizeImage("/images/" + image + ".png", 20, 20));
				} else
					fieldButtons[i][j].setText("X");
			}
		}
	}

	// Method to attempt hitting a cell on the opponent's field
	public int tryHit(int row, int col) {
		if (row < 0 || row >= 9 || col < 0 || col >= 9) {
			return 0;
		}

		if (fieldStatus[row][col][0] == SHIP) {
			fieldStatus[row][col][0] = HIT;
			hits--;
			if (hits == 0) { // win state
				return 2;
			}

			int shipHit = fieldStatus[row][col][1];

			boolean sunk = true;
			for (int[] point : shipsList[shipHit]) {
				sunk = fieldStatus[point[0]][point[1]][0] == HIT;
				if (!sunk)
					break;
			}
			if (sunk) { // ship sunk
				for (int[] point : shipsList[shipHit]) {
					fieldStatus[point[0]][point[1]][0] = SUNK;
				}
				return 3; // 3 indicates that a ship is sunk
			}

			return 1; // 1 indicates a regular hit
		}

		return 0; // 0 indicates a miss
	}

	// Method to resize an image given its path and desired dimensions
	private ImageIcon resizeImage(String imagePath, int width, int height) {
		ImageIcon imageIcon = new ImageIcon(getClass().getResource(imagePath));
		Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		return new ImageIcon(image);
	}
}
