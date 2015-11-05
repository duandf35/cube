package cube.models;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wenyu
 * @since 11/3/15
 */
public class Tetris implements ITetris {
    private boolean digested;
    private List<ICube> cubes;

    public Tetris(List<Position> positions) {
        digested = false;
        cubes = new ArrayList<>();

        for (Position p: positions) {
            ICube cube = new Cube(p);

            cubes.add(cube);
        }
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
    public void rotate() {

    }

    @Override
    public void move(Integer[] d) {
        for (ICube c: cubes) {
            c.move(d);
        }
    }

    @Override
    public void paint(Graphics g) {
        for (ICube c: cubes) {
            c.paint(g);
        }
    }
}
