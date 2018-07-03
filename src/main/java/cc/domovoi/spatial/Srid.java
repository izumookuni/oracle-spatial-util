package cc.domovoi.spatial;

public enum Srid {
    GCS_WGS_1984(4326, "WGS 84"),
    LONGITUDE_LATITUDE(8307, "Longitude / Latitude (WGS 84)"),
    ;

    public final int id;

    public final String name;

    Srid(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
