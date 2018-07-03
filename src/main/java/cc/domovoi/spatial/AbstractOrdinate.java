package cc.domovoi.spatial;

import java.util.List;

public abstract class AbstractOrdinate<A extends PointLike> implements OrdinateLike<A> {

    protected final OType oType;

    protected final List<A> pointList;

    public AbstractOrdinate(OType oType, List<A> pointList) {
        this.oType = oType;
        this.pointList = pointList;
    }

    @Override
    public OType oType_() {
        return this.oType;
    }

    @Override
    public List<A> pointList_() {
        return this.pointList;
    }

}
