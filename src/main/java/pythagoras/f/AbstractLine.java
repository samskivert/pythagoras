//
// $Id$

package pythagoras.f;

/**
 * Provides most of the implementation of {@link ILine}, obtaining only the start and end points
 * from the derived class.
 */
public abstract class AbstractLine implements ILine
{
    @Override // from interface ILine
    public IPoint p1 () {
        return new AbstractPoint() {
            @Override public float getX () {
                return getX1();
            }
            @Override public float getY () {
                return getY1();
            }
        };
    }

    @Override // from interface ILine
    public Point getP1 () {
        return new Point(getX1(), getY1());
    }

    @Override // from interface ILine
    public IPoint p2 () {
        return new AbstractPoint() {
            @Override public float getX () {
                return getX2();
            }
            @Override public float getY () {
                return getY2();
            }
        };
    }

    @Override // from interface ILine
    public Point getP2 () {
        return new Point(getX2(), getY2());
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
    public boolean contains (float x, float y) {
        return false;
    }

    @Override // from interface IShape
    public boolean contains (IPoint point) {
        return false;
    }

    @Override // from interface IShape
    public boolean contains (float x, float y, float w, float h) {
        return false;
    }

    @Override // from interface IShape
    public boolean contains (IRectangle r) {
        return false;
    }

    @Override // from interface IShape
    public boolean intersects (float rx, float ry, float rw, float rh) {
        return Geometry.lineIntersectsRect(getX1(), getY1(), getX2(), getY2(), rx, ry, rw, rh);
    }

    @Override // from interface IShape
    public boolean intersects (IRectangle r) {
        return r.intersectsLine(this);
    }

    @Override // from interface IShape
    public IRectangle bounds () {
        return new AbstractRectangle() {
            @Override public float getX () {
                return Math.min(getX1(), getX2());
            }
            @Override public float getY () {
                return Math.min(getY1(), getY2());
            }
            @Override public float getWidth () {
                float x1 = getX1(), x2 = getX2();
                return (x1 < x2) ? (x2 - x1) : (x1 - x2);
            }
            @Override public float getHeight () {
                float y1 = getY1(), y2 = getY2();
                return (y1 < y2) ? (y2 - y1) : (y1 - y2);
            }
            // this isn't visible in the type, so won't be called by non-combatants
            @Override public void setFrame (float x, float y, float w, float h) {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override // from interface IShape
    public Rectangle getBounds () {
        float x1 = getX1(), x2 = getX2(), y1 = getY1(), y2 = getY2();
        float rx, ry, rw, rh;
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
        return new Rectangle(rx, ry, rw, rh);
    }
}
