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

    private Integer width, height, x, y;
    private Color color;

    public BasicCube(Integer x, Integer y) {
        config = CubeConfig.getInstance();

        width  = config.getWidth();
        height = config.getHeight();
        color  = config.getColor();

        this.x = x;
        this.y = y;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public void paint(Graphics g) {
        RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(hints);

        Rectangle2D r2d = new Rectangle2D.Double(x, y, width, height);

        g2d.setStroke(new BasicStroke(config.getStrokeWidth()));
        g2d.setColor(color);
        g2d.fill(r2d);
        g2d.draw(r2d);
    }
}
