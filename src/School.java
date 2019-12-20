import java.awt.geom.Point2D;

public class School {
    private Point2D coordinate;
    private String name;

    public School(Point2D.Double coordinate, String name){
        this.coordinate = coordinate;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public Point2D getCoordinate() {
        return coordinate;
    }
}
