package cc.domovoi.spatial;

import oracle.spatial.geometry.DataException;
import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EJGeometry extends JGeometry implements GeometryLike {

    /**
     * Create Geometry
     * @param gTypeValue gTypeValue
     * @param srid srid
     */
    public EJGeometry(int gTypeValue, int srid) {
        super(gTypeValue, srid);
    }

    /**
     * Create Geometry
     * @param gTypeValue gTypeValue
     * @param srid srid
     * @param x x
     * @param y y
     * @param z z
     * @param elemInfoArray elemInfoArray
     * @param ordinateArray ordinateArray
     */
    public EJGeometry(int gTypeValue, int srid, double x, double y, double z, int[] elemInfoArray, double[] ordinateArray) {
        super(gTypeValue, srid, x, y, z, elemInfoArray, ordinateArray);
    }

    /**
     * Create Geometry
     * @param gTypeValue gTypeValue
     * @param srid srid
     * @param elemInfoArray elemInfoArray
     * @param ordinateArray ordinateArray
     */
    public EJGeometry(int gTypeValue, int srid, int[] elemInfoArray, double[] ordinateArray) {
        super(gTypeValue, srid, elemInfoArray, ordinateArray);
    }

    /**
     * Create 2D point
     * @param x x
     * @param y y
     * @param srid srid
     */
    public EJGeometry(double x, double y, int srid) {
        super(x, y, srid);
    }

    /**
     * Create 3D point
     * @param x x
     * @param y y
     * @param z z
     * @param srid srid
     */
    public EJGeometry(double x, double y, double z, int srid) {
        super(x, y, z, srid);
    }


    /**
     * Create 2D rectangle
     * @param x1 x1
     * @param y1 y1
     * @param x2 x2
     * @param y2 y2
     * @param srid srid
     */
    public EJGeometry(double x1, double y1, double x2, double y2, int srid) {
        super(x1, y1, x2, y2, srid);
    }

    /**
     * Create from JGeometry
     * @param jGeometry jGeometry
     * @return EJGeometry
     */
    public static EJGeometry from(JGeometry jGeometry) {
        int gTypeValue = jGeometry.getDimensions() * 1000 + jGeometry.getLRMDimension() * 100 + jGeometry.getType();
        double[] xyz = jGeometry.getLabelPointXYZ();
        return new EJGeometry(gTypeValue, jGeometry.getSRID(), xyz[0], xyz[1], xyz[2], jGeometry.getElemInfo(), jGeometry.getOrdinatesArray());
    }

    /**
     * Create EJGeometry
     * @param gType gType
     * @param srid srid
     * @param pointValueList pointValueList
     * @param elemInfoList elemInfoList
     * @param ordinateValueList ordinateValueList
     * @return EJGeometry
     */
    public static EJGeometry of(GType gType, Srid srid, List<Double> pointValueList, List<ElemInfoLike> elemInfoList, List<Double> ordinateValueList) {
        int[] elemInfoValues = elemInfoList.stream().flatMapToInt(elemInfo -> IntStream.of(elemInfo.rawElemInfo())).toArray();
        double[] ordinateValues = ordinateValueList.stream().mapToDouble(Double::doubleValue).toArray();
        if (pointValueList.size() < 2) {
            return new EJGeometry(gType.gTypeValue(), srid.id, elemInfoValues, ordinateValues);
        }
        else if (pointValueList.size() < 3) {
            return new EJGeometry(gType.gTypeValue(), srid.id, pointValueList.get(0), pointValueList.get(1), 0.0, elemInfoValues, ordinateValues);
        }
        else {
            return new EJGeometry(gType.gTypeValue(), srid.id, pointValueList.get(0), pointValueList.get(1), pointValueList.get(2), elemInfoValues, ordinateValues);
        }
    }

    /**
     * Create EJGeometry
     * @param gType gType
     * @param srid srid
     * @param elemInfoList elemInfoList
     * @param ordinateValueList ordinateValueList
     * @return EJGeometry
     */
    public static EJGeometry of(GType gType, Srid srid, List<ElemInfoLike> elemInfoList, List<Double> ordinateValueList) {
        return EJGeometry.of(gType, srid, Collections.emptyList(), elemInfoList, ordinateValueList);
    }

    /**
     * Create EJGeometry
     * @param gType gType
     * @param srid srid
     * @param pointValueList pointValueList
     * @return EJGeometry
     */
    public static EJGeometry of(GType gType, Srid srid, List<Double> pointValueList) {
        return EJGeometry.of(gType, srid, pointValueList, Collections.emptyList(), Collections.emptyList());
    }

    /**
     * Create EJGeometry
     * @param gType gType
     * @param srid srid
     * @param elemInfo elemInfo
     * @param ordinateValueList ordinateValueList
     * @return EJGeometry
     */
    public static EJGeometry of(GType gType, Srid srid, ElemInfoLike elemInfo, List<Double> ordinateValueList) {
        return EJGeometry.of(gType, srid, Collections.singletonList(elemInfo), ordinateValueList);
    }

    @Override
    public GType gType() {
        return Arrays.stream(GType.values())
                .filter(gType -> gType.dimension == this.dim && gType.lrs == this.linfo && gType.type == this.gtype)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("GType undefined"));
    }

    @Override
    public Srid srid() {
        return Arrays.stream(Srid.values()).filter(srid1 -> srid1.id == this.srid).findFirst().orElseThrow(() -> new RuntimeException("Srid undefined"));
    }

    @Override
    public Optional<PointLike> point() {
        if (elemInfo == null || ordinates == null) {
            if (this.getDimensions() == 2) {
                return Optional.of(new cc.domovoi.spatial.LLPoint(this.x, this.y));
            }
            else {
                return Optional.of(new cc.domovoi.spatial.Point(this.x, this.y, this.z));
            }
        }
        else {
            return Optional.empty();
        }
    }

    @Override
    public List<ElemInfoLike> elemInfoList() {
        if (this.elemInfo == null) {
            return null;
        }
        List<Optional<ElemInfo>> elemInfoListOptional = new ArrayList<>();
        int size = this.elemInfo.length / 3;
        for (int i = 0; i < size; i += 1) {
            int offset = this.elemInfo[i * 3];
            int eTypeValue = this.elemInfo[i * 3 + 1];
            int interpretation = this.elemInfo[i * 3 + 2];
            elemInfoListOptional.add(ElemInfos.of(offset, eTypeValue, interpretation));
        }
        if (elemInfoListOptional.stream().allMatch(Optional::isPresent)) {
            return elemInfoListOptional.stream().map(Optional::get).collect(Collectors.toList());
        }
        else {
            throw new RuntimeException("EType undefined");
        }
    }

    @Override
    public boolean singleElement() {
        return this.elemInfo != null && this.elemInfo.length == 3;
    }

    @Override
    public List<OrdinateLike<? extends PointLike>> ordinateList() {
        if (this.elemInfoList() == null) {
            return this.getDimensions() == 2 ?
                    Collections.singletonList(new LLOrdinate(OType.POINT_2D, Collections.singletonList(new LLPoint(this.x, this.y)))) :
                    Collections.singletonList(new Ordinate(OType.POINT_3D, Collections.singletonList(new cc.domovoi.spatial.Point(this.x, this.y, this.z))));
        }
        if (this.singleElement()) {
            ElemInfoLike elemInfo = this.elemInfoList().get(0);
            OType oType = OType.of(elemInfo.eType(), elemInfo.eType() != EType.POINT ? elemInfo.interpretation() : 0, this.getDimensions()).orElseThrow(() -> new RuntimeException("OType undefined"));
            List<cc.domovoi.spatial.Point> pointList = new ArrayList<>();
            if (this.getDimensions() == 2) {
                int size = this.ordinates.length / 2;
                for (int i = 0; i < size; i += 1) {
                    cc.domovoi.spatial.Point point = new cc.domovoi.spatial.LLPoint(this.ordinates[i * 2], this.ordinates[i * 2 + 1]);
                    pointList.add(point);
                }
            }
            else if (this.getDimensions() == 3) {
                int size = this.ordinates.length / 3;
                for (int i = 0; i < size; i += 1) {
                    cc.domovoi.spatial.Point point = new cc.domovoi.spatial.Point(this.ordinates[i * 3], this.ordinates[i * 3 + 1], this.ordinates[i * 3 + 2]);
                    pointList.add(point);
                }
            }
            else {
                throw new RuntimeException("Dimensions undefined");
            }
            return Collections.singletonList(new Ordinate(oType, pointList));
        }
        else {
            return Stream.of(this.getElements()).flatMap(ejGeometry -> ejGeometry.ordinateList().stream()).collect(Collectors.toList());
        }
    }

    @Override
    public List<Double> pointValueList() {
        return point().map(p -> {
            if (this.getDimensions() == 2) {
                return Arrays.asList(p.xValue(), p.yValue());
            }
            else {
                return Arrays.asList(p.xValue(), p.yValue(), p.zValue());
            }
        }).orElse(Collections.emptyList());
    }

    @Override
    public List<Integer> elemInfoValueList() {
//        return elemInfoList().stream().flatMap(elemInfo -> Stream.of(elemInfo.startingOffset(), elemInfo.eTypeValue(), elemInfo.interpretation())).collect(Collectors.toList());
        return Arrays.stream(this.elemInfo).boxed().collect(Collectors.toList());
    }

    @Override
    public List<Double> ordinateValueList() {
        return Arrays.stream(this.ordinates).boxed().collect(Collectors.toList());
    }

    public static EJGeometry eJLoad(STRUCT var0) throws SQLException {
        JGeometry jGeometry = JGeometry.load(var0);
        return EJGeometry.from(jGeometry);
    }

    @Override
    public EJGeometry getElementAt(int i) {
        return EJGeometry.from(super.getElementAt(i));
    }

    @Override
    public EJGeometry[] getElements() {
        return Arrays.stream(super.getElements()).map(EJGeometry::from).toArray(EJGeometry[]::new);
    }

    @Override
    protected EJGeometry[] getElements(int i) {
        return Arrays.stream(super.getElements(i)).map(EJGeometry::from).toArray(EJGeometry[]::new);
    }

    @Override
    public EJGeometry densifyArcs(double v, boolean b) {
        return EJGeometry.from(super.densifyArcs(v, b));
    }

    @Override
    public EJGeometry simplify(double v, double v1, double v2) throws Exception, SQLException {
        return EJGeometry.from(super.simplify(v, v1, v2));
    }

    @Override
    public EJGeometry buffer(double v, double v1, double v2, double v3) throws Exception, SQLException {
        return EJGeometry.from(super.buffer(v, v1, v2, v3));
    }

    @Override
    public EJGeometry buffer(double v) throws Exception, SQLException, DataException {
        return EJGeometry.from(super.buffer(v));
    }

    @Override
    public EJGeometry affineTransforms(boolean b, double v, double v1, double v2, boolean b1, JGeometry jGeometry, double v3, double v4, double v5, boolean b2, JGeometry jGeometry1, JGeometry jGeometry2, double v6, int i, boolean b3, double v7, double v8, double v9, double v10, double v11, double v12, boolean b4, JGeometry jGeometry3, JGeometry jGeometry4, int i1, boolean b5, double[] doubles, double[] doubles1) throws Exception {
        return EJGeometry.from(super.affineTransforms(b, v, v1, v2, b1, jGeometry, v3, v4, v5, b2, jGeometry1, jGeometry2, v6, i, b3, v7, v8, v9, v10, v11, v12, b4, jGeometry3, jGeometry4, i1, b5, doubles, doubles1));
    }

    @Override
    public String toString() {
        return  "EJGeometry (gtype=" + this.gtype + ", dim=" + this.dim + ", srid=" + this.srid + ")";
    }

    @Override
    public String toStringFull() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EJGeometry (gtype=" + this.gtype + ", dim=" + this.dim + ", srid=" + this.srid);
        if (this.isOptimizedPoint()) {
            stringBuilder.append( ", Point=(");
            stringBuilder.append(this.x + "," + this.y);
            if (this.dim > 2) {
                stringBuilder.append("," + this.z);
            }

            stringBuilder.append("))");
            return stringBuilder.toString();
        } else {
            stringBuilder.append(",  \n ElemInfo(");

            int var2;
            for(var2 = 0; var2 < this.elemInfo.length - 1; ++var2) {
                stringBuilder.append(this.elemInfo[var2] + ",");
            }

            stringBuilder.append(this.elemInfo[this.elemInfo.length - 1] + ")");
            stringBuilder.append(",  \n Ordinates(");

            for(var2 = 0; var2 < this.ordinates.length / this.dim; ++var2) {
                for(int var3 = 0; var3 < this.dim; ++var3) {
                    stringBuilder.append(this.ordinates[var3 + var2 * this.dim]);
                    if (var3 < this.dim - 1) {
                        stringBuilder.append(",");
                    }
                }

                stringBuilder.append("\n");
            }

            stringBuilder.append("))");
            return stringBuilder.toString();
        }
    }
}
