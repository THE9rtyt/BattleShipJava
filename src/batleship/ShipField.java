package batleship;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComponent;
import java.awt.EventQueue;
import javax.swing.JFrame;
import java.util.Random;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ShipField extends JPanel {
	// constants to represent the status of a cell in the field
    private static final int EMPTY = 0;
    private static final int SHIP = 1;
    private static final int HIT = 2;
    private static final int SUNK = 3;

    private JFrame frame;
    private int[][] fieldStatus; // array to keep track of field status

    public ShipField() {
        initialize();
        fieldStatus = new int[9][9];
        generateField(); // initialize the field with ships
    }

    // initialize the frame
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // method to randomly place ships on the field for CPU players
    private void generateField() {
        Random rand = new Random();
        int x = rand.nextInt(10);
        int y = rand.nextInt(10);
        int direction = rand.nextInt(2); // 0 for horizontal, 1 for vertical

        if (direction == 0) {
            for (int i = 0; i < 5; i++) {
                if (x + i < 10) {
                    fieldStatus[y][x + i] = SHIP;
                }
            }
        } else {
            for (int i = 0; i < 5; i++) {
                if (y + i < 10) {
                    fieldStatus[y + i][x] = SHIP;
                }
            }
        }
    }

    // method to place a ship at the location specified by the user
    public boolean placeShip(int x, int y, int size, int direction) {
        // validate input
        if (x < 0 || x >= 10 || y < 0 || y >= 10 || size <= 0 || (direction != 0 && direction != 1)) {
            return false;
        }

        // check if ship can be placed without overlapping or going out of bounds
        if (direction == 0) {
            for (int i = 0; i < size; i++) {
                if (x + i >= 10 || fieldStatus[y][x + i] != EMPTY) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (y + i >= 10 || fieldStatus[y + i][x] != EMPTY) {
                    return false;
                }
            }
        }

        // place the ship
        if (direction == 0) {
            for (int i = 0; i < size; i++) {
                fieldStatus[y][x + i] = SHIP;
            }
        } else {
            for (int i = 0; i < size; i++) {
                fieldStatus[y + i][x] = SHIP;
            }
        }

        return true;
    }

    // method to attempt a hit at the specified location
    public boolean tryHit(int row, int col) {
        // validate input
        if (row < 0 || row >= 10 || col < 0 || col >= 10) {
            return false;
        }

        // check if there is a ship at the specified location
        if (fieldStatus[row][col] == SHIP) {
            fieldStatus[row][col] = HIT;
            return true;
        }

        return false;
    }	
	JButton[][] fieldButtons;

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ShipField(String name) {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
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
		
		//setup field buttons
		fieldButtons = new JButton[9][9];
		//loop rows
		for(int i = 0; i < 9; i++) {
			//loop columns
			for(int j = 0; j < 9; j++) {
				// System.out.println(r+c + "row: " + i + "  col: " + j);
				fieldButtons[i][j] = new JButton("X");
				fieldButtons[i][j].setFont(new Font("Arial", Font.PLAIN, 10));
				fieldButtons[i][j].setName(i + " " + j);
				fieldButtons[i][j].setEnabled(true);
				fieldButtons[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.out.println(((JComponent) e.getSource()).getName());
						//TODO: complete action listener for clicking field
					}
				});
				
				GridBagConstraints gbc_fieldbtn = new GridBagConstraints();
				gbc_fieldbtn.gridy = i+1;
				gbc_fieldbtn.gridx = j+1;
				add(fieldButtons[i][j], gbc_fieldbtn);
			}
		}

	}

	//overide set enabled to mass setEnable the buttons
	@Override
	public void setEnabled(boolean enabled) {
		//loop rows
		for(int i = 0; i < 9; i++) {
			//loop columns
			for(int j = 0; j < 9; j++) {
				fieldButtons[i][j].setEnabled(enabled);
			}
		}
	}
}