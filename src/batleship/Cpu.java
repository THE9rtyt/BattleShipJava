package batleship;

import java.util.List;

public class Cpu {
	/*
	 * tracker of enemy field 0 = not touched 1 = empty 2 = ship hit
	 */
	private int[][] enemy_field;
	private ShipField ship_field;

	public Cpu(ShipField s_field) {
		ship_field = s_field;

		enemy_field = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				enemy_field[i][j] = 0; // not touched
			}
		}
	}

	public void generateField(List<Integer> ships) {
		while (!ships.isEmpty()) {
			int xPlace = (int) (Math.random() * 9);
			int yPlace = (int) (Math.random() * 9);
			int dirPlace = (int) (Math.random() * 4);

			if (ship_field.placeShip(xPlace, yPlace, ships.get(0), dirPlace))
				ships.remove(0);
		}
	}

	public int[] getNextTurn() {
		int[] point = new int[2];

		// newhit validity t/f
		boolean newHit = false;
		while (!newHit) { // while we have an invalid hit
			point[0] = (int) (Math.random() * 9);
			point[1] = (int) (Math.random() * 9);

			if (enemy_field[point[0]][point[1]] == 0) {
				newHit = true; // valid hit if we haven't tried it yet
			}
		}

		return point;
	}

	// takes the result of the turn and saves it to the enemy field var
	public void updateField(int r, int c, int hitStatus) {
		// a hitStatus of 0 is a miss, otherwise we hit/sunk/won
		enemy_field[r][c] = hitStatus == 0 ? 1 : 2;
	}
}
