package batleship;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.List;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ShipField extends JPanel {
	private static final int EMPTY = 0;
	private static final int SHIP = 1;
	private static final int HIT = 2;
	private static final int SUNK = 3;


	private static int ships = 0;
	private static ArrayList<int[]>[] shipsList;
	private int hits;

	private int[][][] fieldStatus;
	// fieldStatus[x][y][0] empty/ship/hit/sunk
	// fieldStatus[x][y][1] ship#


	private JButton[][] fieldButtons;

	private static final long serialVersionUID = 1L;

	public ShipField(String name, ActionListener listener) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0,
				Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel lblPlayer = new JLabel(name);
		lblPlayer.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblPlayer = new GridBagConstraints();
		gbc_lblPlayer.insets = new Insets(0, 0, 5, 5);
		gbc_lblPlayer.gridx = 0;
		gbc_lblPlayer.gridy = 0;
		add(lblPlayer, gbc_lblPlayer);

		JLabel lbl1 = new JLabel("1");
		lbl1.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl1 = new GridBagConstraints();
		gbc_lbl1.insets = new Insets(0, 0, 5, 5);
		gbc_lbl1.gridx = 1;
		gbc_lbl1.gridy = 0;
		add(lbl1, gbc_lbl1);

		JLabel lbl2 = new JLabel("2");
		lbl2.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl2 = new GridBagConstraints();
		gbc_lbl2.insets = new Insets(0, 0, 5, 5);
		gbc_lbl2.gridx = 2;
		gbc_lbl2.gridy = 0;
		add(lbl2, gbc_lbl2);

		JLabel lbl3 = new JLabel("3");
		lbl3.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl3 = new GridBagConstraints();
		gbc_lbl3.insets = new Insets(0, 0, 5, 5);
		gbc_lbl3.gridx = 3;
		gbc_lbl3.gridy = 0;
		add(lbl3, gbc_lbl3);

		JLabel lbl4 = new JLabel("4");
		lbl4.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl4 = new GridBagConstraints();
		gbc_lbl4.insets = new Insets(0, 0, 5, 5);
		gbc_lbl4.gridx = 4;
		gbc_lbl4.gridy = 0;
		add(lbl4, gbc_lbl4);

		JLabel lbl5 = new JLabel("5");
		lbl5.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl5 = new GridBagConstraints();
		gbc_lbl5.insets = new Insets(0, 0, 5, 5);
		gbc_lbl5.gridx = 5;
		gbc_lbl5.gridy = 0;
		add(lbl5, gbc_lbl5);

		JLabel lbl6 = new JLabel("6");
		lbl6.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl6 = new GridBagConstraints();
		gbc_lbl6.insets = new Insets(0, 0, 5, 5);
		gbc_lbl6.gridx = 6;
		gbc_lbl6.gridy = 0;
		add(lbl6, gbc_lbl6);

		JLabel lbl7 = new JLabel("7");
		lbl7.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl7 = new GridBagConstraints();
		gbc_lbl7.insets = new Insets(0, 0, 5, 5);
		gbc_lbl7.gridx = 7;
		gbc_lbl7.gridy = 0;
		add(lbl7, gbc_lbl7);

		JLabel lbl8 = new JLabel("8");
		lbl8.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl8 = new GridBagConstraints();
		gbc_lbl8.insets = new Insets(0, 0, 5, 5);
		gbc_lbl8.gridx = 8;
		gbc_lbl8.gridy = 0;
		add(lbl8, gbc_lbl8);

		JLabel lbl9 = new JLabel("9");
		lbl9.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lbl9 = new GridBagConstraints();
		gbc_lbl9.insets = new Insets(0, 0, 5, 5);
		gbc_lbl9.gridx = 9;
		gbc_lbl9.gridy = 0;
		add(lbl9, gbc_lbl9);

		JLabel lblA = new JLabel("A");
		lblA.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.gridx = 0;
		gbc_lblA.gridy = 1;
		add(lblA, gbc_lblA);

		JLabel lblB = new JLabel("B");
		lblB.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblB = new GridBagConstraints();
		gbc_lblB.insets = new Insets(0, 0, 5, 5);
		gbc_lblB.gridx = 0;
		gbc_lblB.gridy = 2;
		add(lblB, gbc_lblB);

		JLabel lblC = new JLabel("C");
		lblC.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblC = new GridBagConstraints();
		gbc_lblC.insets = new Insets(0, 0, 5, 5);
		gbc_lblC.gridx = 0;
		gbc_lblC.gridy = 3;
		add(lblC, gbc_lblC);

		JLabel lblD = new JLabel("D");
		lblD.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblD = new GridBagConstraints();
		gbc_lblD.insets = new Insets(0, 0, 5, 5);
		gbc_lblD.gridx = 0;
		gbc_lblD.gridy = 4;
		add(lblD, gbc_lblD);

		JLabel lblE = new JLabel("E");
		lblE.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblE = new GridBagConstraints();
		gbc_lblE.insets = new Insets(0, 0, 5, 5);
		gbc_lblE.gridx = 0;
		gbc_lblE.gridy = 5;
		add(lblE, gbc_lblE);

		JLabel lblF = new JLabel("F");
		lblF.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblF = new GridBagConstraints();
		gbc_lblF.insets = new Insets(0, 0, 5, 5);
		gbc_lblF.gridx = 0;
		gbc_lblF.gridy = 6;
		add(lblF, gbc_lblF);

		JLabel lblG = new JLabel("G");
		lblG.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblG = new GridBagConstraints();
		gbc_lblG.insets = new Insets(0, 0, 5, 5);
		gbc_lblG.gridx = 0;
		gbc_lblG.gridy = 7;
		add(lblG, gbc_lblG);

		JLabel lblH = new JLabel("H");
		lblH.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblH = new GridBagConstraints();
		gbc_lblH.insets = new Insets(0, 0, 5, 5);
		gbc_lblH.gridx = 0;
		gbc_lblH.gridy = 8;
		add(lblH, gbc_lblH);

		JLabel lblI = new JLabel("I");
		lblI.setFont(new Font("Arial", Font.PLAIN, 12));
		GridBagConstraints gbc_lblI = new GridBagConstraints();
		gbc_lblI.insets = new Insets(0, 0, 0, 5);
		gbc_lblI.gridx = 0;
		gbc_lblI.gridy = 9;
		add(lblI, gbc_lblI);

		fieldStatus = new int[9][9][2];
		for (int i = 0; i < fieldStatus.length; i++) {
			for (int j = 0; j < fieldStatus.length; j++) {
				fieldStatus[i][j][0] = EMPTY;
			}
		}

		// Setup field buttons
		fieldButtons = new JButton[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				fieldButtons[i][j] = new JButton("X");
				fieldButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 10));
				fieldButtons[i][j].setName(i + " " + j);
				fieldButtons[i][j].setEnabled(true);
				fieldButtons[i][j].addActionListener(listener);

				GridBagConstraints gbc_fieldbtn = new GridBagConstraints();
				gbc_fieldbtn.gridy = i + 1;
				gbc_fieldbtn.gridx = j + 1;
				add(fieldButtons[i][j], gbc_fieldbtn);
			}
		}

		shipsList = new ArrayList[6];
		hits = 0;
	}

	@Override
	public void setEnabled(boolean enabled) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				fieldButtons[i][j].setEnabled(enabled);
			}
		}
	}

	public boolean placeShip(int y, int x, int size, int direction) {
		if (x < 0 || x >= 9 || y < 0 || y >= 9 || size <= 0) {
			return false;
		}

		try {
			if (direction == 2) { // down
				for (int i = 0; i < size; i++) {
					if (y + i >= 9 || fieldStatus[y + i][x][0] != EMPTY) {
						return false;
					}
				}
				ships++;

				ArrayList<int[]> newShip = new ArrayList<int[]>();
				for (int i = 0; i < size; i++) {
					fieldStatus[y + i][x][0] = SHIP;
					fieldButtons[y + i][x].setBackground(Color.DARK_GRAY);

					fieldStatus[y + i][x][1] = ships; //save ship's #
					int[] point = {y+i, x};	//save point in array
					newShip.add(point);	//push point into list
					hits++;
				}

				shipsList[ships] = newShip;

			} else if (direction == 0) { // up
				for (int i = size * -1 + 1; i <= 0; i++) {
					if (y + i >= 9 || fieldStatus[y + i][x][0] != EMPTY) {
						return false;
					}
				}
				for (int i = size * -1 + 1; i <= 0; i++) {
					fieldStatus[y + i][x][0] = SHIP;
					fieldButtons[y + i][x].setBackground(Color.DARK_GRAY);
				}
			} else if (direction == 3) { // left
				for (int i = size * -1 + 1; i <= 0; i++) {
					if (x + i >= 9 || fieldStatus[y][x + i][0] != EMPTY) {
						return false;
					}
				}
				for (int i = size * -1 + 1; i <= 0; i++) {
					fieldStatus[y][x + i][0] = SHIP;
					fieldButtons[y][x + i].setBackground(Color.DARK_GRAY);
				}
			} else if (direction == 1) { // right
				for (int i = 0; i < size; i++) {
					if (x + i >= 9 || fieldStatus[y][x + i][0] != EMPTY) {
						return false;
					}
				}
				for (int i = 0; i < size; i++) {
					fieldStatus[y][x + i][0] = SHIP;
					fieldButtons[y][x + i].setBackground(Color.DARK_GRAY);
				}
			}
			return true;
		} catch (IndexOutOfBoundsException iob) {
			return false;
		}
	}

	public int tryHit(int row, int col) {
		if (row < 0 || row >= 9 || col < 0 || col >= 9) {
			return 0;
		}

		if (fieldStatus[row][col][0] == SHIP) {
			fieldStatus[row][col][0] = HIT;
			fieldButtons[row][col].setBackground(Color.RED);
			
			hits--;
			if(hits == 0) { //win state
				return 2;
			}

			int shipHit = fieldStatus[row][col][1];

			boolean sunk = true;
			for(int[] point : shipsList[shipHit]) {
				sunk = fieldStatus[point[0]][point[1]][0] != HIT;
			}	
			if(sunk) { //ship sunk
				//TODO
			}

			return 1;
		}

		return 0;
	}
}
