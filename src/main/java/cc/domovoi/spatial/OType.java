package cc.domovoi.spatial;

import java.util.Optional;
import java.util.stream.Stream;

public enum OType {
    POINT_2D(EType.POINT, 0, 2),
    LINE_2D(EType.LINE, 1, 2),
    CURVE_2D(EType.LINE, 2, 2),
    POLYGON_2D(EType.OUTER_POLYGON, 1, 2),
    INNER_POLYGON_2D(EType.INNER_POLYGON, 1, 2),
    CURVE_POLYGON_2D(EType.OUTER_POLYGON, 2, 2),
    INNER_CURVE_POLYGON_2D(EType.INNER_POLYGON, 2, 2),
    RECTANGLE_2D(EType.OUTER_POLYGON, 3, 2),
    INNER_RECTANGLE_2D(EType.INNER_POLYGON, 3, 2),
    CIRCLE_2D(EType.OUTER_POLYGON, 4, 2),
    INNER_CIRCLE_2D(EType.INNER_POLYGON, 4, 2),

    POINT_3D(EType.POINT, 0, 3),
    LINE_3D(EType.LINE, 1, 3),
    CURVE_3D(EType.LINE, 2, 3),
    POLYGON_3D(EType.OUTER_POLYGON, 1, 3),
    INNER_POLYGON_3D(EType.INNER_POLYGON, 1, 3),
    CURVE_POLYGON_3D(EType.OUTER_POLYGON, 2, 3),
    INNER_CURVE_POLYGON_3D(EType.INNER_POLYGON, 2, 3),
    RECTANGLE_3D(EType.OUTER_POLYGON, 3, 3),
    INNER_RECTANGLE_3D(EType.INNER_POLYGON, 3, 3),
    CIRCLE_3D(EType.OUTER_POLYGON, 4, 3),
    INNER_CIRCLE_3D(EType.INNER_POLYGON, 4, 3),
    ;

    public final EType eType;

    public final int interpretation;

    public final int dimension;

    OType(EType eType, int interpretation, int dimension) {
        this.eType = eType;
        this.interpretation = interpretation;
        this.dimension = dimension;
    }

    public static Optional<OType> of(EType eType, int interpretation, int dimension) {
        return Stream.of(OType.values()).filter(oType -> oType.eType == eType && oType.interpretation == interpretation && oType.dimension == dimension).findFirst();
    }
}
