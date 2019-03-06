package riverflow.tellomapper;

import java.util.ArrayList;

public class Path {

    private ArrayList<Coordinate> coordinates;

    public Path () {

    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public void addCoordinate(Coordinate p) {
        coordinates.add(p);
    }
}
