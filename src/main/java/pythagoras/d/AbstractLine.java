//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

import java.util.NoSuchElementException;

/**
 * Provides most of the implementation of {@link ILine}, obtaining only the start and end points
 * from the derived class.
 */
public abstract class AbstractLine implements ILine
{
    @Override // from interface ILine
    public Point getP1 () {
        return getP1(new Point());
    }

    @Override // from interface ILine
    public Point getP1 (Point target) {
        target.setLocation(getX1(), getY1());
        return target;
    }

    @Override // from interface ILine
    public Point getP2 () {
        return getP2(new Point());
    }

    @Override // from interface ILine
    public Point getP2 (Point target) {
        target.setLocation(getX2(), getY2());
        return target;
    }

    @Override // from interface ILine
    public double pointLineDistSq (double px, double py) {
        return Lines.pointLineDistSq(px, py, getX1(), getY1(), getX2(), getY2());
    }

    @Override // from interface ILine
    public double pointLineDistSq (IPoint p) {
        return Lines.pointLineDistSq(p.getX(), p.getY(), getX1(), getY1(), getX2(), getY2());
    }

    @Override // from interface ILine
    public double pointLineDist (double px, double py) {
        return Lines.pointLineDist(px, py, getX1(), getY1(), getX2(), getY2());
    }

    @Override // from interface ILine
    public double pointLineDist (IPoint p) {
        return Lines.pointLineDist(p.getX(), p.getY(), getX1(), getY1(), getX2(), getY2());
    }

    @Override // from interface ILine
    public double pointSegDistSq (double px, double py) {
        return Lines.pointSegDistSq(px, py, getX1(), getY1(), getX2(), getY2());
    }

    @Override // from interface ILine
    public double pointSegDistSq (IPoint p) {
        return Lines.pointSegDistSq(p.getX(), p.getY(), getX1(), getY1(), getX2(), getY2());
    }

    @Override // from interface ILine
    public double pointSegDist (double px, double py) {
        return Lines.pointSegDist(px, py, getX1(), getY1(), getX2(), getY2());
    }

    @Override // from interface ILine
    public double pointSegDist (IPoint p) {
        return Lines.pointSegDist(p.getX(), p.getY(), getX1(), getY1(), getX2(), getY2());
    }

    @Override // from interface ILine
    public int relativeCCW (double px, double py) {
        return Lines.relativeCCW(px, py, getX1(), getY1(), getX2(), getY2());
    }

    @Override // from interface ILine
    public int relativeCCW (IPoint p) {
        return Lines.relativeCCW(p.getX(), p.getY(), getX1(), getY1(), getX2(), getY2());
    }

    @Override // from interface ILine
    public Line clone () {
        return new Line(getX1(), getY1(), getX2(), getY2());
    }

    @Override // from interface IShape
    public boolean isEmpty () {
        return false;
    }

    @Override // from interface IShape
    public boolean contains (double x, double y) {
        return false;
    }

    @Override // from interface IShape
    public boolean contains (IPoint point) {
        return false;
    }

    @Override // from interface IShape
    public boolean contains (double x, double y, double w, double h) {
        return false;
    }

    @Override // from interface IShape
    public boolean contains (IRectangle r) {
        return false;
    }

    @Override // from interface IShape
    public boolean intersects (double rx, double ry, double rw, double rh) {
        return Lines.lineIntersectsRect(getX1(), getY1(), getX2(), getY2(), rx, ry, rw, rh);
    }

    @Override // from interface IShape
    public boolean intersects (IRectangle r) {
        return r.intersectsLine(this);
    }

    @Override // from interface IShape
    public Rectangle getBounds () {
        return getBounds(new Rectangle());
    }

    @Override // from interface IShape
    public Rectangle getBounds (Rectangle target) {
        double x1 = getX1(), x2 = getX2(), y1 = getY1(), y2 = getY2();
        double rx, ry, rw, rh;
        if (x1 < x2) {
            rx = x1;
            rw = x2 - x1;
        } else {
            rx = x2;
            rw = x1 - x2;
        }
        if (y1 < y2) {
            ry = y1;
            rh = y2 - y1;
        } else {
            ry = y2;
            rh = y1 - y2;
        }
        target.setBounds(rx, ry, rw, rh);
        return target;
    }

    @Override // from interface IShape
    public PathIterator getPathIterator (AffineTransform at) {
        return new Iterator(this, at);
    }

    @Override // from interface IShape
    public PathIterator getPathIterator (AffineTransform at, double flatness) {
        return new Iterator(this, at);
    }

    /** An iterator over an {@link ILine}. */
    protected static class Iterator implements PathIterator
    {
        private double x1, y1, x2, y2;
        private AffineTransform t;
        private int index;

        Iterator (ILine l, AffineTransform at) {
            this.x1 = l.getX1();
            this.y1 = l.getY1();
            this.x2 = l.getX2();
            this.y2 = l.getY2();
            this.t = at;
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
            if (index == 0) {
                type = SEG_MOVETO;
                coords[0] = x1;
                coords[1] = y1;
            } else {
                type = SEG_LINETO;
                coords[0] = x2;
                coords[1] = y2;
            }
            if (t != null) {
                t.transform(coords, 0, coords, 0, 1);
            }
            return type;
        }
    }
}
