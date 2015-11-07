package cube.models;

import com.sun.prism.*;
import cube.configs.CubeConfig;

import java.awt.*;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * @author wenyu
 * @since 10/23/15
 */
public class Cube implements ICube {
    private final CubeConfig config;

    private Integer width, height;
    private Position position;

    public Cube(Position position) {
        config = CubeConfig.getInstance();

        width  = config.getWidth();
        height = config.getHeight();

        this.position = position;
    }

    @Override
    public Integer getWidth() {
        return width;
    }

    @Override
    public Integer getHeight() {
        return height;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        setPosition(position.getX(), position.getY());
    }

    @Override
    public void setPosition(Integer x, Integer y) {
        position.setX(x);
        position.setY(y);
    }

    @Override
    public void move(Integer[] d) {
        position.moveX(d[0]);
        position.moveY(d[1]);
    }

    @Override
    public void paint(Graphics g) {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(hints);

        Rectangle2D oR2d = new Rectangle2D.Double(position.getX(), position.getY(), width, height);
        Rectangle2D iR2d = new Rectangle2D.Double(position.getX() + config.getBorder(),
                                                  position.getY() + config.getBorder(),
                                                  width  - 2 * config.getBorder(),
                                                  height - 2 * config.getBorder());

        g2d.setStroke(new BasicStroke(config.getStrokeWidth()));
        g2d.setColor(config.getColor());
        g2d.fill(oR2d);
        g2d.draw(oR2d);

        g2d.setColor(config.getBorderColor());
        g2d.fill(iR2d);
        g2d.draw(iR2d);
    }
}
