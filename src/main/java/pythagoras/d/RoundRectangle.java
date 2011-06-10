//
// Pythagoras - a collection of geometry classes
// http://github.com/samskivert/pythagoras

package pythagoras.d;

import java.io.Serializable;

/**
 * Represents a rectangle with rounded corners, defined by an arc width and height.
 */
public class RoundRectangle extends AbstractRoundRectangle implements Serializable
{
    /** The x-coordinate of the framing rectangle. */
    public double x;

    /** The y-coordinate of the framing rectangle. */
    public double y;

    /** The width of the framing rectangle. */
    public double width;

    /** The height of the framing rectangle. */
    public double height;

    /** The width of the arc that defines the rounded corners. */
    public double arcwidth;

    /** The height of the arc that defines the rounded corners. */
    public double archeight;

    /**
     * Creates a rounded rectangle with frame (0x0+0+0) and corners of size (0x0).
     */
    public RoundRectangle () {
    }

    /**
     * Creates a rounded rectangle with the specified frame and corner dimensions.
     */
    public RoundRectangle (double x, double y, double width, double height,
                           double arcwidth, double archeight) {
        setRoundRect(x, y, width, height, arcwidth, archeight);
    }

    /**
     * Sets the frame and corner dimensions of this rectangle to the specified values.
     */
    public void setRoundRect (double x, double y, double width, double height,
                              double arcwidth, double archeight) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.arcwidth = arcwidth;
        this.archeight = archeight;
    }

    /**
     * Sets the frame and corner dimensions of this rectangle to be equal to those of the supplied
     * rectangle.
     */
    public void setRoundRect (IRoundRectangle rr) {
        setRoundRect(rr.getX(), rr.getY(), rr.getWidth(), rr.getHeight(),
                     rr.getArcWidth(), rr.getArcHeight());
    }

    @Override // from interface IRoundRectangle
    public double getArcWidth () {
        return arcwidth;
    }

    @Override // from interface IRoundRectangle
    public double getArcHeight () {
        return archeight;
    }

    @Override // from interface IRectangularShape
    public double getX () {
        return x;
    }

    @Override // from interface IRectangularShape
    public double getY () {
        return y;
    }

    @Override // from interface IRectangularShape
    public double getWidth () {
        return width;
    }

    @Override // from interface IRectangularShape
    public double getHeight () {
        return height;
    }

    @Override // from RoundRectangle
    public void setFrame (double x, double y, double width, double height) {
        setRoundRect(x, y, width, height, arcwidth, archeight);
    }
}
