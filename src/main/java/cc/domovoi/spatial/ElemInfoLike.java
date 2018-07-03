package cc.domovoi.spatial;

import java.io.Serializable;

public interface ElemInfoLike extends Cloneable, Serializable {

    int startingOffset();

    EType eType();

    int interpretation();

    default int eTypeValue() {
        return eType().eType;
    }

    default int[] rawElemInfo() {
        return new int[] {startingOffset(), eTypeValue(), interpretation()};
    }
}
