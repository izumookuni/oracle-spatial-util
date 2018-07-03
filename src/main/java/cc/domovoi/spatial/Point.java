package cc.domovoi.spatial;

public class Point implements PointLike {

    private final double x;

    private final double y;

    private final double z;

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public double xValue() {
        return x;
    }

    @Override
    public double yValue() {
        return y;
    }

    @Override
    public double zValue() {
        return z;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
