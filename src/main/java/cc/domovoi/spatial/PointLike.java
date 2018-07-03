package cc.domovoi.spatial;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public interface PointLike extends Cloneable, Serializable {

    double xValue();

    double yValue();

    double zValue();

    default List<Double> rawPointList() {
        return Arrays.asList(xValue(), yValue(), zValue());
    }

    default double[] rawPoints() {
        return new double[] {xValue(), yValue(), zValue()};
    }

}
