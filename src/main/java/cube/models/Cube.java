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

    Position getPosition();

    void setPosition(Integer x, Integer y);

    /**
     * If cube is digested, it no longer response keyboard action
     */
    void digest();

    Boolean isDigested();
}
