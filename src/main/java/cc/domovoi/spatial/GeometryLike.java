package cc.domovoi.spatial;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GeometryLike extends Cloneable, Serializable {

    GType gType();

    Srid srid();

    Optional<PointLike> point();

    List<ElemInfoLike> elemInfoList();

    List<OrdinateLike<? extends PointLike>> ordinateList();

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
