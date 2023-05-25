package cyPath;


import java.util.ArrayList;
import java.util.List;


public class OccupiedCoordinates {
	 private List<Coordinate> occupiedCoordinates;

	    public OccupiedCoordinates() {
	        occupiedCoordinates = new ArrayList<>();
	    }

	    public void addCoordinate(Coordinate coordinate) {
	        occupiedCoordinates.add(coordinate);
	    }

	    public void removeCoordinate(Coordinate coordinate) {
	        occupiedCoordinates.remove(coordinate);
	    }

	    public boolean isCoordinateOccupied(Coordinate coordinate) {
	        return occupiedCoordinates.contains(coordinate);
	    }
}
