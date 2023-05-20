package Abstraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Barrier {

	int x, y, direction;

	/*
	 * the barrier are made to be set on junction of the line, while the pawn are on
	 * plain square
	 */
	
	public Barrier(int x, int y, int direction) {
		this.x = x;
		this.y = y;
		this.direction = direction;
	}

	// x = column, between 0 and 8
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	// y = row, between 0 and 8
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	// direction = 0 vertical barrier, direction = 1 horizontal barrier
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	boolean isBarrierPlacementValid(List<List<Integer>> playersPosition, List<Barrier> listBarriers) {
		// out of board
		if ((this.getX() < 0 || this.getX() > 9) || (this.getY() < 0 || this.getY() > 9))
			return false;

		// half in the board
		else if ((this.getX() == 0 || this.getX() == 9) && this.getDirection() == 1)
			return false;
		else if ((this.getY() == 0 || this.getY() == 9) && this.getDirection() == 0)
			return false;

		// look for barrier in the same place or just next to in the same direction
		for (Barrier fence : listBarriers) {
			int fenceX = fence.getX();
			int fenceY = fence.getY();
			int fenceDirection = fence.getDirection();

			if (fenceX == this.getX() && fenceY == this.getY()) // same place
				return false;
			if (this.getDirection() == fenceDirection) { // next to in the same direction
				if (this.getDirection() == 0 && (this.getX() == fenceX + 1 || this.getX() == fenceX - 1)
						&& this.getY() == fenceY)
					return false;
				else if (this.getDirection() == 1 && (this.getY() == fenceY + 1 || this.getY() == fenceY - 1)
						&& this.getX() == fenceX)
					return false;
			}
		}

		if (!pathFinding(playersPosition, listBarriers))
			return false;
		return true;
	}

	private boolean pathFinding(List<List<Integer>> playersPositions, List<Barrier> listBarriers) {
		// principle of BFS
		// add new barrier to see if it still allow a way out
		listBarriers.add(this);
		// help define adjacent square
		int[] rowAdj = { 0, 1, 0, -1 };
		int[] colAdj = { -1, 0, 1, 0 };

		for (int i = 0; i < playersPositions.size(); i++) {
			// initialize for a random player
			boolean[][] visited = new boolean[9][9];
			Queue<List<Integer>> queue = new LinkedList<>();

			visited[playersPositions.get(i).get(0)][playersPositions.get(i).get(1)] = true;
			queue.add(playersPositions.get(i));

			while (!queue.isEmpty()) {
				// actualize the point where we are
				System.out.println(queue);
				List<Integer> currentPosition = queue.poll();
				// check if we reach the opposite side, depending on which player you are
				if ((i == 0 && currentPosition.get(1) == 8) || (i == 1 && currentPosition.get(1) == 0)
						|| (i == 2 && currentPosition.get(0) == 8) || (i == 3 && currentPosition.get(0) == 8))
					// good for this player, we look for other player next
					break;

				// look which adjacent square can be reach
				for (int k = 0; k < 4; k++) {
					// Define the next position to look for
					List<Integer> adjPosition = new ArrayList<>();
					adjPosition.add(currentPosition.get(0) + rowAdj[k]);
					adjPosition.add(currentPosition.get(1) + colAdj[k]);
					if (isValidMove(currentPosition, adjPosition, k, visited, listBarriers)) {
						visited[adjPosition.get(0)][adjPosition.get(1)] = true;
						queue.add(adjPosition);
					}
				}
				if (queue.isEmpty())
					return false; // no path found and empty queue
			}

		}
		return true;

	}

	boolean isBarrier(List<Integer> position, int direction, List<Barrier> listBarriers) {
		// direction = where we want to go, 0 up, 1 right, 2 down, 3 left
		int x = position.get(0);
		int y = position.get(1);

		// don't mind this list, helped me factorize if test below
		int[][] list = { { x, x + 1, y, y, 1 }, { x + 1, x + 1, y, y + 1, 0 }, { x, x + 1, y + 1, y + 1, 1 },
				{ x, x, y, y + 1, 0 } };
		for (Barrier f : listBarriers) {
			if ((f.getX() == list[direction][0] || f.getX() == list[direction][1])
					&& (f.getY() == list[direction][2] || f.getY() == list[direction][3])
					&& f.getDirection() == list[direction][4])
				return true;
		}
		return false;

	}

	boolean isValidMove(List<Integer> position, List<Integer> adjPosition, int direction, boolean[][] visited,
			List<Barrier> listBarriers) {
		// return true if adjPosition on board and no barrier between adjPosition and
		// position
		return (adjPosition.get(0) >= 0) && (adjPosition.get(0) < 9) && (adjPosition.get(1) >= 0)
				&& (adjPosition.get(1) < 9) && !visited[adjPosition.get(0)][adjPosition.get(1)]
				&& !isBarrier(position, direction, listBarriers);
	}

	public static void main(String[] args) {
		// Test fence placement validity, can be deleted later

		// define variable
		List<List<Integer>> playersPositions = new ArrayList<>();
		playersPositions.add(Arrays.asList(0, 0));
		playersPositions.add(Arrays.asList(8, 8));
		playersPositions.add(Arrays.asList(0, 8));
		playersPositions.add(Arrays.asList(8, 0));

		List<Barrier> fences = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			Barrier f1 = new Barrier(2 * i + 1, 1, 1);
			fences.add(f1);
		}
		Barrier f2 = new Barrier(8, 2, 0);
		fences.add(f2);

		/*
		 * some test residue Barrier verifier = new Barrier(8, 4, 1); Barrier verif =
		 * new Barrier(8, 4, 1); boolean[][] visite = new boolean[9][9];
		 * 
		 * boolean isValid = verifier.isBarrierPlacementValid(playersPositions, fences);
		 * boolean isValide = verif.isValidMove(Arrays.asList(8, 2), Arrays.asList(7,
		 * 2), 3, visite, fences); boolean isV = verif.isBarrier(Arrays.asList(0,0), 2,
		 * fences); System.out.println(isValid);
		 */
	}

}
