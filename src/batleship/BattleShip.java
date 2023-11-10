package batleship;

import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

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

	ShipField[] fields;
	HUD hud;

	List<Integer> ships; //list of ships used durring placing by a human player
	int[] point; //temp vars to keep track of locations durrent setup

	private boolean turn = true; //true for p1, false for p2(cpu)
	private boolean cpuPlayer = true; // t/f if there is a cpu player
	private int setup = 0; //game setup state 0-inplay 1-p1 setup 2-p2 setup

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
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, Double.MIN_VALUE, 1.0};
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

		ActionListener field2Listener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        String name = ((JComponent) e.getSource()).getName();
        fieldClicked(1, name);
      }
    };
		
		fields[0] = new ShipField("CPU", field2Listener);
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

	protected void fieldClicked(int field, String name) {
		int[] clickLoc = {-1,-1};

		//parse clicked location from name
		for(String num : name.split(" ")) {
			clickLoc[(clickLoc[0] == -1) ? 0 : 1] = Integer.parseInt(num);
		};

		// System.out.printf("click: field: %d square: %d, %d\n", field, clickLoc[0], clickLoc[1]);


		if(setup != 0) {
			if(point[0] == -1) {
				point[0] = clickLoc[0];
				point[1] = clickLoc[1];
				hud.println("Select second point");
			} else {
				int dir = -1;
				if(clickLoc[0] == point[0]) { ///same Y
					if(clickLoc[1] > point[1]) dir = 1; //right
					else dir = 3; //left
				} else if(clickLoc[1] == point[1])  {//same X
					if(clickLoc[0] > point[0]) dir = 2; //down
					else dir = 0; //up
				} else {
					hud.println("invalid diagonal ship placement");
				}
				if(dir != -1) {
					boolean placed = fields[field].placeShip(point[0], point[1], (int)ships.get(0), dir);
					if(placed) {
						ships.remove(0);
						hud.println("Placed ship succesfully!");
					} else {
						hud.println("Failed to place ship");
					}
				}
				if(!checkSetupOver()) hud.println("Select first point for ship size: " + ships.get(0));
				point[0] = -1;
				point[1] = -1;
			}
		//end of setup
		} else {
			
		}
	}

	//sets game logic flags for new game and begins setup
	private void init() {
		// if(cpuPlayer) field1.generateField();
		//setup instance vars ready for game setup
		setup = 1;
		ships = new ArrayList<Integer>(Arrays.asList(2,2,3,4,6));
		fields[1].setEnabled(true);
		point = new int[]{-1,-1}; //begin point as invalid value for fresh ship placement
		hud.println("select first point for ship size: " + ships.get(0));

	}

	//checks for ships left to place, then checks in the second players needs to place ships
	private boolean checkSetupOver() {
		if(ships.isEmpty()) {
			setup  = (cpuPlayer || setup == 2) ? 0 : 2; //true: 0, false: 2
			switch(setup) {
				case 0:
					fields[1].setEnabled(false);
					fields[0].setEnabled(false);
					hud.println("beginning Game turn: " + (turn ? "P1" : "CPU"));
					break;
				case 2:
					fields[1].setEnabled(false);
					fields[0].setEnabled(true);
					ships = new ArrayList<Integer>(Arrays.asList(2,2,3,4,6));
				default:
					break;
			}
		}
		return ships.isEmpty();
	}

	private void gameWin(int player) {
		fields[0].setEnabled(false);
		fields[1].setEnabled(false);
		hud.println("%s Wins!");
	}



	protected void clicked_About() {
		JOptionPane.showMessageDialog(this, "Made by the Algorithmic Armada");
	}

}
