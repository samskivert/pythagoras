//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Tests the various transform implementations.
 */
public class TransformTest
{
    @Test public void testTranslate () {
        for (Transform proto : createTransforms()) {
            if (proto.generality() < RigidTransform.GENERALITY) continue;
            for (Point trans : TRANS) {
                Transform t = proto.clone();
                t.setTranslation(trans.x, trans.y);
                for (Point point : POINTS) {
                    test(t, point, point.add(trans.x, trans.y));
                }
                for (Vector vec : VECTORS) {
                    test(t, vec, vec);
                }
            }
        }
    }

    @Test public void testRotate () {
        for (Transform proto : createTransforms()) {
            if (proto.generality() < RigidTransform.GENERALITY) continue;
            for (float angle : ANGLES) {
                Transform t = proto.clone();
                t.setRotation(angle);
                for (Point point : POINTS) {
                    test(t, point, point.rotate(angle));
                }
                for (Vector vector : VECTORS) {
                    test(t, vector, vector.rotate(angle));
                }
            }
        }
    }

    @Test public void testScale () {
        for (Transform proto : createTransforms()) {
            if (proto.generality() < UniformTransform.GENERALITY) continue;
            for (Point point : POINTS) {
                for (float scale : SCALES) {
                    Transform t = proto.clone();
                    t.setUniformScale(scale);
                    test(t, point, point.mult(scale));
                }
            }
        }
    }

    @Test public void testTranslateRotate () {
        for (Transform proto : createTransforms()) {
            if (proto.generality() < RigidTransform.GENERALITY) continue;
            for (Point trans : TRANS) {
                Transform t1 = proto.clone();
                t1.setTranslation(trans.x, trans.y);
                for (float angle : ANGLES) {
                    Transform t2 = proto.clone();
                    t2.setRotation(angle);

                    Transform t = t1.clone().rotate(angle);
                    Transform tpost = t2.concatenate(t1);
                    Transform tpre = t1.preConcatenate(t2);
                    for (Point point : POINTS) {
                        Point expect = point.add(trans.x, trans.y).rotateLocal(angle);
                        test(t, point, expect);
                        test(tpost, point, expect);
                        test(tpre, point, expect);
                    }
                    for (Vector vector : VECTORS) {
                        Vector expect = vector.rotate(angle);
                        test(t, vector, expect);
                        test(tpost, vector, expect);
                        test(tpre, vector, expect);
                    }
                }
            }
        }
    }

    @Test public void testRotateTranslate () {
        for (Transform proto : createTransforms()) {
            if (proto.generality() < RigidTransform.GENERALITY) continue;
            for (float angle : ANGLES) {
                Transform t1 = proto.clone();
                t1.setRotation(angle);
                for (Point trans : TRANS) {
                    Transform t2 = proto.clone();
                    t2.setTranslation(trans.x, trans.y);

                    // test that a single transform rotates then translates
                    Transform t = proto.clone();
                    t.setRotation(angle);
                    t.setTranslation(trans.x, trans.y);

                    // test explicitly via concatenation
                    Transform tpost = t2.concatenate(t1);
                    Transform tpre = t1.preConcatenate(t2);
                    for (Point point : POINTS) {
                        Point expect = point.rotate(angle).addLocal(trans.x, trans.y);
                        test(t, point, expect);
                        test(tpost, point, expect);
                        test(tpre, point, expect);
                    }
                    for (Vector vector : VECTORS) {
                        Vector expect = vector.rotate(angle);
                        test(t, vector, expect);
                        test(tpost, vector, expect);
                        test(tpre, vector, expect);
                    }
                }
            }
        }
    }

    @Test public void testTranslateScale () {
        for (Transform proto : createTransforms()) {
            if (proto.generality() < UniformTransform.GENERALITY) continue;
            for (Point trans : TRANS) {
                Transform t1 = proto.clone();
                t1.setTranslation(trans.x, trans.y);
                for (float scale : SCALES) {
                    Transform t2 = proto.clone();
                    t2.setUniformScale(scale);

                    Transform t = t1.clone().uniformScale(scale);
                    Transform tpost = t2.concatenate(t1);
                    Transform tpre = t1.preConcatenate(t2);
                    for (Point point : POINTS) {
                        Point expect = point.add(trans.x, trans.y).multLocal(scale);
                        test(t, point, expect);
                        test(tpost, point, expect);
                        test(tpre, point, expect);
                    }
                    for (Vector vector : VECTORS) {
                        Vector expect = vector.scale(scale);
                        test(t, vector, expect);
                        test(tpost, vector, expect);
                        test(tpre, vector, expect);
                    }
                }
            }
        }
    }

