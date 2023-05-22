package Abstraction;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Barrier {

	int direction;
	Coordinates coordinates;

	/*
	 * the barrier are made to be set on junction of the line, while the pawn are on
	 * plain square
	 */

	public Barrier(Coordinates coordinates, int direction) {
		this.coordinates = coordinates;
		this.direction = direction;
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
	}

	// direction = 0 vertical barrier, direction = 1 horizontal barrier
	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	boolean isBarrierPlacementValid(List<Coordinates> playersPosition, List<Barrier> listBarriers) {
		// out of board
		if ((this.coordinates.getX() < 0 || this.coordinates.getX() > 9)
				|| (this.coordinates.getY() < 0 || this.coordinates.getY() > 9))
			return false;

		// half in the board
		else if ((this.coordinates.getX() == 0 || this.coordinates.getX() == 9) && this.getDirection() == 1)
			return false;
		else if ((this.coordinates.getY() == 0 || this.coordinates.getY() == 9) && this.getDirection() == 0)
			return false;

		// look for barrier in the same place or just next to in the same direction
		for (Barrier fence : listBarriers) {
			int fenceX = fence.coordinates.getX();
			int fenceY = fence.coordinates.getY();
			int fenceDirection = fence.getDirection();

			if (fenceX == this.coordinates.getX() && fenceY == this.coordinates.getY()) // same place
				return false;
			if (this.getDirection() == fenceDirection) { // next to in the same direction
				if (this.getDirection() == 0
						&& (this.coordinates.getX() == fenceX + 1 || this.coordinates.getX() == fenceX - 1)
						&& this.coordinates.getY() == fenceY)
					return false;
				else if (this.getDirection() == 1
						&& (this.coordinates.getY() == fenceY + 1 || this.coordinates.getY() == fenceY - 1)
						&& this.coordinates.getX() == fenceX)
					return false;
			}
		}

		if (!pathFinding(playersPosition, listBarriers))
			return false;
		return true;
	}

	private boolean pathFinding(List<Coordinates> playersPositions, List<Barrier> listBarriers) {
		// principle of BFS
		// add new barrier to see if it still allow a way out
		listBarriers.add(this);
		// help define adjacent square
		int[] rowAdj = { 0, 1, 0, -1 };
		int[] colAdj = { -1, 0, 1, 0 };

		for (int i = 0; i < playersPositions.size(); i++) {
			// initialize for a random player
			boolean[][] visited = new boolean[9][9];
			Queue<Coordinates> queue = new LinkedList<>();

			visited[playersPositions.get(i).getX()][playersPositions.get(i).getY()] = true;
			queue.add(playersPositions.get(i));

			while (!queue.isEmpty()) {
				// actualize the point where we are
				Coordinates currentPosition = queue.poll();
				// check if we reach the opposite side, depending on which player you are
				if ((i == 0 && currentPosition.getY() == 8) || (i == 1 && currentPosition.getY() == 0)
						|| (i == 2 && currentPosition.getX() == 8) || (i == 3 && currentPosition.getX() == 8))
					// good for this player, we look for other player next
					break;

				// look which adjacent square can be reach
				for (int k = 0; k < 4; k++) {
					// Define the next position to look for
					Coordinates adjPosition = new Coordinates((currentPosition.getX() + rowAdj[k]),
							(currentPosition.getY() + colAdj[k]));

					if (isValidMove(currentPosition, adjPosition, k, visited, listBarriers)) {
						visited[adjPosition.getX()][adjPosition.getY()] = true;
						queue.add(adjPosition);
					}
				}
				if (queue.isEmpty())
					listBarriers.remove(listBarriers.size() - 1); // remove the new barrier we added
				return false; // no path found and empty queue
			}

		}
		return true;

	}

	boolean isBarrier(Coordinates position, int direction, List<Barrier> listBarriers) {
		// direction = where we want to go, 0 up, 1 right, 2 down, 3 left
		int x = position.getX();
		int y = position.getY();

		// don't mind this list, helped me factorize if test below
		int[][] list = { { x, x + 1, y, y, 1 }, { x + 1, x + 1, y, y + 1, 0 }, { x, x + 1, y + 1, y + 1, 1 },
				{ x, x, y, y + 1, 0 } };
		for (Barrier f : listBarriers) {
			if ((f.coordinates.getX() == list[direction][0] || f.coordinates.getX() == list[direction][1])
					&& (f.coordinates.getY() == list[direction][2] || f.coordinates.getY() == list[direction][3])
					&& f.getDirection() == list[direction][4])
				return true;
		}
		return false;

	}

	boolean isValidMove(Coordinates position, Coordinates adjPosition, int direction, boolean[][] visited,
			List<Barrier> listBarriers) {
		// return true if adjPosition on board and no barrier between adjPosition and
		// position
		return (adjPosition.getX() >= 0) && (adjPosition.getX() < 9) && (adjPosition.getY() >= 0)
				&& (adjPosition.getY() < 9) && !visited[adjPosition.getX()][adjPosition.getY()]
				&& !isBarrier(position, direction, listBarriers);
	}

	public static void main(String[] args) {
		// Test fence placement validity, can be deleted later

		// define variable
		List<Coordinates> playersPositions = new ArrayList<>();
		Coordinates p1 = new Coordinates(0, 0);
		playersPositions.add(p1);
		Coordinates p2 = new Coordinates(8, 8);
		playersPositions.add(p2);
		Coordinates p3 = new Coordinates(0, 8);
		Coordinates p4 = new Coordinates(8, 0);
		playersPositions.add(p3);
		playersPositions.add(p4);

		List<Barrier> fences = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			Coordinates c1 = new Coordinates(2 * i + 1, 1);
			Barrier f1 = new Barrier(c1, 1);
			fences.add(f1);
		}
		Coordinates c2 = new Coordinates(8, 2);
		Barrier f2 = new Barrier(c2, 0);
		fences.add(f2);

		/*
		 * some test residue Barrier verifier = new Barrier(8, 4, 1); Barrier verif =
		 * new Barrier(8, 3, 1); boolean[][] visite = new boolean[9][9];
		 * 
		 * boolean isValid = verif.isBarrierPlacementValid(playersPositions, fences);
		 * boolean isValide = verif.isValidMove((Coordinates) Arrays.asList(8, 2),
		 * (Coordinates) Arrays.asList(7,2), 3, visite, fences); boolean isV =
		 * verif.isBarrier(Arrays.asList(0,0), 2, fences); System.out.println(isValid);
		 */

	}

}
