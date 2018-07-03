package cc.domovoi.spatial;

import java.util.List;

public class LLOrdinate extends AbstractOrdinate<LLPoint> {

    public LLOrdinate(OType oType, List<LLPoint> pointList) {
        super(oType, pointList);
    }

    @Override
    public String toString() {
        return "LLOrdinate{" +
                "oType=" + oType +
                ", pointList=" + pointList +
                '}';
    }
}
