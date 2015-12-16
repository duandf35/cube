package cube.models;

import cube.configs.CubeConfig;

import java.util.List;

/**
 * Provide rotating mechanism.
 *
 * @author wenyu
 * @since 11/5/15
 */
public class Rotator implements Rotatable {

    private final CubeConfig config;

    private ICube center;
    private Integer radius;
    private List<ICube> rim;

    public Rotator() {
        config = CubeConfig.getInstance();
    }

    @Override
    public void setRim(List<ICube> rim) {
        this.rim = rim;
    }

    @Override
    public void setCenter(ICube center) {
        this.center = center;
    }

    @Override
    public void rotate() {
        doRotate();
    }

    @Override
    public void rotate(Position p) {
        if (null != center) {
            if (null != p) {
                doRotate(p);
            } else {
                doRotate();
            }
        } else {
            // Not rotatable
        }
    }

    private void doRotate() {
        rim.stream().forEach(r -> doRotate(r.getPosition()));
    }

    private void doRotate(Position rp) {
        Position cp = center.getPosition();

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
