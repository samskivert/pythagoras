//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

import java.util.NoSuchElementException;

/**
 * Provides most of the implementation of {@link IQuadCurve}, obtaining only the start, end and
 * control point from the derived class.
 */
public abstract class AbstractQuadCurve implements IQuadCurve
{
    @Override // from interface IQuadCurve
    public Point getP1 () {
        return new Point(getX1(), getY1());
    }

    @Override // from interface IQuadCurve
    public Point getCtrlP () {
        return new Point(getCtrlX(), getCtrlY());
    }

    @Override // from interface IQuadCurve
    public Point getP2 () {
        return new Point(getX2(), getY2());
    }

    @Override // from interface IQuadCurve
    public double getFlatnessSq () {
        return Lines.pointSegDistSq(getCtrlX(), getCtrlY(), getX1(), getY1(), getX2(), getY2());
    }

    @Override // from interface IQuadCurve
    public double getFlatness () {
        return Lines.pointSegDist(getCtrlX(), getCtrlY(), getX1(), getY1(), getX2(), getY2());
    }

    @Override // from interface IQuadCurve
    public void subdivide (QuadCurve left, QuadCurve right) {
        QuadCurves.subdivide(this, left, right);
    }

    @Override // from interface IQuadCurve
    public QuadCurve clone () {
        return new QuadCurve(getX1(), getY1(), getCtrlX(), getCtrlY(), getX2(), getY2());
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
        return cross != Crossing.CROSSING && Crossing.isInsideEvenOdd(cross);
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
        return cross == Crossing.CROSSING || Crossing.isInsideEvenOdd(cross);
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
        double ctrlx = getCtrlX(), ctrly = getCtrlY();
        double rx0 = Math.min(Math.min(x1, x2), ctrlx);
        double ry0 = Math.min(Math.min(y1, y2), ctrly);
        double rx1 = Math.max(Math.max(x1, x2), ctrlx);
        double ry1 = Math.max(Math.max(y1, y2), ctrly);
        target.setBounds(rx0, ry0, rx1 - rx0, ry1 - ry0);
        return target;
    }

    @Override // from interface IShape
    public PathIterator getPathIterator (AffineTransform t) {
        return new Iterator(this, t);
    }

    @Override // from interface IShape
    public PathIterator getPathIterator (AffineTransform t, double flatness) {
        return new FlatteningPathIterator(getPathIterator(t), flatness);
    }

    /** An iterator over an {@link IQuadCurve}. */
    protected static class Iterator implements PathIterator
    {
        private IQuadCurve c;
        private AffineTransform t;
        private int index;

        Iterator (IQuadCurve q, AffineTransform t) {
            this.c = q;
            this.t = t;
        }

        @Override public int getWindingRule () {
            return WIND_NON_ZERO;
        }

        @Override public boolean isDone () {
            return (index > 1);
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
                type = SEG_QUADTO;
                coords[0] = c.getCtrlX();
                coords[1] = c.getCtrlY();
                coords[2] = c.getX2();
                coords[3] = c.getY2();
                count = 2;
            }
            if (t != null) {
                t.transform(coords, 0, coords, 0, count);
            }
            return type;
        }
    }
}