    @Test public void testScaleTranslate () {
        for (Transform proto : createTransforms()) {
            if (proto.generality() < UniformTransform.GENERALITY) continue;
            for (float scale : SCALES) {
                Transform t1 = proto.clone();
                t1.setUniformScale(scale);
                for (Point trans : TRANS) {
                    Transform t2 = proto.clone();
                    t2.setTranslation(trans.x, trans.y);

                    // test that a single transform scales then translates
                    Transform t = proto.clone();
                    t.setUniformScale(scale);
                    t.setTranslation(trans.x, trans.y);

                    // test explicitly via concatenation
                    Transform tpost = t2.concatenate(t1);
                    Transform tpre = t1.preConcatenate(t2);
                    for (Point point : POINTS) {
                        Point expect = point.mult(scale).addLocal(trans.x, trans.y);
                        test(t, point, expect);
                        test(tpost, point, expect);
                        test(tpre, point, expect);
                    }
                    for (Vector vector : VECTORS) {
                        Vector expect = vector.scale(scale);
                        test(t, vector, expect);
                        test(tpost, vector, expect);
                        test(tpre, vector, expect);
                    }
                }
            }
        }
    }

    @Test public void testRotateScale () {
        for (Transform proto : createTransforms()) {
            if (proto.generality() < UniformTransform.GENERALITY) continue;
            for (float angle : ANGLES) {
                Transform t1 = proto.clone();
                t1.setRotation(angle);
                for (float scale : SCALES) {
                    Transform t2 = proto.clone();
                    t2.setUniformScale(scale);

                    Transform t = t1.clone().uniformScale(scale);
                    Transform tpost = t2.concatenate(t1);
                    Transform tpre = t1.preConcatenate(t2);
                    for (Point point : POINTS) {
                        Point expect = point.rotate(angle).multLocal(scale);
                        test(t, point, expect);
                        test(tpost, point, expect);
                        test(tpre, point, expect);
                    }
                    for (Vector vector : VECTORS) {
                        Vector expect = vector.rotate(angle).scaleLocal(scale);
                        test(t, vector, expect);
                        test(tpost, vector, expect);
                        test(tpre, vector, expect);
                    }
                }
            }
        }
    }

    @Test public void testScaleRotate () {
        for (Transform proto : createTransforms()) {
            if (proto.generality() < UniformTransform.GENERALITY) continue;
            for (float scale : SCALES) {
                Transform t1 = proto.clone();
                t1.setUniformScale(scale);
                for (float angle : ANGLES) {
                    Transform t2 = proto.clone();
                    t2.setRotation(angle);

                    // test explicitly via concatenation
                    Transform tpost = t2.concatenate(t1);
                    Transform tpre = t1.preConcatenate(t2);
                    for (Point point : POINTS) {
                        Point expect = point.mult(scale).rotateLocal(angle);
                        test(tpost, point, expect);
                        test(tpre, point, expect);
                    }
                    for (Vector vector : VECTORS) {
                        Vector expect = vector.scale(scale).rotateLocal(angle);
                        test(tpost, vector, expect);
                        test(tpre, vector, expect);
                    }

                    // if we have an affine transform, we cannot set the scale and then set the
                    // rotation, because setting the rotation will first extract the scale and then
                    // reapply it, losing the sign of the scale in the process
                    if (proto.generality() >= AffineTransform.GENERALITY) continue;

                    // test that a single transform scales then rotates
                    Transform t = proto.clone();
                    t.setUniformScale(scale);
                    t.setRotation(angle);
                    for (Point point : POINTS) {
                        Point expect = point.mult(scale).rotateLocal(angle);
                        test(t, point, expect);
                    }
                    for (Vector vector : VECTORS) {
                        Vector expect = vector.scale(scale).rotateLocal(angle);
                        test(t, vector, expect);
                    }
                }
            }
        }
    }

