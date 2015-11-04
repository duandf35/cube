package cube.models;

import java.awt.*;

/**
 * @author wenyu
 * @since 10/23/15
 */
public interface ICube {

    void paint(Graphics g);

    Integer getWidth();

    Integer getHeight();

    Position getPosition();

    void setPosition(Integer x, Integer y);
}
