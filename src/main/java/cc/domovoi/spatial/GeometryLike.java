package cc.domovoi.spatial;

import java.io.Serializable;
import java.util.List;

public interface GeometryLike extends Cloneable, Serializable {

    GType gType();

    Srid srid();

    List<PointLike> pointList();

    List<ElemInfoLike> elemInfoList();

    List<OrdinateLike> ordinateList();

    default int gTypeValue() {
        return gType().gTypeValue();
    }

    default int sridValue() {
        return srid().id;
    }

    List<Double> pointValueList();

    List<Integer> elemInfoValueList();

    List<Double> ordinateValueList();

    default boolean singleElement() {
        return this.elemInfoList() != null && this.elemInfoList().size() == 1;
    }

}
