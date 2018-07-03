package cc.domovoi.spatial;

public class LLPoint extends Point {

    public LLPoint(double longitude, double latitude) {
        super(longitude, latitude, 0.0);
    }

    @Override
    public String toString() {
        return "LLPoint{" +
                "longitude=" + this.xValue() +
                ", latitude=" + this.yValue() +
                '}';
    }
}
