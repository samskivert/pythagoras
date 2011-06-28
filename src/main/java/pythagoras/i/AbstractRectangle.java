//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.i;

/**
 * Provides most of the implementation of {@link IRectangle}, obtaining only the location and
 * dimensions from the derived class.
 */
public abstract class AbstractRectangle implements IRectangle
{
    @Override // from IRectangle
    public int getMinX () {
        return getX();
    }

    @Override // from IRectangle
    public int getMinY () {
        return getY();
    }

    @Override // from IRectangle
    public int getMaxX () {
        return getX() + getWidth() - 1;
    }

    @Override // from IRectangle
    public int getMaxY () {
        return getY() + getHeight() - 1;
    }

    @Override // from interface IRectangle
    public Point getLocation () {
        return getLocation(new Point());
    }

    @Override // from interface IRectangle
    public Point getLocation (Point target) {
        target.setLocation(getX(), getY());
        return target;
    }

    @Override // from interface IRectangle
    public Dimension getSize () {
        return getSize(new Dimension());
    }

    @Override // from interface IRectangle
    public Dimension getSize (Dimension target) {
        target.setSize(getWidth(), getHeight());
        return target;
    }

    @Override // from interface IRectangle
    public Rectangle intersection (int rx, int ry, int rw, int rh) {
        int x1 = Math.max(getX(), rx);
        int y1 = Math.max(getY(), ry);
        int x2 = Math.min(getMaxX(), rx + rw - 1);
        int y2 = Math.min(getMaxY(), ry + rh - 1);
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
    public int outcode (int px, int py) {
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
    public boolean isEmpty () {
        return getWidth() <= 0 || getHeight() <= 0;
    }

    @Override // from interface IShape
    public boolean contains (int px, int py) {
        if (isEmpty()) return false;

        int x = getX(), y = getY();
        if (px < x || py < y) return false;

        px -= x;
        py -= y;
        return px < getWidth() && py < getHeight();
    }

    @Override // from interface IShape
    public boolean contains (IPoint point) {
        return contains(point.getX(), point.getY());
    }

    @Override // from interface IShape
    public boolean contains (int rx, int ry, int rw, int rh) {
        if (isEmpty()) return false;

        int x1 = getX(), y1 = getY(), x2 = x1 + getWidth(), y2 = y1 + getHeight();
        return (x1 <= rx) && (rx + rw <= x2) && (y1 <= ry) && (ry + rh <= y2);
    }

    @Override // from interface IShape
    public boolean contains (IRectangle rect) {
        return contains(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override // from interface IShape
    public boolean intersects (int rx, int ry, int rw, int rh) {
        if (isEmpty()) return false;

        int x1 = getX(), y1 = getY(), x2 = x1 + getWidth(), y2 = y1 + getHeight();
        return (rx + rw > x1) && (rx < x2) && (ry + rh > y1) && (ry < y2);
    }

    @Override // from interface IShape
    public boolean intersects (IRectangle rect) {
        return intersects(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
    }

    @Override // from interface IShape
    public Rectangle getBounds () {
        return getBounds(new Rectangle());
    }

    @Override // from interface IShape
    public Rectangle getBounds (Rectangle target) {
        target.setBounds(getX(), getY(), getWidth(), getHeight());
        return target;
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
        return getX() ^ getY() ^ getWidth() ^ getHeight();
    }

    @Override // from Object
    public String toString () {
        return Dimensions.dimenToString(getWidth(), getHeight()) +
            Points.pointToString(getX(), getY());
    }
}