    @Test public void testScaleRotateTranslate () {
        for (Transform proto : createTransforms()) {
            if (proto.generality() < UniformTransform.GENERALITY) continue;
            for (float scale : SCALES) {
                Transform t1 = proto.clone();
                t1.setUniformScale(scale);
                for (float angle : ANGLES) {
                    Transform t2 = proto.clone();
                    t2.setRotation(angle);
                    for (Point trans : TRANS) {
                        Transform t3 = proto.clone();
                        t3.setTranslation(trans.x, trans.y);

                        // test explicitly via concatenation
                        Transform tpost = t3.concatenate(t2).concatenate(t1);
                        Transform tpre = t1.preConcatenate(t2.preConcatenate(t3));
                        for (Point point : POINTS) {
                            Point expect = point.mult(scale).rotateLocal(angle).
                                addLocal(trans.x, trans.y);
                            test(tpost, point, expect);
                            test(tpre, point, expect);
                        }
                        for (Vector vector : VECTORS) {
                            Vector expect = vector.scale(scale).rotateLocal(angle);
                            test(tpost, vector, expect);
                            test(tpre, vector, expect);
                        }

                        // if we have an affine transform, we cannot set the scale and then set the
                        // rotation, because setting the rotation will first extract the scale and
                        // then reapply it, losing the sign of the scale in the process
                        if (proto.generality() >= AffineTransform.GENERALITY) continue;

                        // test that a single transform scales, rotates, then translates
                        Transform t = proto.clone();
                        t.setUniformScale(scale);
                        t.setRotation(angle);
                        t.setTranslation(trans.x, trans.y);
                        for (Point point : POINTS) {
                            Point expect = point.mult(scale).rotateLocal(angle).
                                addLocal(trans.x, trans.y);
                            test(t, point, expect);
                        }
                        for (Vector vector : VECTORS) {
                            Vector expect = vector.scale(scale).rotateLocal(angle);
                            test(t, vector, expect);
                        }
                    }
                }
            }
        }
    }

    protected void test (Transform t, Point p, Point expect) {
        Point orig = new Point(p);
        String desc = t + " @ " + p;

        // test single point transform and inverse transform
        Point tp = t.transform(p, new Point());
        Point itp = t.inverseTransform(tp, new Point());
        assertEquals(desc, orig, p);
        assertPointsEqual(desc, expect, tp);
        assertPointsEqual(desc, p, itp);

        // test multipoint transform
        Point[] ps = new Point[] { null, p, null };
        Point[] tps = new Point[] { null, new Point(), null };
        t.transform(ps, 1, tps, 1, 1);
        assertEquals(desc, orig, p);
        assertEquals(desc, null, tps[0]);
        assertPointsEqual(desc, expect, tps[1]);
        assertEquals(desc, null, tps[2]);
    }

    protected void assertPointsEqual (String desc, Point p1, Point p2) {
        if (Math.abs(p1.x - p2.x) > MathUtil.EPSILON ||
            Math.abs(p1.y - p2.y) > MathUtil.EPSILON) {
            fail(desc + " want " + p1 + " got " + p2);
        }
    }

    protected void test (Transform t, Vector v, Vector expect) {
        Vector orig = new Vector(v);
        String desc = t + " @ " + v;

        // test vector transform and inverse transform
        Vector tv = t.transform(v, new Vector());
        Vector itv = t.inverseTransform(tv, new Vector());
        assertEquals(desc, orig, v);
        assertVectorsEqual(desc, expect, tv);
        assertVectorsEqual(desc, v, itv);
    }

    protected void assertVectorsEqual (String desc, Vector v1, Vector v2) {
        if (Math.abs(v1.x - v2.x) > MathUtil.EPSILON ||
            Math.abs(v1.y - v2.y) > MathUtil.EPSILON) {
            fail(desc + " want " + v1 + " got " + v2);
        }
    }

    protected Transform[] createTransforms () {
        return new Transform[] {
            new IdentityTransform(),
            new RigidTransform(),
            new UniformTransform(),
            new NonUniformTransform(),
            new AffineTransform(),
        };
    }

    protected static final Point[] POINTS = {
        new Point(0, 0), new Point(FloatMath.TAU, FloatMath.E),
        new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1),
        new Point(1, 1), new Point(-1, 1), new Point(-1, -1), new Point(1, -1)
    };
    protected static final Vector[] VECTORS = {
        new Vector(0, 0), new Vector(FloatMath.TAU, FloatMath.E),
        new Vector(1, 0), new Vector(0, 1), new Vector(-1, 0), new Vector(0, -1),
        new Vector(1, 1), new Vector(-1, 1), new Vector(-1, -1), new Vector(1, -1)
    };
    protected static final float[] ANGLES = {
        0, FloatMath.PI/2, FloatMath.PI, FloatMath.PI*3/2,
        -FloatMath.PI/2, -FloatMath.PI, -FloatMath.PI*3/2
    };
    protected static final float[] SCALES = { 0.5f, 1, 1.5f, -0.5f, -1, -1.5f };

    protected static final float[] DXS = { -25, 0, 25 };
    protected static final float[] DYS = { -25, 0, 25 };
    protected static final Point[] TRANS = new Point[DXS.length * DYS.length];
    static {
        int ii = 0;
        for (float dx : DXS) {
            for (float dy : DYS) {
                TRANS[ii++] = new Point(dx, dy);
            }
        }
    }
}
