package cube.models;

import java.util.List;

import cube.configs.CubeConfig;

/**
 * Provide rotating mechanism.
 *
 * @author wenyu
 * @since 11/5/15
 */
public class TetrisRotator implements Rotator {

    private final CubeConfig config;

    private ICube center;
    private List<ICube> rim;

    public TetrisRotator(ICube center, List<ICube> rim) {
        config = CubeConfig.getInstance();

        this.center = center;
        this.rim = rim;
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
            doRotate(p);
        } else {
            // Not rotatable
        }
    }

    private void doRotate() {
        rim.forEach(r -> doRotate(r.getPosition()));
    }

    /**
     * given:
     *      center: (Xc, Yc), start position: (X, Y)
     * when:
     *      doing clockwise rotation
     *      next position is (X', Y')
     * know:
     *      dx = X - Xc
     *      dy = Y - Yc
     * and
     *      dx' = X' - Xc
     *      dy' = Y' - Yc
     * and
     *      dx' = - dy
     *      dy' = dx
     * so
     *      X' - Xc = -dy = Yc - Y
     *   =>
     *      X' = Yc + Xc - Y
     *
     *      Y' - Yc = dx = X - Xc
     *   =>
     *      Y' = Yc - Xc + X
     *
     * so next position always be:
     *
     *      X' = Yc + Xc - Y
     *      Y' = Yc - Xc + X
     *
     * @param rp the position of cube
     */
    private void doRotate(Position rp) {
        Position cp = center.getPosition();

        Integer nx = cp.getY() + cp.getX() - rp.getY();
        Integer ny = cp.getY() - cp.getX() + rp.getX();

        rp.setX(nx);
        rp.setY(ny);
    }
}
