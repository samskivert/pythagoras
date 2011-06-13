//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.i;

import java.io.Serializable;

/**
 * Represents an area in two dimensions.
 */
public class Rectangle extends AbstractRectangle implements Serializable
{
    /** The x-coordinate of the rectangle's upper left corner. */
    public int x;

    /** The y-coordinate of the rectangle's upper left corner. */
    public int y;

    /** The width of the rectangle. */
    public int width;

    /** The height of the rectangle. */
    public int height;

    /**
     * Constructs a rectangle at (0,0) and with dimensions (0,0).
     */
    public Rectangle () {
    }

    /**
     * Constructs a rectangle with the supplied upper-left corner and dimensions (0,0).
     */
    public Rectangle (IPoint p) {
        setBounds(p.getX(), p.getY(), 0, 0);
    }

    /**
     * Constructs a rectangle with upper-left corner at (0,) and the supplied dimensions.
     */
    public Rectangle (IDimension d) {
        setBounds(0, 0, d.getWidth(), d.getHeight());
    }

    /**
     * Constructs a rectangle with upper-left corner at the supplied point and with the supplied
     * dimensions.
     */
    public Rectangle (IPoint p, IDimension d) {
        setBounds(p.getX(), p.getY(), d.getWidth(), d.getHeight());
    }

    /**
     * Constructs a rectangle with the specified upper-left corner and dimensions.
     */
    public Rectangle (int x, int y, int width, int height) {
        setBounds(x, y, width, height);
    }

    /**
     * Constructs a rectangle with bounds equal to the supplied rectangle.
     */
    public Rectangle (IRectangle r) {
        setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * Sets the upper-left corner of this rectangle to the specified point.
     */
    public void setLocation (int x, int y) {
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
     * Sets the size of this rectangle to the specified dimensions.
     */
    public void setSize (int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * Sets the size of this rectangle to the supplied dimensions.
     */
    public void setSize (IDimension d) {
        setSize(d.getWidth(), d.getHeight());
    }

    /**
     * Sets the bounds of this rectangle to the specified bounds.
     */
    public void setBounds (int x, int y, int width, int height) {
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
    public void grow (int dx, int dy) {
        x -= dx;
        y -= dy;
        width += dx + dx;
        height += dy + dy;
    }

    /**
     * Translates the upper-left corner of this rectangle by the specified amount.
     */
    public void translate (int mx, int my) {
        x += mx;
        y += my;
    }

    /**
     * Expands the bounds of this rectangle to contain the specified point.
     */
    public void add (int px, int py) {
        int x1 = Math.min(x, px);
        int x2 = Math.max(x + width, px);
        int y1 = Math.min(y, py);
        int y2 = Math.max(y + height, py);
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
        int x1 = Math.min(x, r.getX());
        int x2 = Math.max(x + width, r.getX() + r.getWidth());
        int y1 = Math.min(y, r.getY());
        int y2 = Math.max(y + height, r.getY() + r.getHeight());
        setBounds(x1, y1, x2 - x1, y2 - y1);
    }

    @Override // from interface IRectangle
    public int getX () {
        return x;
    }

    @Override // from interface IRectangle
    public int getY () {
        return y;
    }

    @Override // from interface IRectangle
    public int getWidth () {
        return width;
    }

    @Override // from interface IRectangle
    public int getHeight () {
        return height;
    }
}
