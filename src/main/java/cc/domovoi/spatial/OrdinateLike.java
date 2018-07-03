package cc.domovoi.spatial;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public interface OrdinateLike<A extends PointLike> {

    OType oType_();

    List<A> pointList_();

    default List<Double> rawOrdinateList() {
        return pointList_().stream().flatMap(point -> point.rawPointList().stream()).collect(Collectors.toList());
    }

    default double[] rawOrdinates() {
        return pointList_().stream().flatMapToDouble(point -> DoubleStream.of(point.rawPoints())).toArray();
    }

}
