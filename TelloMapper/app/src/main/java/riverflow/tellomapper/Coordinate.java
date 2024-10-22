package riverflow.tellomapper;

public class Coordinate {

    private float x;
    private float y;

    public Coordinate() {

    }

    // use this for duplicating coordinates
    public Coordinate(Coordinate coordinate) {
        x = coordinate.getX();
        y = coordinate.getY();
    }

    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
    
    public String toString() {
        return "(" + (int)x + ", " + (int)y + ")";
    }

    public String toReadable() {
        return x + "\t" + y + "\n";
    }

}
