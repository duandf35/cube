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

    public Rotator(Integer diameter) {
        config = CubeConfig.getInstance();

        this.diameter = diameter;
    }

    public void setRim(List<ICube> rim) {
        this.rim = rim;
    }

    public void setCenter(List<ICube> center) {
        this.center = center;
    }

    public void rotate() {
        doRotate(null);
    }

    public void rotate(Position p) {
        if (diameter.equals(DIAMETER_3) && isCenter(p)) {
            doRotate(p);
        }
    }

    private void doRotate(Position p) {
        if (diameter.equals(DIAMETER_3)) {
            if (p != null) {
                doRotateD3(p);
            } else {
                doRotateD3();
            }
        } else if (diameter.equals(DIAMETER_4)) {
            if (p != null) {
                doRotateD4(p);
            } else {
                doRotateD4();
            }
        }
    }

    private void doRotateD3() {
        for (ICube r: rim) {
            doRotateD3(r.getPosition());
        }
    }

    private void doRotateD4() {
        for (ICube r: rim) {
            doRotateD4(r.getPosition());
        }
    }

    private void doRotateD3(Position rp) {
        Position cp = center.get(0).getPosition();

        if (inNW(rp, cp)) {
            fromNWtoNE(rp);
        } else if (inN(rp, cp)) {
            fromNtoE(rp);
        } else if (inNE(rp, cp)) {
            fromNEtoSE(rp);
        } else if (inE(rp, cp)) {
            fromEtoS(rp);
        } else if (inSE(rp, cp)) {
            fromSEtoSW(rp);
        } else if (inS(rp, cp)) {
            fromStoW(rp);
        } else if (inSW(rp, cp)) {
            fromSWtoNW(rp);
        } else if (inW(rp, cp)) {
            fromWtoN(rp);
        }
    }

    private void doRotateD4(Position p) {

    }

    private boolean isCenter(Position p) {
        boolean isCenter = false;

        for(ICube c: center) {
            isCenter |= (p.equals(c.getPosition()));
        }

        return isCenter;
    }

    private boolean inNW(Position rp, Position cp) {
        return (cp.getX().equals(rp.getX() + config.getWidth()) && (cp.getY()).equals(rp.getY() + config.getHeight()));
    }

    private boolean inN(Position rp, Position cp) {
        return (cp.getX().equals(rp.getX()) && (cp.getY()).equals(rp.getY() + config.getHeight()));
    }

    private boolean inNE(Position rp, Position cp) {
        return (cp.getX().equals(rp.getX() - config.getWidth()) && (cp.getY()).equals(rp.getY() + config.getHeight()));
    }

    private boolean inE(Position rp, Position cp) {
        return (cp.getY()).equals(rp.getY()) && (cp.getX().equals(rp.getX() - config.getWidth()));
    }

    private boolean inSE(Position rp, Position cp) {
        return (cp.getX().equals(rp.getX() - config.getWidth()) && (cp.getY()).equals(rp.getY() - config.getHeight()));
    }

    private boolean inS(Position rp, Position cp) {
        return (cp.getX()).equals(rp.getX()) && (cp.getY().equals(rp.getY() - config.getWidth()));
    }

    private boolean inSW(Position rp, Position cp) {
        return (cp.getX().equals(rp.getX() + config.getWidth()) && (cp.getY()).equals(rp.getY() - config.getHeight()));
    }

    private boolean inW(Position rp, Position cp) {
        return (cp.getY()).equals(rp.getY()) && (cp.getX().equals(rp.getX() + config.getWidth()));
    }

    private void fromNWtoNE(Position rp) {
        rp.moveX(2 * config.getWidth());
    }

    private void fromWtoN(Position rp) {
        rp.moveX(config.getWidth());
        rp.moveY(- config.getHeight());
    }

    private void fromNEtoSE(Position rp) {
        rp.moveY(2 * config.getHeight());
    }

    private void fromNtoE(Position rp) {
        rp.moveX(config.getWidth());
        rp.moveY(config.getHeight());
    }

    private void fromSEtoSW(Position rp) {
        rp.moveX(- 2 * config.getWidth());
    }

    private void fromEtoS(Position rp) {
        rp.moveX(- config.getWidth());
        rp.moveY(config.getHeight());
    }

    private void fromSWtoNW(Position rp) {
        rp.moveY(- 2 * config.getHeight());
    }

    private void fromStoW(Position rp) {
        rp.moveX(- config.getWidth());
        rp.moveY(- config.getHeight());
    }
}
