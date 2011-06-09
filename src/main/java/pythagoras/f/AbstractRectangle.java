//
// $Id$

package pythagoras.f;

/**
 * Provides most of the implementation of {@link IRectangle}, obtaining only the location and
 * dimensions from the derived class.
 */
public abstract class AbstractRectangle extends RectangularShape implements IRectangle
{
    @Override // from interface IRectangle
    public IPoint location () {
        return new AbstractPoint() {
            @Override public float getX () {
                return AbstractRectangle.this.getX();
            }
            @Override public float getY () {
                return AbstractRectangle.this.getY();
            }
        };
    }

    @Override // from interface IRectangle
    public Point getLocation () {
        return new Point(getX(), getY());
    }

    @Override // from interface IRectangle
    public IDimension size () {
        return new AbstractDimension() {
            @Override public float getWidth () {
                return AbstractRectangle.this.getWidth();
            }
            @Override public float getHeight () {
                return AbstractRectangle.this.getHeight();
            }
        };
    }

    @Override // from interface IRectangle
    public Dimension getSize () {
        return new Dimension(getWidth(), getHeight());
    }

    @Override // from interface IRectangle
    public Rectangle intersection (float rx, float ry, float rw, float rh) {
        float x1 = Math.max(getX(), rx);
        float y1 = Math.max(getY(), ry);
        float x2 = Math.min(getMaxX(), rx + rw);
        float y2 = Math.min(getMaxY(), ry + rh);
        return new Rectangle(x1, y1, x2 - x1, y2 - y1);
    }

    @Override // from interface IRectangle
    public Rectangle intersection (IRectangle r) {
        return intersection(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override // from interface IRectangle
    public Rectangle union (IRectangle r) {
        Rectangle rect = new Rectangle(this);
        rect.add(r);
        return rect;
    }

    @Override // from interface IRectangle
    public boolean intersectsLine (float x1, float y1, float x2, float y2) {
        return Geometry.lineIntersectsRect(x1, y1, x2, y2, getX(), getY(), getWidth(), getHeight());
    }

    @Override // from interface IRectangle
    public boolean intersectsLine (ILine l) {
        return intersectsLine(l.getX1(), l.getY1(), l.getX2(), l.getY2());
    }

    @Override // from interface IRectangle
    public int outcode (float px, float py) {
        int code = 0;

        if (getWidth() <= 0) {
            code |= OUT_LEFT | OUT_RIGHT;
        } else if (px < getX()) {
            code |= OUT_LEFT;
        } else if (px > getMaxX()) {
            code |= OUT_RIGHT;
        }

        if (getHeight() <= 0) {
            code |= OUT_TOP | OUT_BOTTOM;
        } else if (py < getY()) {
            code |= OUT_TOP;
        } else if (py > getMaxY()) {
            code |= OUT_BOTTOM;
        }

        return code;
    }

    @Override // from interface IRectangle
    public int outcode (IPoint p) {
        return outcode(p.getX(), p.getY());
    }

    @Override // from interface IRectangle
    public Rectangle clone () {
        return new Rectangle(this);
    }

    @Override // from interface IShape
    public boolean contains (float px, float py) {
        if (isEmpty()) return false;

        float x = getX(), y = getY();
        if (px < x || py < y) return false;

        px -= x;
        py -= y;
        return px < getWidth() && py < getHeight();
    }

    @Override // from interface IShape
    public boolean contains (float rx, float ry, float rw, float rh) {
        if (isEmpty()) return false;

        double x1 = getX(), y1 = getY(), x2 = x1 + getWidth(), y2 = y1 + getHeight();
        return (x1 <= rx) && (rx + rw <= x2) && (y1 <= ry) && (ry + rh <= y2);
    }

    @Override // from interface IShape
    public boolean intersects (float rx, float ry, float rw, float rh) {
        if (isEmpty()) return false;

        double x1 = getX(), y1 = getY(), x2 = x1 + getWidth(), y2 = y1 + getHeight();
        return (rx + rw > x1) && (rx < x2) && (ry + rh > y1) && (ry < y2);
    }

    @Override // from RectangularShape
    public IRectangle frame () {
        return this;
    }

    @Override // from RectangularShape
    public IRectangle bounds () {
        return this;
    }

    @Override // from Object
    public boolean equals (Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractRectangle) {
            AbstractRectangle r = (AbstractRectangle)obj;
            return r.getX() == getX() && r.getY() == getY() &&
                r.getWidth() == getWidth() && r.getHeight() == getHeight();
        }
        return false;
    }

    @Override // from Object
    public int hashCode () {
        return Float.floatToIntBits(getX()) ^ Float.floatToIntBits(getY()) ^
            Float.floatToIntBits(getWidth()) ^ Float.floatToIntBits(getHeight());
    }

    @Override // from Object
    public String toString () {
        return Geometry.dimenToString(getWidth(), getHeight()) +
            Geometry.pointToString(getX(), getY());
    }
}
