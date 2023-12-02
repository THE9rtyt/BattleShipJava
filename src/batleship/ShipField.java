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
	private static final int SQUARE_SIZE = 20;

	private static Icon waterIcon;
	private static Icon shipIcon;
	private static Icon shipVertIcon;
	private static Icon hitIcon;
	private static Icon sunkIcon;

	private int ships; // Number of ships placed on the field
	private ArrayList<int[]>[] shipsList; // List of ship coordinates
	private int hits; // Number of hits on ships

	private int[][][] fieldStatus; // 3D array to store the status of each cell on the field
	// fieldStatus[x][y][0] represents empty/ship/hit/sunk
	// fieldStatus[x][y][1] represents the ship number
	// fieldStatus[x][y][2] vert/horz ship display
	// fieldStatus[x][y][3] touched(1) or not(0)

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
		lblPlayer.setSize(SQUARE_SIZE, SQUARE_SIZE);
		GridBagConstraints gbc_lblPlayer = new GridBagConstraints();
		gbc_lblPlayer.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayer.gridx = 0;
		gbc_lblPlayer.gridy = 0;
		add(lblPlayer, gbc_lblPlayer);

		// Initialize field axis labels
		JLabel[] lblNums = new JLabel[9];
		JLabel[] lblLets = new JLabel[9];
		for (int i = 0; i < 9; i++) {
			//create label
			lblNums[i] = new JLabel(Integer.toString(i + 1)); // 0..8 -> "1".."9"
			lblLets[i] = new JLabel(Character.toString(i + 0x41)); //0..8 -> "A".."I"
			lblNums[i].setFont(LABEL_FONT);
			lblLets[i].setFont(LABEL_FONT);
			lblNums[i].setSize(SQUARE_SIZE, SQUARE_SIZE);
			lblLets[i].setSize(SQUARE_SIZE, SQUARE_SIZE);
			//create constraints
			GridBagConstraints gbc_lbl = new GridBagConstraints();
			gbc_lbl.insets = new Insets(0, 0, 0, 0);
			//set location and add
			gbc_lbl.gridx = i + 1;
			gbc_lbl.gridy = 0;
			add(lblNums[i], gbc_lbl);
			//set location and add
			gbc_lbl.gridx = 0;
			gbc_lbl.gridy = i + 1;
			add(lblLets[i], gbc_lbl);
		}

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

		if (waterIcon == null) {
			waterIcon = resizeImage("/images/water.png", SQUARE_SIZE, SQUARE_SIZE);
			shipIcon = resizeImage("/images/ship.png", SQUARE_SIZE, SQUARE_SIZE);
			shipVertIcon = resizeImage("/images/shipvert.png", SQUARE_SIZE, SQUARE_SIZE);
			hitIcon = resizeImage("/images/hit.png", SQUARE_SIZE, SQUARE_SIZE);
			sunkIcon = resizeImage("/images/sunk.png", SQUARE_SIZE, SQUARE_SIZE);
		}

		resetField();
	}

	public void resetField() {
		// Initialize fieldStatus array to represent an empty field
		fieldStatus = new int[9][9][4];
		for (int i = 0; i < fieldStatus.length; i++) {
			for (int j = 0; j < fieldStatus.length; j++) {
				fieldStatus[i][j][0] = EMPTY;
				fieldStatus[i][j][3] = 0;	
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
				Icon image;
				switch (fieldStatus[i][j][0]) {
				case SHIP:
					if (!hidden) {
						image = fieldStatus[i][j][2] == 0 ? shipVertIcon : shipIcon;
						break;
					} // else: draw empty
				default:
					image = waterIcon;
					break;
				case SUNK:
					if (!hidden) {
						image = sunkIcon;
						break;
					} // else: draw hit
				case HIT:
					image = hitIcon;
					break;
				}
				fieldButtons[i][j].setIcon(image);
				fieldButtons[i][j].setDisabledIcon(image);

				//red if the point has been touched, else enabled/disabled grey
				Color color = fieldStatus[i][j][3]== 1 ? Color.decode(Integer.toString(0xcc1111)) : 
						fieldButtons[i][j].isEnabled() ? Color.gray : Color.lightGray;
				fieldButtons[i][j].setBackground(color);
				fieldButtons[i][j].setBorder(BorderFactory.createLineBorder(color, 5, true));
			}
		}
	}

	// Method to attempt hitting a cell on the opponent's field
	public int tryHit(int row, int col) {
		if (row < 0 || row >= 9 || col < 0 || col >= 9) {
			return 0;
		}
		fieldStatus[row][col][3] = 1; //touch

		if (fieldStatus[row][col][0] == SHIP) {
			fieldStatus[row][col][0] = HIT;
			hits--;

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
				if (hits == 0)
					return 2; //win state
				else
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
