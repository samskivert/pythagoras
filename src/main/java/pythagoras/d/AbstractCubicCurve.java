//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

import java.util.NoSuchElementException;

/**
 * Provides most of the implementation of {@link ICubicCurve}, obtaining only the start, end and
 * control points from the derived class.
 */
public abstract class AbstractCubicCurve implements ICubicCurve
{
    @Override // from interface ICubicCurve
    public Point getP1 () {
        return new Point(getX1(), getY1());
    }

    @Override // from interface ICubicCurve
    public Point getCtrlP1 () {
        return new Point(getCtrlX1(), getCtrlY1());
    }

    @Override // from interface ICubicCurve
    public Point getCtrlP2 () {
        return new Point(getCtrlX2(), getCtrlY2());
    }

    @Override // from interface ICubicCurve
    public Point getP2 () {
        return new Point(getX2(), getY2());
    }

    @Override // from interface ICubicCurve
    public double getFlatnessSq () {
        return CubicCurves.getFlatnessSq(getX1(), getY1(), getCtrlX1(), getCtrlY1(),
                                         getCtrlX2(), getCtrlY2(), getX2(), getY2());
    }

    @Override // from interface ICubicCurve
    public double getFlatness () {
        return CubicCurves.getFlatness(getX1(), getY1(), getCtrlX1(), getCtrlY1(),
                                       getCtrlX2(), getCtrlY2(), getX2(), getY2());
    }

    @Override // from interface ICubicCurve
    public void subdivide (CubicCurve left, CubicCurve right) {
        CubicCurves.subdivide(this, left, right);
    }

    @Override // from interface ICubicCurve
    public CubicCurve clone () {
        return new CubicCurve(getX1(), getY1(), getCtrlX1(), getCtrlY1(),
                              getCtrlX2(), getCtrlY2(), getX2(), getY2());
    }

    @Override // from interface IShape
    public boolean isEmpty () {
        return true; // curves contain no space
    }

    @Override // from interface IShape
    public boolean contains (double px, double py) {
        return Crossing.isInsideEvenOdd(Crossing.crossShape(this, px, py));
    }

    @Override // from interface IShape
    public boolean contains (double rx, double ry, double rw, double rh) {
        int cross = Crossing.intersectShape(this, rx, ry, rw, rh);
        return (cross != Crossing.CROSSING) && Crossing.isInsideEvenOdd(cross);
    }

    @Override // from interface IShape
    public boolean contains (IPoint p) {
        return contains(p.getX(), p.getY());
    }

    @Override // from interface IShape
    public boolean contains (IRectangle r) {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override // from interface IShape
    public boolean intersects (double rx, double ry, double rw, double rh) {
        int cross = Crossing.intersectShape(this, rx, ry, rw, rh);
        return (cross == Crossing.CROSSING) || Crossing.isInsideEvenOdd(cross);
    }

    @Override // from interface IShape
    public boolean intersects (IRectangle r) {
        return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override // from interface IShape
    public Rectangle getBounds () {
        return getBounds(new Rectangle());
    }

    @Override // from interface IShape
    public Rectangle getBounds (Rectangle target) {
        double x1 = getX1(), y1 = getY1(), x2 = getX2(), y2 = getY2();
        double ctrlx1 = getCtrlX1(), ctrly1 = getCtrlY1();
        double ctrlx2 = getCtrlX2(), ctrly2 = getCtrlY2();
        double rx1 = Math.min(Math.min(x1, x2), Math.min(ctrlx1, ctrlx2));
        double ry1 = Math.min(Math.min(y1, y2), Math.min(ctrly1, ctrly2));
        double rx2 = Math.max(Math.max(x1, x2), Math.max(ctrlx1, ctrlx2));
        double ry2 = Math.max(Math.max(y1, y2), Math.max(ctrly1, ctrly2));
        target.setBounds(rx1, ry1, rx2 - rx1, ry2 - ry1);
        return target;
    }

    @Override // from interface IShape
    public PathIterator getPathIterator (AffineTransform t) {
        return new Iterator(this, t);
    }

    @Override // from interface IShape
    public PathIterator getPathIterator (AffineTransform at, double flatness) {
        return new FlatteningPathIterator(getPathIterator(at), flatness);
    }

    /** An iterator over an {@link ICubicCurve}. */
    protected static class Iterator implements PathIterator
    {
        private ICubicCurve c;
        private AffineTransform t;
        private int index;

        Iterator (ICubicCurve c, AffineTransform t) {
            this.c = c;
            this.t = t;
        }

        @Override public int getWindingRule () {
            return WIND_NON_ZERO;
        }

        @Override public boolean isDone () {
            return index > 1;
        }

        @Override public void next () {
            index++;
        }

        @Override public int currentSegment (double[] coords) {
            if (isDone()) {
                throw new NoSuchElementException("Iterator out of bounds");
            }
            int type;
            int count;
            if (index == 0) {
                type = SEG_MOVETO;
                coords[0] = c.getX1();
                coords[1] = c.getY1();
                count = 1;
            } else {
                type = SEG_CUBICTO;
                coords[0] = c.getCtrlX1();
                coords[1] = c.getCtrlY1();
                coords[2] = c.getCtrlX2();
                coords[3] = c.getCtrlY2();
                coords[4] = c.getX2();
                coords[5] = c.getY2();
                count = 3;
            }
            if (t != null) {
                t.transform(coords, 0, coords, 0, count);
            }
            return type;
        }
    }
}
