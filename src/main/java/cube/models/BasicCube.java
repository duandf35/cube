package cube.models;

import cube.configs.CubeConfig;

import java.awt.*;
import java.awt.geom.Rectangle2D;

/**
 * @author wenyu
 * @since 10/23/15
 */
public class BasicCube implements Cube {
    private final CubeConfig config;

    private Color color;
    private Integer width, height;
    private Boolean digested;
    private Position position;

    public BasicCube(Position position) {
        config = CubeConfig.getInstance();

        color  = config.getColor();
        width  = config.getWidth();
        height = config.getHeight();

        digested = false;
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
    public void setPosition(Integer x, Integer y) {
        position.setX(x);
        position.setY(y);
    }

    @Override
    public Boolean isDigested() {
        return digested;
    }

    @Override
    public void digest() {
        digested = true;
    }

    @Override
    public void paint(Graphics g) {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(hints);

        Rectangle2D r2d = new Rectangle2D.Double(position.getX(), position.getY(), width, height);

        g2d.setStroke(new BasicStroke(config.getStrokeWidth()));
        g2d.setColor(color);
        g2d.fill(r2d);
        g2d.draw(r2d);
    }
}
