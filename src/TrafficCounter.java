import java.awt.*;

public class TrafficCounter {
    private Point coordinate;
    private Integer count;

    public void TrafficCounter(Point coordinate, Integer count){
        this.coordinate = coordinate;
        this.count = count;
    }

    public Point getCoordinate() {
        return coordinate;
    }

    public Integer getCount() {
        return count;
    }
}
