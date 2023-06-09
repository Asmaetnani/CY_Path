package Abstraction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

//import Control.GameController;

public class Barrier implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Coordinate coordinate;
	public Direction direction;

	public enum Direction {
		VERTICAL, HORIZONTAL
	}

	/*
	 * the barrier are made to be set on junction of the line, while the pawn are on
	 * plain square
	 */

	public Barrier(Coordinate coordinate, Direction direction) {
		this.coordinate = coordinate;
		this.direction = direction;
		// direction = 0 vertical barrier, direction = 1 horizontal barrier
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	boolean isBarrierPlacementValid(List<Coordinate> playersPosition, List<Barrier> listBarriers) {
		// out of board
		if ((this.coordinate.getPositionX() < 0 || this.coordinate.getPositionX() > 9)
				|| (this.coordinate.getPositionY() < 0 || this.coordinate.getPositionY() > 9))
			return false;

		// half in the board
		else if ((this.coordinate.getPositionX() == 0 || this.coordinate.getPositionX() == 9)
				&& this.getDirection() == Direction.HORIZONTAL)
			return false;
		else if ((this.coordinate.getPositionY() == 0 || this.coordinate.getPositionY() == 9)
				&& this.getDirection() == Direction.VERTICAL)
			return false;

		// look for barrier in the same place or just next to in the same direction
		for (Barrier fence : listBarriers) {
			int fenceX = fence.coordinate.getPositionX();
			int fenceY = fence.coordinate.getPositionY();
			Direction fenceDirection = fence.getDirection();

			if (fenceX == this.coordinate.getPositionX() && fenceY == this.coordinate.getPositionY()) // same place
				return false;
			if (this.getDirection() == fenceDirection) { // next to in the same direction
				if (this.getDirection() == Direction.VERTICAL
						&& (this.coordinate.getPositionX() == fenceX + 1
								|| this.coordinate.getPositionX() == fenceX - 1)
						&& this.coordinate.getPositionY() == fenceY)
					return false;
				else if (this.getDirection() == Direction.HORIZONTAL
						&& (this.coordinate.getPositionY() == fenceY + 1
								|| this.coordinate.getPositionY() == fenceY - 1)
						&& this.coordinate.getPositionX() == fenceX)
					return false;
			}
		}

		if (!pathFinding(playersPosition, listBarriers))
			return false;
		return true;
	}

	private boolean pathFinding(List<Coordinate> playersPositions, List<Barrier> listBarriers) {
		// principle of BFS
		// add new barrier to see if it still allow a way out
		List<Barrier> lb = new ArrayList<>(listBarriers);

		lb.add(this);
		// help define adjacent square
		int[] rowAdj = { 0, 1, 0, -1 };
		int[] colAdj = { -1, 0, 1, 0 };

		for (int i = 0; i < playersPositions.size(); i++) {
			// initialize for a random player
			boolean[][] visited = new boolean[9][9];
			Queue<Coordinate> queue = new LinkedList<>();

			visited[playersPositions.get(i).getPositionX()][playersPositions.get(i).getPositionY()] = true;
			queue.add(playersPositions.get(i));

			while (!queue.isEmpty()) {
				// actualize the point where we are
				Coordinate currentPosition = queue.poll();
				// check if we reach the opposite side, depending on which player you are
				if ((i == 0 && currentPosition.getPositionY() == 8) || (i == 1 && currentPosition.getPositionY() == 0)
						|| (i == 2 && currentPosition.getPositionX() == 8)
						|| (i == 3 && currentPosition.getPositionX() == 8))
					// good for this player, we look for other player next
					break;

				// look which adjacent square can be reach
				for (int k = 0; k < 4; k++) {
					// Define the next position to look for
					Coordinate adjPosition = new Coordinate((currentPosition.getPositionX() + rowAdj[k]),
							(currentPosition.getPositionY() + colAdj[k]));

					if (isValidMove(currentPosition, adjPosition, visited, lb)) {
						visited[adjPosition.getPositionX()][adjPosition.getPositionY()] = true;
						queue.add(adjPosition);
					}
				}
				if (queue.isEmpty())
					return false; // no path found and empty queue
			}

		}
		return true;

	}

	public static boolean isBarrier(Coordinate position, Coordinate adjPosition, List<Barrier> listBarriers) {
		// direction = where we want to go, 0 up, 1 right, 2 down, 3 left
		int x = position.getPositionX();
		int y = position.getPositionY();
		int direction = -1;
		if (x == adjPosition.getPositionX()) {
			if (y == adjPosition.getPositionY() - 1)
				direction = 0;
			else
				direction = 2;
		} else {
			if (x == adjPosition.getPositionX() - 1)
				direction = 3;
			else
				direction = 1;
		}

		// don't mind this list, helped me factorize if test below
		Object[][] list = { { x, x + 1, y, y, Direction.VERTICAL }, { x + 1, x + 1, y, y + 1, Direction.HORIZONTAL },
				{ x, x + 1, y + 1, y + 1, Direction.VERTICAL }, { x, x, y, y + 1, Direction.HORIZONTAL } };

		for (Barrier b : listBarriers) {
			if ((b.coordinate.getPositionX() == (int) list[direction][0]
					|| b.coordinate.getPositionX() == (int) list[direction][1])
					&& (b.coordinate.getPositionY() == (int) list[direction][2]
							|| b.coordinate.getPositionY() == (int) list[direction][3])
					&& b.getDirection() == list[direction][4]) {
				return true;
			}
		}
		return false;

	}

	public static boolean isValidMove(Coordinate position, Coordinate adjPosition, boolean[][] visited,
			List<Barrier> listBarriers) {
		// return true if adjPosition on board and no barrier between adjPosition and
		// position
		return (adjPosition.getPositionX() >= 0) && (adjPosition.getPositionX() < 9)
				&& (adjPosition.getPositionY() >= 0) && (adjPosition.getPositionY() < 9)
				&& !visited[adjPosition.getPositionX()][adjPosition.getPositionY()]
				&& !isBarrier(position, adjPosition, listBarriers);
	}

	public static void main(String[] args) {
		// Test fence placement validity, can be deleted later

		// define variable
		List<Coordinate> playersPositions = new ArrayList<>();
		Coordinate p1 = new Coordinate(0, 0);
		playersPositions.add(p1);
		Coordinate p2 = new Coordinate(8, 8);
		playersPositions.add(p2);
		Coordinate p3 = new Coordinate(0, 8);
		Coordinate p4 = new Coordinate(8, 0);
		playersPositions.add(p3);
		playersPositions.add(p4);

		List<Barrier> fences = new ArrayList<>();

		for (int i = 0; i < 4; i++) {
			Coordinate c1 = new Coordinate(2 * i + 1, 1);
			Barrier f1 = new Barrier(c1, Direction.HORIZONTAL);
			fences.add(f1);
		}
		Coordinate c2 = new Coordinate(8, 2);
		Barrier f2 = new Barrier(c2, Direction.VERTICAL);
		fences.add(f2);

		/*
		 * //some test residue Coordinate c3 = new Coordinate(8, 3); Barrier verifier =
		 * new Barrier(c3, 1); boolean isValid =
		 * verifier.isBarrierPlacementValid(playersPositions, fences);
		 * System.out.println(isValid);
		 */

	}

}
