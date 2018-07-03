package cc.domovoi.spatial;

public enum EType {

    POINT(1),
    LINE(2),
    OUTER_POLYGON(1003),
    INNER_POLYGON(2003),
    COMPOUND_LINE(4),
    COMPOUND_OUTER_POLYGON(1005),
    COMPOUND_INNER_POLYGON(2005),
    ;

    public final int eType;

    EType(int eType) {
        this.eType = eType;
    }
}
