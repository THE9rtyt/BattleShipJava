package batleship;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.EventQueue;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class BattleShip extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	// gui variables
	private HUD hud;
	private ShipField[] fields;

	// var for copying off of, main list of ships to place
	private static final List<Integer> SHIPS_ALL = Arrays.asList(2, 2, 3, 4, 6);
	private List<Integer> ships; // list of ships used durring placing by a human player
	private int[] point; // temp vars to keep track of locations current setup

	// game settings variables
	private boolean turn = true; // true for p1, false for p2(cpu)
	private boolean cpuPlayer = true; // t/f if there is a cpu player

	// cpu player object
	private Cpu cpu;
	/*
	 * game setup state 0-inplay 1-p1 setup 2-p2 setup 3-game finished
	 */
	private int setup = 0;
	// number of turns in the game
	private int turns;

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
		this.setTitle("BattleShip");

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
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		ActionListener field1Listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = ((JComponent) e.getSource()).getName();
				fieldClicked(1, name);
			}
		};

		fields = new ShipField[2];

		fields[1] = new ShipField("P1", field1Listener);
		fields[1].setEnabled(false);
		GridBagConstraints gbc_field1 = new GridBagConstraints();
		gbc_field1.insets = new Insets(0, 0, 5, 5);
		gbc_field1.fill = GridBagConstraints.BOTH;
		gbc_field1.gridx = 0;
		gbc_field1.gridy = 1;
		contentPane.add(fields[1], gbc_field1);

		ActionListener field0Listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = ((JComponent) e.getSource()).getName();
				fieldClicked(0, name);
			}
		};

		fields[0] = new ShipField("CPU", field0Listener);
		fields[0].setEnabled(false);
		GridBagConstraints gbc_field2 = new GridBagConstraints();
		gbc_field2.insets = new Insets(0, 0, 0, 5);
		gbc_field2.fill = GridBagConstraints.BOTH;
		gbc_field2.gridx = 0;
		gbc_field2.gridy = 0;
		contentPane.add(fields[0], gbc_field2);

		hud = new HUD();
		GridBagConstraints gbc_hud = new GridBagConstraints();
		gbc_hud.insets = new Insets(0, 0, 0, 5);
		gbc_hud.fill = GridBagConstraints.BOTH;
		gbc_hud.gridx = 1;
		gbc_hud.gridy = 0;
		gbc_hud.gridheight = 2;
		contentPane.add(hud, gbc_hud);

		init();
	}

	// sets game logic flags for new game and begins setup
	private void init() {
		if (cpuPlayer) {
			cpu = new Cpu(fields[0]);
			ships = new ArrayList<Integer>(SHIPS_ALL);
			cpu.generateField(ships);
		}
		// setup instance vars ready for game setup
		// start with no turns played
		turns = 0;
		hud.setTurn(turns);
		// setup for player 1 status
		setup = 1;
		// put ships into otu ship "stack"
		ships = new ArrayList<Integer>(SHIPS_ALL);
		// enable filed 1 for P1 to place ships
		fields[1].setEnabled(true);
		point = new int[] { -1, -1 }; // begin point as invalid value for fresh ship placement
		hud.println("select first point for ship size: " + ships.get(0));
	}

	protected void fieldClicked(int field, String name) {
		int[] clickLoc = { -1, -1 };

		// parse clicked location from name
		for (String num : name.split(" ")) {
			clickLoc[(clickLoc[0] == -1) ? 0 : 1] = Integer.parseInt(num);
		}

		if (setup != 0) {
			// continue with ship setup
			setupShips(field, clickLoc[0], clickLoc[1]);
		} else {
			doTurn(field, clickLoc[0], clickLoc[1]);

			if (!turn && cpuPlayer && setup != 3) { // check if CPU is playing and the game is not over
				// cpu play
				int[] cpuTarget = cpu.getNextTurn();
				int hitStatus = doTurn(1, cpuTarget[0], cpuTarget[1]);
				cpu.updateField(cpuTarget[0], cpuTarget[1], hitStatus);
			}

			if (turn)
				turns++; // inc at the end of p2 turn
			hud.setTurn(turns);
		}

		fields[0].showField(turn);
		fields[1].showField(!turn);
	}

	private void setupShips(int field, int r, int c) {
		if (point[0] == -1) {
			point[0] = r;
			point[1] = c;
			hud.println("Select second point");
		} else {
			int dir = -1;
			if (r == point[0]) { // same row
				if (c > point[1])
					dir = 3; // right
				else
					dir = 1; // left
			} else if (c == point[1]) {// same column
				if (r > point[0])
					dir = 2; // down
				else
					dir = 0; // up
			} else {
				hud.println("invalid diagonal ship placement");
			}
			if (dir != -1) {
				boolean placed = fields[field].placeShip(point[0], point[1], (int) ships.get(0), dir);
				if (placed) {
					ships.remove(0);
					hud.println("Placed ship succesfully!");
				} else {
					hud.println("Failed to place ship");
				}
			}
			if (!checkSetupOver())
				hud.println("Select first point for ship size: " + ships.get(0));
			point[0] = -1;
			point[1] = -1;
		}
	}

	// checks for ships left to place, then checks for the second players needs to
	// place ships
	private boolean checkSetupOver() {
		if (ships.isEmpty()) {
			// sets the next setup state, goes to 0 when cpu playing or player 2 setup over
			setup = (cpuPlayer || setup == 2) ? 0 : 2; // true: 0, false: 2
			switch (setup) {
			case 0:
				hud.println("beginning Game turn: " + (turn ? "P1" : "CPU"));
				fields[0].setEnabled(turn);
				fields[1].setEnabled(!turn);
				break;
			case 2:
				fields[1].setEnabled(false);
				fields[0].setEnabled(true);
				ships = new ArrayList<Integer>(SHIPS_ALL);
			default:
				break;
			}
		}
		return ships.isEmpty(); // this might be refilled(P2 setup), it might not(P2 setup over or CPU playing)
	}

	private int doTurn(int field, int r, int c) {
		hud.print((turn ? "P1" : "CPU") + " aims for " + convert2char(r) + (c + 1));

		int status = fields[field].tryHit(r, c);

		switch (status) {
		case 1: // normal hit
			hud.println(".....hit!");
			break;
		case 0: // normal miss
			hud.println(".....miss!");
			break;
		case 3: // ship sunk
			hud.println("..ship sunk!");
			break;
		case 2:
			gameWin(field);
			return status;
		}

		turn = !turn; // switch turn
		return status;
	}

	private char convert2char(int num) {
		return (char) (num + 0x41); // 0x41 turns int 0..9 into ascii for A..I
	}

	private void gameWin(int player) {
		fields[0].setEnabled(false);
		fields[1].setEnabled(false);
		setup = 3;
		hud.println(".....hit!\n\n" + (turn ? "P1" : "CPU") + "Wins!");
	}

	protected void clicked_About() {
		JOptionPane.showMessageDialog(this, "Made by the Algorithmic Armada");
	}

}
