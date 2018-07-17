package cc.domovoi.spatial.test;

import cc.domovoi.spatial.*;
import oracle.spatial.geometry.JGeometry;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GeometryTest {

    Logger logger = LoggerFactory.getLogger(GeometryTest.class);

    @Test
    public void testGeometry() {
        GType gType = GType.LINE_OR_CURVE_2D;
        Srid srid = Srid.GCS_WGS_1984;
        ElemInfo elemInfo = ElemInfos.line2D(1);
        double[] ordinate = new double[] {108.1, 22.1, 108.2, 22.2, 108.5, 22.3};
        JGeometry jGeometry = new JGeometry(gType.gTypeValue(), srid.id, new int[]{elemInfo.startingOffset(), elemInfo.eTypeValue(), elemInfo.interpretation()}, ordinate);
        logger.info(jGeometry.toStringFull());
//        EJGeometry ejGeometry = EJGeometry.from(jGeometry);
//        logger.info(ejGeometry.toStringFull());
        JGeometry[] jGeometries = jGeometry.getElements();
        Stream.of(jGeometries).forEach(jGeometry1 -> logger.info(jGeometry1.toStringFull()));
    }

    @Test
    public void testGeometry2() {
        GType gType = GType.MULTI_LINE_OR_CURVE_2D;
        Srid srid = Srid.GCS_WGS_1984;
//        ElemInfo elemInfo1 = ElemInfos.compoundLine(2);
        ElemInfo elemInfo2 = ElemInfos.line2D(1);
        ElemInfo elemInfo3 = ElemInfos.line2D(7);
        double[] ordinates = new double[]{108.1, 22.1, 108.2, 22.2, 108.3, 22.5, 109.1, 23.4, 109.3, 23.6, 108.7, 23.9};
//        logger.info(String.valueOf(gType.gTypeValue()));
        JGeometry jGeometry = new JGeometry(gType.gTypeValue(), srid.id, new int[]{elemInfo2.startingOffset(), elemInfo2.eTypeValue(), elemInfo2.interpretation(), elemInfo3.startingOffset(), elemInfo3.eTypeValue(), elemInfo3.interpretation()}, ordinates);
        logger.info(jGeometry.toStringFull());
        JGeometry[] jGeometries = jGeometry.getElements();
//        logger.info(String.valueOf(jGeometries.length));
        Stream.of(jGeometries).forEach(jGeometry1 -> logger.info(jGeometry1.toStringFull()));
        EJGeometry ejGeometry = EJGeometry.from(jGeometry);
        ejGeometry.ordinateList().forEach(ordinate -> logger.info(ordinate.toString()));
    }

    @Test
    public void testGeometry3() {
//        GType gType = GType.MULTI_POINT_2D;
        Srid srid = Srid.GCS_WGS_1984;
        EJGeometry jGeometry = EJGeometry.from(new JGeometry(108.2, 22.4, srid.id));

        logger.info(jGeometry.toStringFull());
    }

    @Test
    public void testGeometry4() {
        GType gType = GType.MULTI_POINT_2D;
        Srid srid = Srid.GCS_WGS_1984;
        ElemInfo elemInfo1 = ElemInfos.multiPoint2D(1, 3);
        double[] ordinates = new double[] {108.1, 22.1, 108.2, 22.2, 108.3, 22.5};
        EJGeometry geometry = EJGeometry.of(gType, srid, Collections.emptyList(), Collections.singletonList(elemInfo1), DoubleStream.of(ordinates).boxed().collect(Collectors.toList()));
        logger.info(geometry.toStringFull());
        EJGeometry[] geometries = geometry.getElements();
        Stream.of(geometries).forEach(jGeometry1 -> logger.info(jGeometry1.toStringFull()));
    }

    @Test
    public void testGeometry5() {
        GType gType = GType.COLLECTION_2D;
        Srid srid = Srid.GCS_WGS_1984;
        ElemInfo elemInfo1 = ElemInfos.multiPoint2D(1, 3);
        ElemInfo elemInfo2 = ElemInfos.line2D(7);
        ElemInfo elemInfo3 = ElemInfos.line2D(13);
        ElemInfo elemInfo4 = ElemInfos.linePolygon2D(17);
        EJGeometry geometry = EJGeometry.of(gType, srid, Arrays.asList(elemInfo1, elemInfo2, elemInfo3, elemInfo4), IntStream.rangeClosed(1, 26).asDoubleStream().boxed().collect(Collectors.toList()));
        logger.info(geometry.toStringFull());
        EJGeometry[] geometries = geometry.getElements();
        Stream.of(geometries).forEach(jGeometry1 -> {
            logger.info(jGeometry1.toStringFull());
            logger.info(jGeometry1.ordinateList().toString());
            logger.info(jGeometry1.pointValueList().toString());
        });
    }

}
