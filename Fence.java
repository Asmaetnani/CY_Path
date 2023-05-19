package Abstraction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Fence {

	int x, y, direction;

	// x = row, between 0 and 8
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	// x = column, between 0 and 8
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	// direction = 0 fence vertical, direction = 1 fence horizontal
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	boolean isFencePlacementValid(Fence f, List<List<Integer>> playersPosition, List<Fence> listFences) {
		// out of board
		if ((f.getX() < 0 || f.getX() > 9) || (f.getY() < 0 || f.getY() > 9))
			return false;

		// half in the board
		else if ((f.getX() == 0 || f.getX() == 9) && f.getDirection() == 1)
			return false;
		else if ((f.getY() == 0 || f.getY() == 9) && f.getDirection() == 0)
			return false;

		// look for fence in the same place or just next to in the same direction
		for (Fence fence : listFences) {
			int fenceX = fence.getX();
			int fenceY = fence.getY();
			int fenceDirection = fence.getDirection();

			if (fenceX == f.getX() && fenceY == f.getY()) // same place
				return false;
			if (f.getDirection() == fenceDirection) { // next to in the same direction
				if (f.getDirection() == 0 && (f.getX() == fenceX + 1 || f.getX() == fenceX - 1) && f.getY() == fenceY)
					return false;
				else if (f.getDirection() == 1 && (f.getY() == fenceY + 1 || f.getY() == fenceY - 1)
						&& f.getX() == fenceX)
					return false;
			}
		}

		if (!pathFinding(f, playersPosition, listFences))
			return false;
		return true;
	}

	private boolean pathFinding(Fence f, List<List<Integer>> playersPositions, List<Fence> listFences) {
		// BFS sur le principe
		// add new fence to see if it still allow a way out
		listFences.add(f);
		// help define adjacents positions
		int[] rowAdj = { 0, 1, 0, -1 };
		int[] colAdj = { 1, 0, -1, 0 };

		for (int i = 0; i < playersPositions.size(); i++) {
			// initialize for a random player
			boolean[][] visited = new boolean[9][9];
			Queue<List<Integer>> queue = new LinkedList<>();

			visited[playersPositions.get(i).get(0)][playersPositions.get(i).get(1)] = true;
			queue.add(playersPositions.get(i));

			while (!queue.isEmpty()) {
				// actualize the point where we are
				List<Integer> currentPosition = queue.poll();

				// check if we reach the opposite side, depending on which player you are
				if ((i == 0 && currentPosition.get(1) == 8) || i == 1 && currentPosition.get(1) == 0)
					return true;

				// look which neighbor can be reach
				for (int k = 0; k < 4; k++) {
					List<Integer> adjPosition = new ArrayList<>();
					adjPosition.add(currentPosition.get(0) + rowAdj[k]);
					adjPosition.add(currentPosition.get(1) + colAdj[k]);
					if (isValidMove(adjPosition, i, visited, listFences)) {
						visited[currentPosition.get(0) + rowAdj[k]][currentPosition.get(1) + colAdj[k]] = true;
						queue.add(adjPosition);
					}
				}
				// to not come back in the same position
				visited[currentPosition.get(0)][currentPosition.get(1)] = true;
			}
		}
		return false; // no path and empty queue
	}

	boolean isFence(List<Integer> position, int direction, List<Fence> listFences) {
		// direction = where we want to go, 0 up, 1 right, 2 down, 3 left
		int x = position.get(0);
		int y = position.get(1);

		// Version alternative
		int[][] list = { { x, x + 1, y , y , 1 }, { x + 1, x + 1, y, y + 1, 0 }, { x, x + 1, y + 1, y + 1, 1 },
				{ x , x , y, y + 1, 0 } };
		for (Fence f : listFences) {
			if ((f.getX() == list[direction][0] || f.getX() == list[direction][1] || f.getY() == list[direction][2]
					|| f.getY() == list[direction][3]) && f.getDirection() == list[direction][4])
				return true;
		}
		return false;

	}

	boolean isValidMove(List<Integer> position, int direction, boolean[][] visited, List<Fence> listFences) {
		return (position.get(0) >= 0) && (position.get(0) < 9) && (position.get(1) >= 0) && (position.get(1) < 9)
				&& !visited[position.get(0)][position.get(1)] && !isFence(position, direction, listFences);
	}

	public static void main(String[] args) {
		Fence verifier = new Fence();
		//Fence verif = new Fence();
		// Test fence placement validity
		List<List<Integer>> playersPositions = new ArrayList<>();
		playersPositions.add(Arrays.asList(0, 0));
		playersPositions.add(Arrays.asList(8, 8));

		List<Fence> fences = new ArrayList<>();
		Fence f = new Fence();
		f.setDirection(1);
		f.setX(8);
		f.setY(3);

		for (int i = 0; i < 4; i++) {
			Fence f1 = new Fence();
			f1.setX(2 * i + 1);
			f1.setY(1);
			f1.setDirection(1);
			fences.add(f1);
		}
		Fence f2 = new Fence();
		f2.setX(7);
		f2.setY(2);
		f2.setDirection(0);
		fences.add(f2);
		boolean isValid = verifier.isFencePlacementValid(f, playersPositions, fences);
		//boolean isValid = verif.isFence(Arrays.asList(0, 0), 2, fences);
		System.out.println(isValid);
	}

}
