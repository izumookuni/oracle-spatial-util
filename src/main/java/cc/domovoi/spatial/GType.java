package cc.domovoi.spatial;

public enum GType {

    POINT_2D(2, 0, 1),
    LINE_OR_CURVE_2D(2, 0, 2),
    POLYGON_2D(2, 0, 3),
    COLLECTION_2D(2, 0, 4),
    MULTI_POINT_2D(2, 0, 5),
    MULTI_LINE_OR_CURVE_2D(2, 0, 6),
    MULTI_POLYGON_2D(2, 0, 7),
    POINT_3D(3, 3, 1),
    LINE_OR_CURVE_3D(3, 3, 2),
    POLYGON_3D(3, 3, 3),
    COLLECTION_3D(3, 3, 4),
    MULTI_POINT_3D(3, 3, 5),
    MULTI_LINE_OR_CURVE_3D(3, 3, 6),
    MULTI_POLYGON_3D(3, 3, 7),
    ;

    public final int dimension;

    public final int lrs;

    public final int type;

    GType(int dimension, int lrs, int type) {
        this.dimension = dimension;
        this.lrs = lrs;
        this.type = type;
    }

    public int gTypeValue() {
        return dimension * 1000 + lrs * 100 + type;
    }
}
