//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.f;

import java.io.Serializable;

/**
 * Represents an area in two dimensions.
 */
public class Rectangle implements IRectangle, Serializable
{
    /** The x-coordinate of the rectangle's upper left corner. */
    public float x;

    /** The y-coordinate of the rectangle's upper left corner. */
    public float y;

    /** The width of the rectangle. */
    public float width;

    /** The height of the rectangle. */
    public float height;

    /**
     * Constructs a rectangle at (0,0) and with dimensions (0,0).
     */
    public Rectangle () {
        setBounds(0, 0, 0, 0);
    }

    /**
     * Constructs a rectangle with the supplied upper-left corner and dimensions (0,0).
     */
    public Rectangle (Point p) {
        setBounds(p.x, p.y, 0, 0);
    }

    /**
     * Constructs a rectangle with upper-left corner at (0,) and the supplied dimensions.
     */
    public Rectangle (Dimension d) {
        setBounds(0, 0, d.width, d.height);
    }

    /**
     * Constructs a rectangle with upper-left corner at the supplied point and with the supplied
     * dimensions.
     */
    public Rectangle (Point p, Dimension d) {
        setBounds(p.x, p.y, d.width, d.height);
    }

    /**
     * Constructs a rectangle with the specified upper-left corner and dimensions.
     */
    public Rectangle (float x, float y, float width, float height) {
        setBounds(x, y, width, height);
    }

    /**
     * Constructs a rectangle with bounds equal to the supplied rectangle.
     */
    public Rectangle (Rectangle r) {
        setBounds(r.x, r.y, r.width, r.height);
    }

    /**
     * Sets the size of this rectangle to the specified dimensions.
     */
    public void setSize (float width, float height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Sets the size of this rectangle to the supplied dimensions.
     */
    public void setSize (Dimension d) {
        setSize(d.width, d.height);
    }

    /**
     * Sets the upper-left corner of this rectangle to the specified point.
     */
    public void setLocation (float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Sets the upper-left corner of this rectangle to the supplied point.
     */
    public void setLocation (IPoint p) {
        setLocation(p.getX(), p.getY());
    }

    /**
     * Sets the bounds of this rectangle to the specified bounds.
     */
    public void setBounds (float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    /**
     * Sets the bounds of this rectangle to those of the supplied rectangle.
     */
    public void setBounds (IRectangle r) {
        setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * Grows the bounds of this rectangle by the specified amount (i.e. the upper-left corner moves
     * by the specified amount in the negative x and y direction and the width and height grow by
     * twice the specified amount).
     */
    public void grow (float dx, float dy) {
        x -= dx;
        y -= dy;
        width += dx + dx;
        height += dy + dy;
    }

    /**
     * Translates the upper-left corner of this rectangle by the specified amount.
     */
    public void translate (float mx, float my) {
        x += mx;
        y += my;
    }

    /**
     * Expands the bounds of this rectangle to contain the specified point.
     */
    public void add (float px, float py) {
        float x1 = Math.min(x, px);
        float x2 = Math.max(x + width, px);
        float y1 = Math.min(y, py);
        float y2 = Math.max(y + height, py);
        setBounds(x1, y1, x2 - x1, y2 - y1);
    }

    /**
     * Expands the bounds of this rectangle to contain the supplied point.
     */
    public void add (IPoint p) {
        add(p.getX(), p.getY());
    }

    /**
     * Expands the bounds of this rectangle to contain the supplied rectangle.
     */
    public void add (IRectangle r) {
        float x1 = Math.min(x, r.getX());
        float x2 = Math.max(x + width, r.getX() + r.getWidth());
        float y1 = Math.min(y, r.getY());
        float y2 = Math.max(y + height, r.getY() + r.getHeight());
        setBounds(x1, y1, x2 - x1, y2 - y1);
    }

    @Override // from interface IRectangle
    public float getX ()
    {
        return x;
    }

    @Override // from interface IRectangle
    public float getY ()
    {
        return y;
    }

    @Override // from interface IRectangle
    public float getWidth ()
    {
        return width;
    }

    @Override // from interface IRectangle
    public float getHeight ()
    {
        return height;
    }

    @Override // from interface IRectangle
    public boolean isEmpty () {
        return width <= 0 || height <= 0;
    }

    @Override // from interface IRectangle
    public Point getLocation () {
        return new Point(x, y);
    }

    @Override // from interface IRectangle
    public Dimension getSize () {
        return new Dimension(width, height);
    }

    @Override // from interface IRectangle
    public Rectangle intersection (float rx, float ry, float rw, float rh) {
        float x1 = Math.max(x, rx);
        float y1 = Math.max(y, ry);
        float x2 = Math.min(x + width, rx + rw);
        float y2 = Math.min(y + height, ry + rh);
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
    public int outcode (double px, double py) {
        int code = 0;

        if (width <= 0) {
            code |= OUT_LEFT | OUT_RIGHT;
        } else if (px < x) {
            code |= OUT_LEFT;
        } else if (px > x + width) {
            code |= OUT_RIGHT;
        }

        if (height <= 0) {
            code |= OUT_TOP | OUT_BOTTOM;
        } else if (py < y) {
            code |= OUT_TOP;
        } else if (py > y + height) {
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
        try {
            return (Rectangle)super.clone();
        } catch (CloneNotSupportedException cnse) {
            throw new AssertionError(cnse);
        }
    }

    @Override // from interface Shape
    public boolean contains (float px, float py) {
        if (isEmpty()) {
            return false;
        }
        if (px < x || py < y) {
            return false;
        }
        px -= x;
        py -= y;
        return px < width && py < height;
    }

    @Override // from interface Shape
    public boolean contains (IPoint p) {
        return contains(p.getX(), p.getY());
    }

    @Override // from interface Shape
    public boolean contains (float rx, float ry, float rw, float rh) {
        return contains(rx, ry) && contains(rx + rw - 1, ry + rh - 1);
    }

    @Override // from interface Shape
    public boolean contains (IRectangle r) {
        return contains(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override // from interface Shape
    public boolean intersects (float rx, float ry, float rw, float rh) {
        return !intersection(rx, ry, rw, rh).isEmpty(); // TODO: don't create garbage
    }

    @Override // from interface Shape
    public boolean intersects (IRectangle r) {
        return intersects(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    @Override // from interface Shape
    public IRectangle getBounds () {
        return this;
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Rectangle) {
            Rectangle r = (Rectangle)obj;
            return r.x == x && r.y == y && r.width == width && r.height == height;
        }
        return false;
    }

    @Override
    public int hashCode () {
        return Float.floatToIntBits(x) ^ Float.floatToIntBits(y) ^
            Float.floatToIntBits(width) ^ Float.floatToIntBits(height);
    }

    @Override
    public String toString () {
        return Geometry.dimenToString(width, height) + Geometry.pointToString(x, y);
    }
}
