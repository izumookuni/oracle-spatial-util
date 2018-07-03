package cc.domovoi.spatial;

public class ElemInfo implements ElemInfoLike {

    protected final int startingOffset;

    protected final EType eType;

    protected final int interpretation;

    public ElemInfo(int startingOffset, EType eType, int interpretation) {
        this.startingOffset = startingOffset;
        this.eType = eType;
        this.interpretation = interpretation;
    }

    @Override
    public int startingOffset() {
        return this.startingOffset;
    }

    @Override
    public EType eType() {
        return this.eType;
    }

    @Override
    public int interpretation() {
        return this.interpretation;
    }

    @Override
    public String toString() {
        return "ElemInfo{" +
                "startingOffset=" + this.startingOffset +
                ", eType=" + this.eType +
                ", interpretation=" + this.interpretation +
                '}';
    }
}
