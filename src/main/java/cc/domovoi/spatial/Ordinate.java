package cc.domovoi.spatial;

import java.util.List;

public class Ordinate extends AbstractOrdinate<Point> {

    public Ordinate(OType oType, List<Point> pointList) {
        super(oType, pointList);
    }

    @Override
    public String toString() {
        return "Ordinate{" +
                "oType=" + oType +
                ", pointList=" + pointList +
                '}';
    }
}
