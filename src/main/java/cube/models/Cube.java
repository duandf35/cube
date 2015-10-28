package cube.models;

import java.awt.*;

/**
 * @author wenyu
 * @since 10/23/15
 */
public interface Cube {

    void paint(Graphics g);

    Integer getWidth();

    Integer getHeight();

    Integer getX();

    Integer getY();

    void setX(Integer x);

    void setY(Integer y);
}
