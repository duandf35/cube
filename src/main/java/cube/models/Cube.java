package cube.models;

import cube.configs.CubeConfig;

import java.awt.*;
import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

/**
 * @author wenyu
 * @since 10/23/15
 */
public class Cube implements ICube {
    private final CubeConfig config;

    private Color color;
    private Integer width, height;
    private Position position;

    // Hold graphic reference for disposing
    private Graphics2D g2d;

    public Cube(Position position) {
        config = CubeConfig.getInstance();

        width  = config.getWidth();
        height = config.getHeight();
        color = config.getColor();

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
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public void moveX(Integer d) {
        position.moveX(d);
    }

    @Override
    public void moveY(Integer d) {
        position.moveY(d);
    }

    @Override
    public void paint(Graphics g) {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2d = (Graphics2D) g;
        g2d.setRenderingHints(hints);

        Rectangle2D oR2d = new Rectangle2D.Double(position.getX(), position.getY(), width, height);
        Rectangle2D iR2d = new Rectangle2D.Double(position.getX() + config.getBorder(),
                                                  position.getY() + config.getBorder(),
                                                  width  - 2 * config.getBorder(),
                                                  height - 2 * config.getBorder());

        g2d.setStroke(new BasicStroke(config.getStrokeWidth()));
        g2d.setColor(color);
        g2d.fill(oR2d);
        g2d.draw(oR2d);

        g2d.setColor(config.getBorderColor());
        g2d.fill(iR2d);
        g2d.draw(iR2d);
    }

    @Override
    public void dispose() {
        g2d.dispose();
    }
}
