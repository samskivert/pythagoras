//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f

import java.awt.geom.{AffineTransform => JAffineTransform}

import scala.collection.mutable.ListBuffer

import org.junit._
import org.junit.Assert._

/**
 * Tests the various transform implementations.
 */
class TransformTest
{
  trait Action {
    def set (t :Transform)
    def add (t :Transform)
    def add (xf :JAffineTransform)
    def info :String
  }

  val ANGLES = Seq(
    0, FloatMath.PI/2, FloatMath.PI, FloatMath.PI*3/2,
    -FloatMath.PI/2, -FloatMath.PI, -FloatMath.PI*3/2
  ).map(angle => new Action() {
    def set (t :Transform) = t.setRotation(angle)
    def add (t :Transform) = t.rotate(angle)
    def add (xf :JAffineTransform) = xf.rotate(angle)
    def info = "r:" + (180*angle/math.Pi).toInt + "'"
  })

  val SCALES = Seq(0.5f, 1, 1.5f, -0.5f, -1, -1.5f).map(scale => new Action {
    def set (t :Transform) = t.setUniformScale(scale)
    def add (t :Transform) = t.uniformScale(scale)
    def add (xf :JAffineTransform) = xf.scale(scale, scale)
    def info = "s:" + scale
  })

  val DXS = Seq(-25, 0, 25)
  val DYS = Seq(-25, 0, 25)
  val TRANS = (for (dx <- DXS; dy <- DYS) yield new Point(dx, dy)).map(trans => new Action {
    def set (t :Transform) = t.setTranslation(trans.x, trans.y)
    def add (t :Transform) = t.translate(trans.x, trans.y)
    def add (xf :JAffineTransform) = xf.translate(trans.x, trans.y)
    def info = "t:" + trans
  })

  val XFORMS = Seq(new AffineTransform)

  val POINTS = Seq(
    new Point(0, 0), new Point(MathUtil.TAU, FloatMath.E),
    new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1),
    new Point(1, 1), new Point(-1, 1), new Point(-1, -1), new Point(1, -1)
  )
  val VECTORS = Seq(
    new Vector(0, 0), new Vector(MathUtil.TAU, FloatMath.E),
    new Vector(1, 0), new Vector(0, 1), new Vector(-1, 0), new Vector(0, -1),
    new Vector(1, 1), new Vector(-1, 1), new Vector(-1, -1), new Vector(1, -1)
  )

  @Test def testTranslate {
    test(List(TRANS))
  }

  @Test def testRotate () {
    test(List(ANGLES))
  }

  @Test def testScale () {
    test(List(SCALES))
  }

  @Test def testTranslateRotate () {
    test(List(TRANS, ANGLES))
  }

  @Test def testRotateTranslate () {
    test(List(ANGLES, TRANS))
  }

  @Test def testTranslateScale () {
    test(List(TRANS, SCALES))
  }

  @Test def testScaleTranslate () {
    test(List(SCALES, TRANS))
  }

  @Test def testRotateScale () {
    test(List(ANGLES, SCALES))
  }

  @Test def testScaleRotate () {
    test(List(SCALES, ANGLES))
  }

  @Test def testScaleRotateTranslate () {
    test(List(SCALES, ANGLES, TRANS))
  }

  def test (actionLists :List[Seq[Action]]) {
    def test (lists :List[Seq[Action]], toApply :List[Action]) {
      lists match {
        case Nil => runTest(toApply);
        case h :: t => h foreach { a => test(t, toApply :+ a) }
      }
    }
    test(actionLists, Nil)
  }

  def runTest (actions :List[Action]) {
    for (proto <- XFORMS) {
      var descs = ListBuffer[String]()
      var trans = ListBuffer[Transform]()
      val t = proto.copy
      val xf = new JAffineTransform
      for (a <- actions) { // TODO: check generality
        descs += a.info
        a.add(t)
        a.add(xf)
        val st = proto.copy
        a.set(st)
        trans += st
      }

      val action = descs.mkString(" ")
      val tpost = trans.reduceLeft(_ concatenate _)
      val tpre = trans.reverse.reduceLeft(_ preConcatenate _)

      for (p <- POINTS) test(action, t, tpost, tpre, xf, p)
      for (v <- VECTORS) test(action, t, tpost, tpre, xf, v)
    }
  }

  def transform (xf :JAffineTransform, point :IPoint) = {
    val dest = Array(0f, 0f)
    xf.transform(Array(point.x, point.y), 0, dest, 0, 1)
    new Point(dest(0), dest(1))
  }

  def transform (xf :JAffineTransform, vec :IVector) = {
    val dest = Array(0.0, 0.0);
    xf.deltaTransform(Array(vec.x.toDouble, vec.y.toDouble), 0, dest, 0, 1);
    new Vector(dest(0).toFloat, dest(1).toFloat)
  }

  def test (action :String, tseq :Transform, tpost :Transform, tpre :Transform,
            xf :JAffineTransform, point :Point) {
    val expect = transform(xf, point)
    test(action + " seq", tseq, point, expect)
    test(action + " post", tpost, point, expect)
    test(action + " pre", tpre, point, expect)
  }

  def test (form :String, t :Transform, p :Point, expect :Point) {
    val orig = new Point(p);
    val desc = form + "\n  " + t + " @ " + p;

    // test single point transform and inverse transform
    val tp = t.transform(p, new Point);
    val itp = t.inverseTransform(tp, new Point);
    assertEquals(desc, orig, p);
    assertPointsEqual(desc, expect, tp);
    assertPointsEqual(desc, p, itp);

    // test multipoint transform
    val ps = Array[IPoint](null, p, null);
    val tps = Array(null, new Point, null);
    t.transform(ps, 1, tps, 1, 1);
    assertEquals(desc, orig, p);
    assertEquals(desc, null, tps(0));
    assertPointsEqual(desc, expect, tps(1));
    assertEquals(desc, null, tps(2));
  }

  def assertPointsEqual (desc :String, p1 :Point, p2 :Point) {
    if (math.abs(p1.x - p2.x) > MathUtil.EPSILON || math.abs(p1.y - p2.y) > MathUtil.EPSILON) {
      fail(desc + "\n  wantPoint " + p1 + " got " + p2);
    }
  }

  def test (action :String, tseq :Transform, tpost :Transform, tpre :Transform,
            xf :JAffineTransform, vector :Vector) {
    val expect = transform(xf, vector)
    test(action + " seq", tseq, vector, expect)
    test(action + " post", tpost, vector, expect)
    test(action + " pre", tpre, vector, expect)
  }

  def test (form :String, t :Transform, v :Vector, expect :Vector) {
    val orig = new Vector(v);
    val desc = form + "\n  " + t + " @ " + v;

    // test vector transform and inverse transform
    val tv = t.transform(v, new Vector);
    val itv = t.inverseTransform(tv, new Vector);
    assertEquals(desc, orig, v);
    assertVectorsEqual(desc, expect, tv);
    assertVectorsEqual(desc, v, itv);
  }

  def assertVectorsEqual (desc :String, v1 :Vector, v2 :Vector) {
    if (math.abs(v1.x - v2.x) > MathUtil.EPSILON || math.abs(v1.y - v2.y) > MathUtil.EPSILON) {
      fail(desc + "\n  wantVec " + v1 + " got " + v2);
    }
  }
}
