//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

import java.util.NoSuchElementException;

/**
 * Provides most of the implementation of {@link IArc}, obtaining only the frame and other metrics
 * from the derived class.
 */
public abstract class AbstractArc extends RectangularShape implements IArc
{
    @Override // from interface IArc
    public Point getStartPoint () {
        return getStartPoint(new Point());
    }

    @Override // from interface IArc
    public Point getStartPoint (Point target) {
        double a = Math.toRadians(getAngleStart());
        target.setLocation(getX() + (1f + Math.cos(a)) * getWidth() / 2f,
                           getY() + (1f - Math.sin(a)) * getHeight() / 2f);
        return target;
    }

    @Override // from interface IArc
    public Point getEndPoint () {
        return getEndPoint(new Point());
    }

    @Override // from interface IArc
    public Point getEndPoint (Point target) {
        double a = Math.toRadians(getAngleStart() + getAngleExtent());
        target.setLocation(getX() + (1f + Math.cos(a)) * getWidth() / 2f,
                           getY() + (1f - Math.sin(a)) * getHeight() / 2f);
        return target;
    }

    @Override // from interface IArc
    public boolean containsAngle (double angle) {
        double extent = getAngleExtent();
        if (extent >= 360f) {
            return true;
        }
        angle = getNormAngle(angle);
        double a1 = getNormAngle(getAngleStart());
        double a2 = a1 + extent;
        if (a2 > 360f) {
            return angle >= a1 || angle <= a2 - 360f;
        }
        if (a2 < 0f) {
            return angle >= a2 + 360f || angle <= a1;
        }
        return (extent > 0f) ? a1 <= angle && angle <= a2 : a2 <= angle && angle <= a1;
    }

    @Override // from interface IArc
    public Arc clone () {
        return new Arc(getX(), getY(), getWidth(), getHeight(), getAngleStart(), getAngleExtent(),
                       getArcType());
    }

    @Override // from RectangularShape
    public boolean isEmpty () {
        return getArcType() == OPEN || super.isEmpty();
    }

    @Override // from RectangularShape
    public boolean contains (double px, double py) {
        // normalize point
        double nx = (px - getX()) / getWidth() - 0.5f;
        double ny = (py - getY()) / getHeight() - 0.5f;
        if ((nx * nx + ny * ny) > 0.25) {
            return false;
        }

        double extent = getAngleExtent();
        double absExtent = Math.abs(extent);
        if (absExtent >= 360f) {
            return true;
        }

        boolean containsAngle = containsAngle(Math.toDegrees(-Math.atan2(ny, nx)));
        if (getArcType() == PIE) {
            return containsAngle;
        }
        if (absExtent <= 180f && !containsAngle) {
            return false;
        }

        Line l = new Line(getStartPoint(), getEndPoint());
        int ccw1 = l.relativeCCW(px, py);
        int ccw2 = l.relativeCCW(getCenterX(), getCenterY());
        return ccw1 == 0 || ccw2 == 0 || ((ccw1 + ccw2) == 0 ^ absExtent > 180f);
    }

    @Override // from RectangularShape
    public boolean contains (double rx, double ry, double rw, double rh) {
        if (!(contains(rx, ry) && contains(rx + rw, ry) &&
              contains(rx + rw, ry + rh) && contains(rx, ry + rh))) {
            return false;
        }

        double absExtent = Math.abs(getAngleExtent());
        if (getArcType() != PIE || absExtent <= 180f || absExtent >= 360f) {
            return true;
        }

        Rectangle r = new Rectangle(rx, ry, rw, rh);
        double cx = getCenterX(), cy = getCenterY();
        if (r.contains(cx, cy)) {
            return false;
        }

        Point p1 = getStartPoint(), p2 = getEndPoint();
        return !r.intersectsLine(cx, cy, p1.getX(), p1.getY()) &&
            !r.intersectsLine(cx, cy, p2.getX(), p2.getY());
    }

    @Override // from RectangularShape
    public boolean intersects (double rx, double ry, double rw, double rh) {
        if (isEmpty() || rw <= 0f || rh <= 0f) {
            return false;
        }

        // check: does arc contain rectangle's points
        if (contains(rx, ry) || contains(rx + rw, ry) ||
            contains(rx, ry + rh) || contains(rx + rw, ry + rh)) {
            return true;
        }

        double cx = getCenterX(), cy = getCenterY();
        Point p1 = getStartPoint(), p2 = getEndPoint();

        // check: does rectangle contain arc's points
        Rectangle r = new Rectangle(rx, ry, rw, rh);
        if (r.contains(p1) || r.contains(p2) || (getArcType() == PIE && r.contains(cx, cy))) {
            return true;
        }

        if (getArcType() == PIE) {
            if (r.intersectsLine(p1.getX(), p1.getY(), cx, cy) ||
                r.intersectsLine(p2.getX(), p2.getY(), cx, cy)) {
                return true;
            }
        } else {
            if (r.intersectsLine(p1.getX(), p1.getY(), p2.getX(), p2.getY())) {
                return true;
            }
        }

        // nearest rectangle point
        double nx = cx < rx ? rx : (cx > rx + rw ? rx + rw : cx);
        double ny = cy < ry ? ry : (cy > ry + rh ? ry + rh : cy);
        return contains(nx, ny);
    }

