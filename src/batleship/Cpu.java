package batleship;

import java.util.ArrayList;
import java.util.List;

public class Cpu {
	// tracker of enemy field 0 = not tried 1 = empty 2 = ship hit
	private int[][] enemy_field;
	// reference to our ShipField
	private ShipField ship_field;

	// AI variables
	int[] point; // remember last hit point
	int rotation; // remeber last rotation directon
	int[] rotPoint;

	ArrayList<Integer> rotPool; // pool of rotations
	/*
	 * keep track of what we're doing 0 - random 1 - rotating 2 - locked on
	 */
	int state;

	public Cpu(ShipField s_field) {
		ship_field = s_field; // save a reference to our field

		// setup AI vars
		point = new int[2];
		rotPool = new ArrayList<>();
		rotPoint = new int[2];
		state = 0;

		enemy_field = new int[9][9];

		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				enemy_field[i][j] = 0; // initialize not tried
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
		switch (state) {
		case 1: // rotating
			calcNextHit(rotPoint);
			break;
		case 2: // locked on
			calcNextHit(point);
			break;
		default:
			randomHit();
		}
		return point;
	}

	// takes the result of the turn and saves it to the enemy field var
	public void updateField(int r, int c, int hitStatus) {
		// a hitStatus of 0 is a miss, otherwise we hit/sunk/won
		enemy_field[r][c] = hitStatus == 0 ? 1 : 2;

		if (state == 0 && hitStatus != 0) {
			state = 1; // if we hit in state 0 rand, move to state 1 rot
			refillRotationPool(); // refill pool and pop a direction out
			rotation = rotPool.remove(0);
			rotPoint[0] = point[0]; // save our rotating point
			rotPoint[1] = point[1];
		} else if (state == 1)
			if (hitStatus == 0) {
				// no hit, rotate to next place if rotpool is not empty
				if (!rotPool.isEmpty())
					rotation = rotPool.remove(0);
				else
					state = 0;
			} else
				state = 2; // if we hit in state 1 rot, move to state 2 loc
		else if (state == 2 && hitStatus == 0)
			resetState(); // miss in state 2, reset
	}

	// reads rotation and point given and saves next place in the point instance
	// var, checks for out of bounds, does randhom hit of reset state if needed
	private void calcNextHit(int[] p) {
		int dir = rotation % 2; // even:true odd:false
		// calc new location in the right axis and direction
		int newPlace = (dir == 0 ? p[1] : p[0]) + rotation - 1 - dir; 
		if (checkBounds(newPlace)) { // check bounds and set next point, else reset state
			point[0] = (dir == 0 ? p[0] : newPlace);
			point[1] = (dir == 0 ? newPlace : p[1]);
		} else
			resetState();
	}

	private boolean checkBounds(int n) {
		return n >= 0 && n < 9;
	}

	private void resetState() {
		if (state == 2 && !rotPool.isEmpty()) { // if we can continue rotating
			rotation = rotPool.remove(0); // rotational "soft" reset, rotPoint should still be saved
			state = 1;
		} else { // full reset
			state = 0;
			randomHit();
		}
	}

	private void refillRotationPool() {
		while (!rotPool.isEmpty())
			rotPool.remove(0); // empty rotationPool

		while (rotPool.size() < 4) {
			int num = (int) (Math.random() * 4);
			if (!rotPool.contains(num))
				rotPool.add(num);
		}
	}

	// sets point to a new random point that has not been tried yet
	private void randomHit() {
		for (boolean newHit = false; !newHit;) { // while we have an invalid hit
			point[0] = (int) (Math.random() * 9);
			point[1] = (int) (Math.random() * 9);

			if (enemy_field[point[0]][point[1]] == 0) {
				newHit = true; // valid hit if we haven't tried it yet
			}
		}
	}
}
