import java.awt.*;

public class School {
    private Point coordinate;
    private String name;

    public void School(Point coordinate, String name){
        this.coordinate = coordinate;
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public Point getCoordinate() {
        return coordinate;
    }
}
