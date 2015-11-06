package cube.models;

import cube.configs.CubeConfig;

import java.util.List;

/**
 * @author wenyu
 * @since 11/5/15
 */
public class Rotator {
    public static final Integer DIAMETER_3 = 3;
    public static final Integer DIAMETER_4 = 4;

    private final CubeConfig config;

    private Integer diameter;
    private List<ICube> rim;
    private List<ICube> center;

    public Rotator(Integer diameter, List<ICube> rim, List<ICube> center) {
        config = CubeConfig.getInstance();

        this.diameter = diameter;
        this.rim = rim;
        this.center = center;
    }

    public void rotate() {
        if (diameter.equals(DIAMETER_3)) {
            rotateD3();
        } else if (diameter.equals(DIAMETER_4)) {
            rotateD4();
        }
    }

    private void rotateD3() {
        Position cp = center.get(0).getPosition();
        for (ICube r: rim) {
            Position rp = r.getPosition();
            if (inN(rp, cp)) {
                fromNtoE(rp);
            } else if (inE(rp, cp)) {
                fromEtoS(rp);
            } else if (inS(rp, cp)) {
                fromStoW(rp);
            } else if (inW(rp, cp)) {
                fromWtoN(rp);
            }
        }
    }

    private void rotateD4() {

    }

    private boolean inN(Position rp, Position cp) {
        return (cp.getX().equals(rp.getX()) && (cp.getY()).equals(rp.getY() + config.getHeight()));
    }

    private boolean inE(Position rp, Position cp) {
        return (cp.getY()).equals(rp.getY()) && (cp.getX().equals(rp.getX() - config.getWidth()));
    }

    private boolean inS(Position rp, Position cp) {
        return (cp.getX()).equals(rp.getX()) && (cp.getY().equals(rp.getY() - config.getWidth()));
    }

    private boolean inW(Position rp, Position cp) {
        return (cp.getY()).equals(rp.getY()) && (cp.getX().equals(rp.getX() + config.getWidth()));
    }

    private void fromWtoN(Position rp) {
        rp.moveX(config.getWidth());
        rp.moveY(- config.getHeight());
    }

    private void fromNtoE(Position rp) {
        rp.moveX(config.getWidth());
        rp.moveY(config.getHeight());
    }

    private void fromEtoS(Position rp) {
        rp.moveX(- config.getWidth());
        rp.moveY(config.getHeight());
    }

    private void fromStoW(Position rp) {
        rp.moveX(- config.getWidth());
        rp.moveY(- config.getHeight());
    }
}
