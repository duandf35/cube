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
    public static final Integer NON_ROTATABLE = 0;
    public static final Integer SINGLE_CENTER = 3;
    public static final Integer DUEL_CENTER = 4;

    private final CubeConfig config;

    private Integer diameter;
    private List<ICube> rim;
    private List<ICube> center;

    public Rotator(Integer diameter) {
        config = CubeConfig.getInstance();

        this.diameter = diameter;
    }

    @Override
    public void setRim(List<ICube> rim) {
        this.rim = rim;
    }

    @Override
    public void setCenter(List<ICube> center) {
        this.center = center;
    }

    @Override
    public void rotate() {
        doRotate(null);
    }

    @Override
    public void rotate(Position p) {
        doRotate(p);
    }

    private void doRotate(Position p) {
        if (diameter.equals(SINGLE_CENTER)) {
            if (p != null) {
                doRotateSC(p);
            } else {
                doRotateSC();
            }
        } else if (diameter.equals(DUEL_CENTER)) {
            if (p != null) {
                doRotateDC(p);
            } else {
                doRotateDC();
            }
        }
    }

    private void doRotateSC() {
        rim.stream().forEach(r -> doRotateSC(r.getPosition()));
    }

    private void doRotateDC() {

    }

    private void doRotateSC(Position rp) {
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

    private void doRotateDC(Position p) {

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
