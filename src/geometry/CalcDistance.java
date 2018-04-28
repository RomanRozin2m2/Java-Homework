package geometry;

import java.lang.Math;
import figures.Location;

public class CalcDistance {

    public double dist(Location point_1, Location point_2) {
        int firstX = point_1.getX();
        int firstY = point_1.getY();
        int secondX = point_2.getX();
        int secondY = point_2.getY();
        int xDist = firstX - secondX;
        int yDist = firstY - secondY;
        return Math.sqrt(xDist * xDist + yDist * yDist);
    }
}