    @Override // from RectangularShape
    public Rectangle getBounds (Rectangle target) {
        if (isEmpty()) {
            target.setBounds(getX(), getY(), getWidth(), getHeight());
            return target;
        }

        double rx1 = getX();
        double ry1 = getY();
        double rx2 = rx1 + getWidth();
        double ry2 = ry1 + getHeight();

        Point p1 = getStartPoint(), p2 = getEndPoint();

        double bx1 = containsAngle(180f) ? rx1 : Math.min(p1.getX(), p2.getX());
        double by1 = containsAngle(90f) ? ry1 : Math.min(p1.getY(), p2.getY());
        double bx2 = containsAngle(0f) ? rx2 : Math.max(p1.getX(), p2.getX());
        double by2 = containsAngle(270f) ? ry2 : Math.max(p1.getY(), p2.getY());

        if (getArcType() == PIE) {
            double cx = getCenterX();
            double cy = getCenterY();
            bx1 = Math.min(bx1, cx);
            by1 = Math.min(by1, cy);
            bx2 = Math.max(bx2, cx);
            by2 = Math.max(by2, cy);
        }
        target.setBounds(bx1, by1, bx2 - bx1, by2 - by1);
        return target;
    }

    @Override // from interface IShape
    public PathIterator getPathIterator (AffineTransform at) {
        return new Iterator(this, at);
    }

    /** Returns a normalized angle (bound between 0 and 360 degrees). */
    protected double getNormAngle (double angle) {
        return angle - Math.floor(angle / 360f) * 360f;
    }

    /** An iterator over an {@link IArc}. */
    protected static class Iterator implements PathIterator
    {
        /** The x coordinate of left-upper corner of the arc rectangle bounds */
        private double x;

        /** The y coordinate of left-upper corner of the arc rectangle bounds */
        private double y;

        /** The width of the arc rectangle bounds */
        private double width;

        /** The height of the arc rectangle bounds */
        private double height;

        /** The start angle of the arc in degrees */
        private double angle;

        /** The angle extent in degrees */
        private double extent;

        /** The closure type of the arc */
        private int type;

        /** The path iterator transformation */
        private AffineTransform t;

        /** The current segment index */
        private int index;

        /** The number of arc segments the source arc subdivided to be approximated by Bezier
         * curves. Depends on extent value. */
        private int arcCount;

        /** The number of line segments. Depends on closure type. */
        private int lineCount;

        /** The step to calculate next arc subdivision point */
        private double step;

        /** The temporary value of cosinus of the current angle */
        private double cos;

        /** The temporary value of sinus of the current angle */
        private double sin;

        /** The coefficient to calculate control points of Bezier curves */
        private double k;

        /** The temporary value of x coordinate of the Bezier curve control vector */
        private double kx;

        /** The temporary value of y coordinate of the Bezier curve control vector */
        private double ky;

        /** The x coordinate of the first path point (MOVE_TO) */
        private double mx;

        /** The y coordinate of the first path point (MOVE_TO) */
        private double my;

        Iterator (IArc a, AffineTransform t) {
            this.width = a.getWidth() / 2f;
            this.height = a.getHeight() / 2f;
            this.x = a.getX() + width;
            this.y = a.getY() + height;
            this.angle = -Math.toRadians(a.getAngleStart());
            this.extent = -a.getAngleExtent();
            this.type = a.getArcType();
            this.t = t;

            if (width < 0 || height < 0) {
                arcCount = 0;
                lineCount = 0;
                index = 1;
                return;
            }

            if (Math.abs(extent) >= 360f) {
                arcCount = 4;
                k = 4f / 3f * (Math.sqrt(2f) - 1f);
                step = Math.PI / 2f;
                if (extent < 0f) {
                    step = -step;
                    k = -k;
                }
            } else {
                arcCount = (int)Math.rint(Math.abs(extent) / 90f);
                step = Math.toRadians(extent / arcCount);
                k = 4f / 3f * (1f - Math.cos(step / 2f)) / Math.sin(step / 2f);
            }

            lineCount = 0;
            if (type == CHORD) {
                lineCount++;
            } else if (type == PIE) {
                lineCount += 2;
            }
        }

        @Override public int getWindingRule () {
            return WIND_NON_ZERO;
        }

        @Override public boolean isDone () {
            return index > arcCount + lineCount;
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
                count = 1;
                cos = Math.cos(angle);
                sin = Math.sin(angle);
                kx = k * width * sin;
                ky = k * height * cos;
                coords[0] = mx = x + cos * width;
                coords[1] = my = y + sin * height;
            } else if (index <= arcCount) {
                type = SEG_CUBICTO;
                count = 3;
                coords[0] = mx - kx;
                coords[1] = my + ky;
                angle += step;
                cos = Math.cos(angle);
                sin = Math.sin(angle);
                kx = k * width * sin;
                ky = k * height * cos;
                coords[4] = mx = x + cos * width;
                coords[5] = my = y + sin * height;
                coords[2] = mx + kx;
                coords[3] = my - ky;
            } else if (index == arcCount + lineCount) {
                type = SEG_CLOSE;
                count = 0;
            } else {
                type = SEG_LINETO;
                count = 1;
                coords[0] = x;
                coords[1] = y;
            }
            if (t != null) {
                t.transform(coords, 0, coords, 0, count);
            }
            return type;
        }
    }
}
