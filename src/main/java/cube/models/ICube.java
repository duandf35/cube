package cube.models;

import java.awt.*;

/**
 * @author wenyu
 * @since 10/23/15
 */
public interface ICube {

    /**
     * Return width of cube.
     * @return width
     */
    Integer getWidth();

    /**
     * Return height of cube.
     * @return height
     */
    Integer getHeight();

    /**
     * Return current position of cube.
     * @return position
     */
    Position getPosition();

    /**
     * Set current position of cube.
     * @param position the position
     */
    void setPosition(Position position);

    /**
     * Set current position of cube.
     * @param x the x coordinate.
     * @param y the y coordinate.
     */
    void setPosition(Integer x, Integer y);

    /**
     * Set color of cube.
     * @param color the color
     */
    void setColor(Color color);

    /**
     * Cube horizontal moving.
     * @param d the position change
     */
    void moveX(Integer d);

    /**
     * Cube vertical moving.
     * @param d the position change
     */
    void moveY(Integer d);

    /**
     * Paint cube.
     * @param g the graphic
     */
    void paint(Graphics g);

    /**
     * Dispose cube.
     */
    void dispose();
}
