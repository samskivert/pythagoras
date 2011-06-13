//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

/**
 * The base class for various {@link IShape} objects whose geometry is defined by a rectangular
 * frame.
 */
public abstract class RectangularShape implements IRectangularShape
{
    /**
     * Sets the location and size of the framing rectangle of this shape to the specified values.
     */
    public abstract void setFrame (double x, double y, double width, double height);

    /**
     * Sets the location and size of the framing rectangle of this shape to the supplied values.
     */
    public void setFrame (IPoint loc, IDimension size) {
        setFrame(loc.getX(), loc.getY(), size.getWidth(), size.getHeight());
    }

    /**
     * Sets the location and size of the framing rectangle of this shape to be equal to the
     * supplied rectangle.
     */
    public void setFrame (IRectangle r) {
        setFrame(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    }

    /**
     * Sets the location and size of the framing rectangle of this shape based on the specified
     * diagonal line.
     */
    public void setFrameFromDiagonal (double x1, double y1, double x2, double y2) {
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
        setFrame(rx, ry, rw, rh);
    }

    /**
     * Sets the location and size of the framing rectangle of this shape based on the supplied
     * diagonal line.
     */
    public void setFrameFromDiagonal (IPoint p1, IPoint p2) {
        setFrameFromDiagonal(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    /**
     * Sets the location and size of the framing rectangle of this shape based on the specified
     * center and corner points.
     */
    public void setFrameFromCenter (double centerX, double centerY,
                                    double cornerX, double cornerY) {
        double width = Math.abs(cornerX - centerX);
        double height = Math.abs(cornerY - centerY);
        setFrame(centerX - width, centerY - height, width * 2, height * 2);
    }

    /**
     * Sets the location and size of the framing rectangle of this shape based on the supplied
     * center and corner points.
     */
    public void setFrameFromCenter (IPoint center, IPoint corner) {
        setFrameFromCenter(center.getX(), center.getY(), corner.getX(), corner.getY());
    }

    @Override // from IRectangularShape
    public double getMinX () {
        return getX();
    }

    @Override // from IRectangularShape
    public double getMinY () {
        return getY();
    }

    @Override // from IRectangularShape
    public double getMaxX () {
        return getX() + getWidth();
    }

    @Override // from IRectangularShape
    public double getMaxY () {
        return getY() + getHeight();
    }

    @Override // from IRectangularShape
    public double getCenterX () {
        return getX() + getWidth() / 2;
    }

    @Override // from IRectangularShape
    public double getCenterY () {
        return getY() + getHeight() / 2;
    }

    @Override // from IRectangularShape
    public Rectangle getFrame () {
        return getBounds();
    }

    @Override // from IRectangularShape
    public Rectangle getFrame (Rectangle target) {
        return getBounds(target);
    }

    @Override // from interface IShape
    public boolean isEmpty () {
        return getWidth() <= 0 || getHeight() <= 0;
    }

    @Override // from interface IShape
    public boolean contains (IPoint point) {
        return contains(point.getX(), point.getY());
    }

    @Override // from interface IShape
    public boolean contains (IRectangle rect) {
        return contains(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
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

    @Override // from interface IShape
    public PathIterator getPathIterator (AffineTransform t, double flatness) {
        return new FlatteningPathIterator(getPathIterator(t), flatness);
    }
}
