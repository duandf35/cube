 package cube.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wenyu
 * @since 11/3/15
 */
public class Tetris implements ITetris {
    private boolean digested;
    private List<ICube> cubes, rim, center;
    private Rotator rotator;

    public Tetris(List<ICube> rim, List<ICube> center, Rotator rotator) {
        digested = false;

        this.rotator = rotator;
        this.rim = rim;
        this.center = center;

        cubes = new ArrayList<>();
        cubes.addAll(rim);
        cubes.addAll(center);

        rotator.setRim(rim);
        rotator.setCenter(center);
    }

    @Override
    public List<ICube> getCubes() {
        return cubes;
    }

    @Override
    public List<ICube> getRim() {
        return rim;
    }

    @Override
    public List<ICube> getCenter() {
        return center;
    }

    @Override
    public List<Position> getPositions() {
        return cubes.stream().map(ICube::getPosition).collect(Collectors.toList());
    }

    @Override
    public void digest() {
        digested = true;
    }

    @Override
    public boolean isDigested() {
        return digested;
    }

    @Override
    public void erase(Position position) {

    }

    @Override
    public void move(Integer[] d) {
        cubes.stream().forEach(c -> c.move(d));
    }

    @Override
    public void rotate() {
        rotator.rotate();
    }

    @Override
    public Position getNextMovePosition(Integer[] d, ICube cube) {
        Position p = new Position(cube.getPosition());

        p.moveX(d[0]);
        p.moveX(d[1]);

        return p;
    }

    @Override
    public Position getNextRotatePosition(ICube cube) {
        Position p = new Position(cube.getPosition());
        rotator.rotate(p);

        return p;
    }

    @Override
    public void paint(Graphics g) {
        cubes.stream().forEach(c -> c.paint(g));
    }
}
