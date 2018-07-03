package cc.domovoi.spatial;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ElemInfos {

    public static ElemInfo point2D(int offset) {
        return new ElemInfo(offset, EType.POINT, 1);
    }

    public static ElemInfo multiPoint2D(int offset, int size) {
        return new ElemInfo(offset, EType.POINT, size);
    }

    public static ElemInfo line2D(int offset) {
        return new ElemInfo(offset, EType.LINE, 1);
    }

    public static ElemInfo curve2D(int offset) {
        return new ElemInfo(offset, EType.LINE, 2);
    }

    public static ElemInfo linePolygon2D(int offset) {
        return new ElemInfo(offset, EType.OUTER_POLYGON, 1);
    }

    public static ElemInfo curvePolygon2D(int offset) {
        return new ElemInfo(offset, EType.OUTER_POLYGON, 2);
    }

    public static ElemInfo rectangle(int offset) {
        return new ElemInfo(offset, EType.OUTER_POLYGON, 3);
    }

    public static ElemInfo circle(int offset) {
        return new ElemInfo(offset, EType.OUTER_POLYGON, 4);
    }

    public static ElemInfo innerLinePolygon2D(int offset) {
        return new ElemInfo(offset, EType.INNER_POLYGON, 1);
    }

    public static ElemInfo innerCurvePolygon2D(int offset) {
        return new ElemInfo(offset, EType.INNER_POLYGON, 2);
    }

    public static ElemInfo innerRectangle(int offset) {
        return new ElemInfo(offset, EType.INNER_POLYGON, 3);
    }

    public static ElemInfo innerCircle(int offset) {
        return new ElemInfo(offset, EType.INNER_POLYGON, 4);
    }

    public static ElemInfo compoundLine(int size) {
        return new ElemInfo(1, EType.COMPOUND_LINE, size);
    }

    public static ElemInfo compoundPolygon(int size) {
        return new ElemInfo(1, EType.COMPOUND_OUTER_POLYGON, size);
    }

    public static ElemInfo compoundInnerPolygon(int size) {
        return new ElemInfo(1, EType.COMPOUND_INNER_POLYGON, size);
    }

    public static ElemInfo of(int offset, EType eType, int interpretation) {
        return new ElemInfo(offset, eType, interpretation);
    }

    public static Optional<ElemInfo> of(int offset, int eTypeValue, int interpretation) {
        return Arrays.stream(EType.values())
                .filter(eType -> eType.eType == eTypeValue)
                .findFirst()
                .map(eType -> ElemInfos.of(offset, eType, interpretation));
    }
}
