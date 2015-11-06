package cube.models;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wenyu
 * @since 11/3/15
 */
public class Tetris implements ITetris {
    private boolean digested;
    private List<ICube> cubes;
    private Rotator rotator;

    public Tetris(List<ICube> cubes, Rotator rotator) {
        digested = false;
        this.cubes = cubes;
        this.rotator = rotator;
    }

    @Override
    public List<ICube> getCubes() {
        return cubes;
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
        for (ICube c: cubes) {
            c.move(d);
        }
    }

    @Override
    public void rotate() {
        rotator.rotate();
    }

    @Override
    public void paint(Graphics g) {
        for (ICube c: cubes) {
            c.paint(g);
        }
    }
}
