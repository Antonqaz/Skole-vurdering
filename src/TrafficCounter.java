import java.awt.geom.Point2D;

public class TrafficCounter {
    private Point2D coordinate;
    private Integer count;

    public TrafficCounter(Point2D coordinate, Integer count){
        this.coordinate = coordinate;
        this.count = count;
    }

    public Point2D getCoordinate() {
        return coordinate;
    }

    public Integer getCount() {
        return count;
    }
}
